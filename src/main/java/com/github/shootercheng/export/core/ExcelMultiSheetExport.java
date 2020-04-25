package com.github.shootercheng.export.core;

import com.github.shootercheng.export.exception.ExportException;
import com.github.shootercheng.export.param.ExportParam;
import com.github.shootercheng.export.utils.ExportStringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author James
 */
public class ExcelMultiSheetExport extends AbstractExcelExport {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelExport.class);

    private Map<Integer, ExportParam> exportParamMap;

    private ExportParam exportParam;

    private List<Integer> sortedIndexList;

    public ExcelMultiSheetExport(String filePath, CellStyle cellStyle, boolean isTemplate, Map<Integer, ExportParam> exportParamMap) {
        checkParam(filePath, exportParamMap);
        this.exportParamMap = exportParamMap;
        this.exportParam = getMinIndexParamAndSorted(exportParamMap);
        super.setFilePath(filePath);
        super.setExportParam(exportParam);
        super.setTemplate(isTemplate);
        super.setCellStyle(cellStyle);
        super.setSheetStartLine(exportParam.getStartLine());
        super.setSheetIndex(exportParam.getSheetIndex());
        try {
            initExcel();
        } catch (IOException e) {
            close();
            throw new ExportException("init excel error", e);
        }
    }

    private ExportParam getMinIndexParamAndSorted(Map<Integer, ExportParam> exportParamMap) {
        Set<Integer> sheetIndexSet = exportParamMap.keySet();
        List<Integer> sheetIndexList = new ArrayList<>(sheetIndexSet);
        sortedIndexList = sheetIndexList.stream().sorted().collect(Collectors.toList());
        return exportParamMap.get(sortedIndexList.get(0));
    }

    public ExcelMultiSheetExport(String templatePath, String targetPath, CellStyle cellStyle, Map<Integer, ExportParam> exportParamMap) {
        this(templatePath, cellStyle, true, exportParamMap);
        super.setTargetPath(targetPath);
    }

    private void checkParam(String filePath, Map<Integer, ExportParam> exportParamMap) {
        if (filePath == null || filePath.length() == 0) {
            throw new ExportException("input file path is empty");
        }
        if (exportParamMap == null || exportParamMap.size() == 0) {
            throw new ExportException("input export param map is empty");
        }
        Collection<ExportParam> exportParams = exportParamMap.values();
        Set<String> sheetNames = new HashSet<>(exportParams.size());
        for (ExportParam exportParam : exportParams) {
            String sheetName = exportParam.getSheetName();
            if (!ExportStringUtils.isEmpty(sheetName)) {
                if (sheetNames.contains(sheetName)) {
                    throw new ExportException("input param exists same sheet name " + sheetName);
                }
                sheetNames.add(sheetName);
            }
        }
    }

    private void resetSheetParamAndInit(Integer paramIndex) {
        exportParam = exportParamMap.get(paramIndex);
        String sheetName = getDefaultSheetName(paramIndex);
        Integer sheetStartLine = exportParam.getStartLine();
        super.setSheetStartLine(sheetStartLine);
        super.setSheetIndex(0);
        super.setExportParam(exportParam);
        initSheet(sheetName);
    }

    public void exportQueryPageByParamIndex(Function<Map<String, Object>, List<String>> dataGetFun, Integer paramIndex) {
        exportQueryPageByParamIndex(dataGetFun, paramIndex, false);
    }

    public void exportQueryPageByParamIndex(Function<Map<String, Object>, List<String>> dataGetFun, Integer paramIndex, boolean isSave) {
        resetSheetParamAndInit(paramIndex);
        exportQuery(dataGetFun, exportParam);
        if (isSave) {
            saveExcel();
            LOGGER.info("save excel to file success");
        }
    }


    private String getDefaultSheetName(Integer paramIndex) {
        String sheetName = exportParam.getSheetName();
        if (sheetName == null || sheetName.trim().length() == 0) {
            sheetName = "sheet" + paramIndex;
            exportParam.setSheetName(sheetName);
        }
        return sheetName;
    }

    public <T> void exportListByParamIndex(List<T> dataList, Integer paramIndex) {
        exportListByParamIndex(dataList, paramIndex, false);
    }

    public <T> void exportListByParamIndex(List<T> dataList, Integer paramIndex, boolean isSave) {
        resetSheetParamAndInit(paramIndex);
        exportList(dataList, exportParam);
        if (isSave) {
            saveExcel();
            LOGGER.info("save excel to file success");
        }
    }
}
