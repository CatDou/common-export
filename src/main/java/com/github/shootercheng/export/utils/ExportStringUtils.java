package com.github.shootercheng.export.utils;

/**
 * @author James
 */
public class ExportStringUtils {

    public static boolean isEmpty(String inputStr) {
        if (inputStr == null || inputStr.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
