package com.github.shootercheng.export;

import java.util.ArrayList;
import java.util.List;

/**
 * @author James
 */
public class ExportCommon {
    /**
     * calculate page start index
     * @param sum total number of data
     * @param pageNum page size
     * @return index collection
     */
    public static List<Integer> calIndexList(int sum, int pageNum) {
        List<Integer> list = new ArrayList<>(sum / pageNum);
        Integer startIndex = 0;
        if (sum <= pageNum) {
            list.add(startIndex);
            return list;
        }
        while (startIndex + pageNum < sum) {
            list.add(startIndex);
            startIndex = startIndex + pageNum;
        }
        // the last page
        list.add(startIndex);
        return list;
    }
}
