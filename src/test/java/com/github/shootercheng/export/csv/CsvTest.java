package com.github.shootercheng.export.csv;

import com.github.shootercheng.export.ExportTest;
import com.github.shootercheng.export.common.Constants;
import com.github.shootercheng.export.core.BaseExport;
import com.github.shootercheng.export.core.CsvExport;
import com.github.shootercheng.export.models.User;
import com.github.shootercheng.export.param.ExportParam;
import com.github.shootercheng.export.service.DataService;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author James
 */
public class CsvTest extends ExportTest {

    public String createFilePath(String fileName) {
        String exportDir = "file" + File.separator + UUID.randomUUID().toString();
        File dirFile = new File(exportDir);
        dirFile.mkdirs();
        String filePath = exportDir + File.separator + fileName;
        return filePath;
    }

    @Test
    public void testExportCsv() {
        // build setter method
        ExportParam exportParam = buildUserExportParam();
        List<User> userList = createDataList(100);
        String filePath = createFilePath("test.csv");
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
        String filePath = createFilePath("test.csv");
        ExportParam exportParam = buildUserExportParam();
        CsvExport csvExport = new CsvExport(filePath, exportParam);
        List<User> userList = createDataList(100);
        csvExport.exportList(userList);
    }

    @Test
    public void testDataFunExport() {
        String filePath = createFilePath("data-function.csv");
        DataService dataService = new DataService();
        ExportParam exportParam = new ExportParam();
        Map<String, Object> searchParam = new HashMap<>(16);
        exportParam.setHeader("username,password,seq");
        // 1000000 数据， 每次查询 10000
        exportParam.setSum(dataService.countSum());
        exportParam.setPageSize(10000);
        exportParam.setRecordSeparator(Constants.CRLF);
        exportParam.setSearchParam(searchParam);
        BaseExport baseExport = new CsvExport(filePath, exportParam);
        baseExport.exportQueryPage(dataService::selectPage);
    }
}
