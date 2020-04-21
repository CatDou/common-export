package com.github.shootercheng.export.core;

import com.github.shootercheng.export.common.Constants;
import com.github.shootercheng.export.common.ExportCommon;
import com.github.shootercheng.export.param.ExportParam;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author James
 */
public interface QueryExport extends BaseExport {

    default void exportQuery(Function<Map<String, Object>, List<String>> pageQueryFun, ExportParam exportParam) {
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
                    processRowData(rowData);
                }
            }
        }
    }
}
