package com.github.shootercheng.csv;

import com.github.shootercheng.ExportTest;
import com.github.shootercheng.export.BaseExport;
import com.github.shootercheng.export.CsvExport;
import com.github.shootercheng.models.User;
import com.github.shootercheng.param.ExportParam;
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
        List<User> userList = createDataList();
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath, true),
                        StandardCharsets.UTF_8))) {
            BaseExport baseExport = new CsvExport(bufferedWriter, exportParam);
            baseExport.exportList(userList);
        } catch (IOException e) {
        } finally {
        }
    }
}
