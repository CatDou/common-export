package com.github.shootercheng.export.excel;

import com.github.shootercheng.export.ExportTest;
import com.github.shootercheng.export.core.BaseExport;
import com.github.shootercheng.export.core.ExcelExport;
import com.github.shootercheng.export.models.User;
import com.github.shootercheng.export.param.ExportParam;
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
