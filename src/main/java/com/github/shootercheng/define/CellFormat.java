package com.github.shootercheng.define;

/**
 * @author chengdu
 *
 */
public interface CellFormat {
    /**
     * format cell value
     * @param column
     * @param cellValue
     * @return
     */
    String format(String column, String cellValue);
}
