package com.github.shootercheng.common;

import com.github.shootercheng.exception.ParamBuildException;
import com.github.shootercheng.utils.ReflectUtil;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

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

    public static char[] UPPER_CHAR = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S',
            'T','U','V','W','X','Y','Z'};

    /**
     * 从 0 开始, AAA-702 不考虑
     * @param num the char num
     * @return excel column str
     */
    public static String calExcelNumChar(int num) {
        if (num < 0 || num >= 702) {
            throw new IllegalArgumentException("column num input error");
        }
        if (num < 26) {
            return String.valueOf(UPPER_CHAR[num]);
        }
        int firstIndex = num / 26 - 1;
        int secondIndex = num % 26;
        return String.valueOf(UPPER_CHAR[firstIndex]) + UPPER_CHAR[secondIndex];
    }

    public static Map<String, Integer> EXCEL_COLUMN = new HashMap<>();

    public static Map<Integer, String> COLUMN_NUM = new HashMap<>();

    static {
        for (int i = 0; i < 702; i++) {
            String columnStr = calExcelNumChar(i);
            EXCEL_COLUMN.put(columnStr, i);
            COLUMN_NUM.put(i, columnStr);
        }
    }

    public static List<Method> buildParamGetter(Class<?> clazz, Map<String, String> fieldColumnMap) {
        Set<String> keySet = fieldColumnMap.keySet();
        List<String> keyList = new ArrayList<>(keySet);
        keyList = keyList.stream().sorted().collect(Collectors.toList());
        Map<String, Method> allBeanGetterMap = ReflectUtil.getBeanGetterMap(clazz);
        List<Method> resultGetter = new ArrayList<>(keyList.size());
        for (String key : keyList) {
            String fieldName = fieldColumnMap.get(key);
            Method getterMethod = allBeanGetterMap.get(fieldName.toLowerCase());
            if (getterMethod == null) {
                throw new ParamBuildException("Bean " + clazz + " not contain field getter " + fieldName +
                        " ,please check config column map");
            }
            resultGetter.add(getterMethod);
        }
        return resultGetter;
    }
}
