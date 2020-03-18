package com.github.shootercheng.export;

import com.github.shootercheng.exception.ExportException;
import com.github.shootercheng.param.ExportParam;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author James
 */
public interface BaseExport {
    void exportQueryPage(Function<Map<String, Object>, List<String>> dataGetFun);

    default void exportCommon(Function<Map<String, Object>, List<String>> pageQueryFun, ExportParam exportParam) {
        int sum = exportParam.getSum();
        int pageSize = exportParam.getPageSize();
        List<Integer> indexList = ExportCommon.calIndexList(sum, pageSize);
        Map<String, Object> searchParam = exportParam.getSearchParam();
        searchParam.put(Constants.PAGE_QUERY_SIZE, pageSize);
        for (Integer index : indexList) {
            searchParam.put(Constants.PADE_QUERY_INDEX, index);
            List<String> queryList = pageQueryFun.apply(searchParam);
            if (queryList != null && queryList.size() > 0) {
                for (String rowData : queryList) {
                    try {
                        processRowData(rowData);
                    } catch (Exception e) {
                        throw new ExportException("process row data error", e);
                    }
                }
            }
        }
    }

    void processRowData(String rowData) throws Exception;
}
