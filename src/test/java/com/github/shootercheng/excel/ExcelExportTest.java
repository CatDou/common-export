package com.github.shootercheng.excel;

import com.github.shootercheng.ExportTest;
import com.github.shootercheng.export.BaseExport;
import com.github.shootercheng.export.ExcelExport;
import com.github.shootercheng.models.User;
import com.github.shootercheng.param.ExportParam;
import org.junit.Test;

import java.io.File;
import java.util.List;
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
        List<User> userList = createDataList();
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
        List<User> userList = createDataList();
        BaseExport baseExport = new ExcelExport(filePath, null, false, exportParam);
        baseExport.exportList(userList);
    }
}
