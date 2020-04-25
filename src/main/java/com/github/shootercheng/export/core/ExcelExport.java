package com.github.shootercheng.export.core;

import com.github.shootercheng.export.exception.ExportException;
import com.github.shootercheng.export.param.ExportParam;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;



/**
 * @author James
 */
public class ExcelExport extends AbstractExcelExport implements BaseExport, QueryExport, DataListExport {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelExport.class);

    private ExportParam exportParam;

    public ExcelExport(String filePath, CellStyle cellStyle, boolean isTemplate, ExportParam exportParam) {
        super.setFilePath(filePath);
        this.exportParam = exportParam;
        super.setExportParam(exportParam);
        super.setTemplate(isTemplate);
        super.setCellStyle(cellStyle);
        super.setSheetStartLine(exportParam.getStartLine());
        super.setSheetIndex(exportParam.getSheetIndex());
        try {
            super.initExcel();
        } catch (IOException e) {
            close();
            throw new ExportException("init excel error", e);
        }
    }

    public ExcelExport(String templatePath, String targetPath, CellStyle cellStyle, ExportParam exportParam) {
        this(templatePath, cellStyle, true, exportParam);
        super.setTargetPath(targetPath);
    }
}
