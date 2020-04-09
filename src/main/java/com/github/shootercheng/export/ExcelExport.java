package com.github.shootercheng.export;

import com.github.shootercheng.common.Constants;
import com.github.shootercheng.common.ExportCommon;
import com.github.shootercheng.common.RowQuotationFormat;
import com.github.shootercheng.define.RowFormat;
import com.github.shootercheng.exception.ExportException;
import com.github.shootercheng.param.ExportParam;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.github.shootercheng.common.Constants.EXCEL_XLS;
import static com.github.shootercheng.common.Constants.EXCEL_XLSX;


/**
 * @author James
 */
public class ExcelExport implements BaseExport, QueryExport, DataListExport {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelExport.class);

    private String filePath;

    private String targetPath;

    private CellStyle cellStyle;

    private ExportParam exportParam;

    private boolean isTemplate;

    private Workbook workbook;

    private Sheet sheet;

    private int sheetStartLine;

    private int sheetIndex;

    private String excelType;

    private RowFormat rowFormat;

    public ExcelExport(String filePath, CellStyle cellStyle, boolean isTemplate, ExportParam exportParam) {
        this.filePath = filePath;
        this.exportParam = exportParam;
        this.isTemplate = isTemplate;
        this.cellStyle = cellStyle;
        this.sheetStartLine = exportParam.getStartLine();
        this.sheetIndex = exportParam.getSheetIndex();
        this.rowFormat = new RowQuotationFormat();
        try {
            initExcel();
        } catch (IOException e) {
            close();
            throw new ExportException("init excel error", e);
        }
    }

    public ExcelExport(String templatePath, String targetPath, CellStyle cellStyle, ExportParam exportParam) {
        this(templatePath, cellStyle, true, exportParam);
        this.targetPath = targetPath;
    }

    @Override
    public void exportQueryPage(Function<Map<String, Object>, List<String>> dataGetFun) {
        exportQuery(dataGetFun, exportParam);
        saveExcel();
    }

    /**
     * 默认使用 ',' 号拼接
     * @param rowData
     * @throws Exception
     */
    @Override
    public void processRowData(String rowData) {
        // 去除引号
        rowData = rowFormat.formatRow(rowData);
        if (exportParam.getRowFormat() != null) {
            rowData = exportParam.getRowFormat().formatRow(rowData);
        }
        String[] cellValues = rowData.split(",");
        // 超过最大行数，就再创建下一页
        boolean maxXlsRow = EXCEL_XLS.equals(excelType) && sheetStartLine > Constants.EXCEL_MAX_ROW_XLS;
        boolean maxXlsxRow = EXCEL_XLSX.equals(excelType) && sheetStartLine > Constants.EXCEL_MAX_ROW_XLSX;
        if ( maxXlsRow||maxXlsxRow ) {
            sheetStartLine = exportParam.getStartLine();
            // 初始化已判断null，这里不判断
            String sheetName = exportParam.getSheetName();
            int curIndex = ++sheetIndex;
            initSheet(sheetName + "_" + curIndex);
        }
        fillRowData(sheet, cellValues, sheetStartLine);
        sheetStartLine++;
    }

    private void initExcel() throws IOException {
        if(filePath == null || filePath.trim().length() == 0){
            throw new ExportException("file path is null");
        }
        if (!isTemplate) {
            if (filePath.endsWith(EXCEL_XLS)) {
                workbook = new HSSFWorkbook();
                excelType = EXCEL_XLS;
            } else if (filePath.endsWith(EXCEL_XLSX)) {
                workbook = new SXSSFWorkbook();
                excelType = EXCEL_XLSX;
            } else {
                throw new ExportException("excel type error");
            }
        } else {
            InputStream inputStream = new FileInputStream(filePath);
            if (filePath.endsWith(EXCEL_XLS)) {
                workbook = new HSSFWorkbook(inputStream);
                excelType = EXCEL_XLS;
            } else if (filePath.endsWith(EXCEL_XLSX)) {
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(filePath);
                workbook = new SXSSFWorkbook(xssfWorkbook);
                excelType = EXCEL_XLSX;
            } else {
                throw new ExportException("excel type error");
            }
        }
        String sheetName = exportParam.getSheetName();
        if (sheetName == null || sheetName.trim().length() == 0) {
            sheetName = "sheet";
            exportParam.setSheetName(sheetName);
        }
        initSheet(sheetName);
    }

    private void initSheet(String sheetName) {
        if (!isTemplate) {
            sheet = workbook.createSheet(sheetName);
            String[] headers = exportParam.getHeader().split(",");
            writeExcelTitle(sheet, cellStyle, headers, sheetStartLine);
            sheetStartLine++;
        } else {
            if (sheetIndex == exportParam.getSheetIndex()) {
                sheet = workbook.getSheetAt(exportParam.getSheetIndex());
            } else {
                sheet = workbook.createSheet(sheetName);
                sheetStartLine = 0;
            }
        }
        LOGGER.info("now sheet is {}", sheetName);
    }

    private void writeExcelTitle(Sheet sheet, CellStyle cellStyle, String[] headers, int rowNum) {
        // 创建标题行, 添加样式
        Row row = sheet.createRow(rowNum);
        for(int i = 0; i < headers.length; i++){
            Cell cell = row.createCell(i);
            if (cellStyle != null) {
                cell.setCellStyle(cellStyle);
            }
            cell.setCellValue(headers[i]);
        }
    }

    private void fillRowData(Sheet sheet, String[] cellValues, int curLine){
        Row valueRow = sheet.createRow(curLine);
        for(int j = 0; j < cellValues.length; j++) {
            Cell cell = valueRow.createCell(j);
            if (exportParam.getCellFormat() != null) {
                String columnChar = ExportCommon.COLUMN_NUM.get(j);
                String cellValue = exportParam.getCellFormat().format(columnChar, cellValues[j]);
                cell.setCellValue(cellValue);
            } else {
                cell.setCellValue(cellValues[j]);
            }
        }
    }

    @Override
    public void close() {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <T> void exportList(List<T> dataList) {
        exportList(dataList, exportParam);
        saveExcel();
    }

    private void saveExcel() {
        OutputStream fileOutputStream = null;
        try {
            // 写入文件
            if (!isTemplate) {
                fileOutputStream = new FileOutputStream(filePath);
            } else {
                fileOutputStream = new FileOutputStream(targetPath);
            }
            workbook.write(fileOutputStream);
        } catch (Exception e) {
            LOGGER.error("export excel error");
            throw new ExportException("export excel error", e);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    LOGGER.info("close workbook error");
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    LOGGER.info("close file outputstream error");
                }
            }
        }
    }
}
