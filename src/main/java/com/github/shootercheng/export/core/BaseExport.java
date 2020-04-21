package com.github.shootercheng.export.core;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author James
 */
public interface BaseExport {
    /**
     * query page data and core
     * @param dataGetFun query funcation
     */
    void exportQueryPage(Function<Map<String, Object>, List<String>> dataGetFun);

    /**
     * close stream
     */
    void close();

    /**
     * process row data
     * @param rowData row data
     */
    void processRowData(String rowData);

    /**
     * core data list
     * @param dataList data list
     * @param <T> T
     */
    <T> void exportList(List<T> dataList);
}
