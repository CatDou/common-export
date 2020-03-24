package com.github.shootercheng.common;

import com.github.shootercheng.define.RowFormat;

/**
 * @author James
 */
public class RowQuotationFormat implements RowFormat {
    @Override
    public String formatRow(String rowData) {
        String[] inputArr = rowData.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < inputArr.length; i++) {
            String istr = inputArr[i];
            // "123,456,789" 在一个单元格 需要合并
            if (istr.length() > 0 && istr.charAt(0) == '"' && istr.charAt(istr.length() - 1) != '"') {
                // 逗号连接替换成分号
                StringBuilder mergeStr = new StringBuilder(istr.substring(1) + ";");
                for (int j = i + 1; j < inputArr.length; j++) {
                    String jstr = inputArr[j];
                    i++;
                    if (jstr.length() > 0 && jstr.charAt(0) != '"' && jstr.charAt(jstr.length() - 1) == '"') {
                        mergeStr.append(jstr.substring(0, jstr.length() - 1));
                        break;
                    } else {
                        mergeStr.append(jstr).append(";");
                    }
                }
                stringBuilder.append(mergeStr).append(",");
            } else if (istr.length() > 0 && istr.charAt(0) == '"' && istr.charAt(istr.length() - 1) == '"') {
                stringBuilder.append(istr.substring(1, istr.length() -1)).append(",");
            } else {
                stringBuilder.append(istr).append(",");
            }
        }
        String result = stringBuilder.toString();
        if (result.length() > 1) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}
