package com.github.shootercheng.common;

import org.junit.Test;

/**
 * @author James
 */
public class RowQuotationFormatTest {

    @Test
    public void testFormat() {
        RowQuotationFormat rowQuotationFormat = new RowQuotationFormat();
        String testStr1 = "james,****,0";
        String testStr2 = "\"james0,1233,322\",\"****\",\"0\"";
        String testStr3 = "\"james0,123\"3,322\",\"****\",\"0\"";
        String testStr4 = "\"james\",\"\",\"0\"";
        String result1 = rowQuotationFormat.formatRow(testStr1);
        String result2 = rowQuotationFormat.formatRow(testStr2);
        String result3 = rowQuotationFormat.formatRow(testStr3);
        String result4 = rowQuotationFormat.formatRow(testStr4);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
    }
}
