package com.github.shootercheng.export.define;

/**
 * @author chengdu
 *
 */
public interface CellFormat {
    /**
     * format cell value
     * @param column csv or excel column
     * @param cellValue cell value
     * @return String
     */
    String format(String column, String cellValue);
}
