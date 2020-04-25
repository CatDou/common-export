package com.github.shootercheng.export.excel;

import com.github.shootercheng.export.ExportTest;
import com.github.shootercheng.export.common.Constants;
import com.github.shootercheng.export.core.BaseExport;
import com.github.shootercheng.export.core.ExcelExport;
import com.github.shootercheng.export.core.ExcelMultiSheetExport;
import com.github.shootercheng.export.models.User;
import com.github.shootercheng.export.param.ExportParam;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author James
 */
public class ExcelExportTest extends ExportTest {

    @Test
    public void testExcel2003() {
        ExportParam exportParam = buildUserExportParam();
        String exportDir = "file" + File.separator + UUID.randomUUID().toString();
        File dirFile = new File(exportDir);
        dirFile.mkdirs();
        String filePath = exportDir + File.separator + "test.xls";
        List<User> userList = createDataList(Constants.EXCEL_MAX_ROW_XLS * 2);
        BaseExport baseExport = new ExcelExport(filePath, null, false, exportParam);
        baseExport.exportList(userList);
    }

    @Test
    public void testExcel2007() {
        ExportParam exportParam = buildUserExportParam();
        String exportDir = "file" + File.separator + UUID.randomUUID().toString();
        File dirFile = new File(exportDir);
        dirFile.mkdirs();
        String filePath = exportDir + File.separator + "test.xlsx";
        List<User> userList = createDataList(Constants.EXCEL_MAX_ROW_XLSX * 2);
        BaseExport baseExport = new ExcelExport(filePath, null, false, exportParam);
        baseExport.exportList(userList);
    }

    @Test
    public void testManySheet() {
        String exportDir = "file" + File.separator + UUID.randomUUID().toString();
        File dirFile = new File(exportDir);
        dirFile.mkdirs();
        String filePath = exportDir + File.separator + "test-many.xlsx";
        ExportParam exportParam1 = buildUserExportParam();
        ExportParam exportParam2 = buildUserExportParam();
        Map<Integer, ExportParam> exportParamMap = new HashMap<>(16);
        exportParamMap.put(0, exportParam1);
        exportParamMap.put(1, exportParam2);
        ExcelMultiSheetExport excelMultiSheetExport = new ExcelMultiSheetExport(filePath, null,
                false, exportParamMap);
        List<User> userList = createDataList(Constants.EXCEL_MAX_ROW_XLSX * 3);
        excelMultiSheetExport.exportListByParamIndex(userList, 0);
        excelMultiSheetExport.exportListByParamIndex(userList, 1, true);
    }
}
