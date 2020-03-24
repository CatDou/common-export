package com.github.shootercheng.export;

import com.github.shootercheng.exception.ExportException;
import com.github.shootercheng.param.ExportParam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author James
 */
public interface BaseExport {
    /**
     * query page data and export
     * @param dataGetFun query funcation
     */
    void exportQueryPage(Function<Map<String, Object>, List<String>> dataGetFun);

    /**
     * close stream
     */
    void close();

    /**
     * process row data
     * @param rowData
     */
    void processRowData(String rowData);

    /**
     * export data list
     * @param dataList data list
     * @param <T> T
     */
    <T> void exportList(List<T> dataList);
}
