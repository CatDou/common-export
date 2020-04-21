package com.github.shootercheng.export.core;

import com.github.shootercheng.export.common.Constants;
import com.github.shootercheng.export.common.ExportCommon;
import com.github.shootercheng.export.exception.ExportException;
import com.github.shootercheng.export.param.ExportParam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author James
 */
public interface DataListExport extends BaseExport {

    default <T> void exportList(List<T> dataList, ExportParam exportParam) {
        List<Method> getterMethods = exportParam.getGetterMethod();
        for (Object object : dataList) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < getterMethods.size(); i++) {
                try {
                    Method method = getterMethods.get(i);
                    Object value = method.invoke(object, Constants.NO_ARGUMENTS);
                    if (value != null) {
                        String columnChar = ExportCommon.COLUMN_NUM.get(i);
                        if (exportParam.getCellFormat() != null) {
                            value = exportParam.getCellFormat().format(columnChar, String.valueOf(value));
                        }
                        stringBuilder.append("\"").append(value).append("\"").append(",");
                    } else {
                        stringBuilder.append("\"").append("\"").append(",");
                    }
                } catch (IllegalAccessException e) {
                    throw new ExportException("illegal access exception", e);
                } catch (InvocationTargetException e) {
                    throw new ExportException("invocation target exception", e);
                }
            }
            String rowData = stringBuilder.toString();
            if (rowData.length() > 1) {
                rowData = rowData.substring(0, rowData.length() - 1);
            }
            processRowData(rowData);
        }
    }
}
