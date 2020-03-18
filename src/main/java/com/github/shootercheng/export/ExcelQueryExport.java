package com.github.shootercheng.export;

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

import static com.github.shootercheng.export.Constants.EXCEL_XLS;
import static com.github.shootercheng.export.Constants.EXCEL_XLSX;


/**
 * @author James
 */
public class ExcelQueryExport implements BaseExport {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelQueryExport.class);

    private String filePath;

    private CellStyle cellStyle;

    private ExportParam exportParam;

    private boolean isTemplate;

    private Workbook workbook;

    private Sheet sheet;

    private int sheetStartLine;

    public ExcelQueryExport(String filePath, CellStyle cellStyle, boolean isTemplate, ExportParam exportParam) {
        this.filePath = filePath;
        this.exportParam = exportParam;
        this.isTemplate = isTemplate;
        this.cellStyle = cellStyle;
        this.sheetStartLine = exportParam.getStartLine();
        try {
            initExcel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportQueryPage(Function<Map<String, Object>, List<String>> dataGetFun) {
        OutputStream fileOutputStream = null;
        try {
            exportCommon(dataGetFun, exportParam);
            // 写入文件
            if (!isTemplate) {
                fileOutputStream = new FileOutputStream(filePath);
                workbook.write(fileOutputStream);
            }
        } catch (Exception e) {
            LOGGER.error("export excel error");
            throw new ExportException("export excel error", e);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 默认使用 ',' 号拼接
     * @param rowData
     * @throws Exception
     */
    @Override
    public void processRowData(String rowData) {
        if (exportParam.getRowFormat() != null) {
            rowData = exportParam.getRowFormat().formatRow(rowData);
        }
        String[] cellValues = rowData.split(",");
        int startLine = exportParam.getStartLine();
        // 超过最大行数，就再创建下一页
        if (startLine > Constants.EXCEL_MAX_ROW) {
            startLine = sheetStartLine;
            exportParam.setStartLine(sheetStartLine);
            // 初始化已判断null，这里不判断
            String sheetName = exportParam.getSheetName();
            int index = exportParam.getSheetIndex() + 1;
            initSheet(sheetName + "_" + index);
            exportParam.setSheetIndex(index);
        }
        fillRowData(sheet, cellValues, startLine);
        exportParam.setStartLine(++startLine);
    }


    private void initExcel() throws IOException {
        if(filePath == null || filePath.trim().length() == 0){
            throw new ExportException("file path is null");
        }
        // create workbook
        String sheetName = exportParam.getSheetName();
        if (sheetName == null || sheetName.trim().length() == 0) {
            sheetName = "sheet";
            exportParam.setSheetName(sheetName);
        }
        if (!isTemplate) {
            if (filePath.endsWith(EXCEL_XLS)) {
                workbook = new HSSFWorkbook();
            } else if (filePath.endsWith(EXCEL_XLSX)) {
                workbook = new SXSSFWorkbook();
            } else {
                throw new ExportException("excel type error");
            }
        } else {
            InputStream inputStream = new FileInputStream(filePath);
            if (filePath.endsWith(EXCEL_XLS)) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (filePath.endsWith(EXCEL_XLSX)) {
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(filePath);
                workbook = new SXSSFWorkbook(xssfWorkbook);
            } else {
                throw new ExportException("excel type error");
            }
        }
        initSheet(sheetName);
    }

    private void initSheet(String sheetName) {
        if (!isTemplate) {
            sheet = workbook.createSheet(sheetName);
            String[] headers = exportParam.getHeader().split(",");
            int startLine = exportParam.getStartLine();
            writeExcelTitle(sheet, cellStyle, headers, startLine);
            exportParam.setStartLine(++startLine);
        } else {
            sheet = workbook.createSheet(sheetName);
        }
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

    public void fillRowData(Sheet sheet, String[] cellValues, int curLine){
        Row valueRow = sheet.createRow(curLine);
        for(int j = 0; j < cellValues.length; j++) {
            Cell cell = valueRow.createCell(j);
            if (exportParam.getCellFormat() != null) {
                String cellValue = exportParam.getCellFormat().format(j, cellValues[j]);
                cell.setCellValue(cellValue);
            } else {
                cell.setCellValue(cellValues[j]);
            }
        }
    }

}