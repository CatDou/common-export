package com.github.shootercheng.export.csv;

import com.github.shootercheng.export.ExportTest;
import com.github.shootercheng.export.core.BaseExport;
import com.github.shootercheng.export.core.CsvExport;
import com.github.shootercheng.export.models.User;
import com.github.shootercheng.export.param.ExportParam;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author James
 */
public class CsvTest extends ExportTest {

    @Test
    public void testExportCsv() {
        // build setter method
        ExportParam exportParam = buildUserExportParam();
        String exportDir = "file" + File.separator + UUID.randomUUID().toString();
        File dirFile = new File(exportDir);
        dirFile.mkdirs();
        String filePath = exportDir + File.separator + "test.csv";
        List<User> userList = createDataList(100);
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath, true),
                        StandardCharsets.UTF_8))) {
            BaseExport baseExport = new CsvExport(bufferedWriter, exportParam);
            baseExport.exportList(userList);
        } catch (IOException e) {
        } finally {
        }
    }

    @Test
    public void testExportCsvPath() {
        String exportDir = "file" + File.separator + UUID.randomUUID().toString();
        File dirFile = new File(exportDir);
        dirFile.mkdirs();
        String filePath = exportDir + File.separator + "test.csv";
        ExportParam exportParam = buildUserExportParam();
        CsvExport csvExport = new CsvExport(filePath, exportParam);
        List<User> userList = createDataList(100);
        csvExport.exportList(userList);

    }
}
