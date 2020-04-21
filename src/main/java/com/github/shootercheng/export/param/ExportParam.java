/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.shootercheng.export.param;

import com.github.shootercheng.export.define.CellFormat;
import com.github.shootercheng.export.define.RowFormat;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * core param for db query core
 * @author chengdu
 */
public class ExportParam {
    private String header;

    private String recordSeparator;

    private String sheetName;

    private int startLine;

    private int sheetIndex;

    private int sum;

    private int pageSize;

    private Map<String, Object> searchParam;

    private RowFormat rowFormat;

    private CellFormat cellFormat;

    private List<Method> getterMethod;

    private Charset charset;

    public String getHeader() {
        return header;
    }

    public ExportParam setHeader(String header) {
        this.header = header;
        return this;
    }

    public int getSum() {
        return sum;
    }

    public ExportParam setSum(int sum) {
        this.sum = sum;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public ExportParam setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Map<String, Object> getSearchParam() {
        return searchParam;
    }

    public ExportParam setSearchParam(Map<String, Object> searchParam) {
        this.searchParam = searchParam;
        return this;
    }

    public String getRecordSeparator() {
        return recordSeparator;
    }

    public ExportParam setRecordSeparator(String recordSeparator) {
        this.recordSeparator = recordSeparator;
        return this;
    }

    public String getSheetName() {
        return sheetName;
    }

    public ExportParam setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public int getStartLine() {
        return startLine;
    }

    public ExportParam setStartLine(int startLine) {
        this.startLine = startLine;
        return this;
    }

    public RowFormat getRowFormat() {
        return rowFormat;
    }

    public ExportParam setRowFormat(RowFormat rowFormat) {
        this.rowFormat = rowFormat;
        return this;
    }

    public CellFormat getCellFormat() {
        return cellFormat;
    }

    public ExportParam setCellFormat(CellFormat cellFormat) {
        this.cellFormat = cellFormat;
        return this;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public ExportParam setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
        return this;
    }

    public List<Method> getGetterMethod() {
        return getterMethod;
    }

    public ExportParam setGetterMethod(List<Method> getterMethod) {
        this.getterMethod = getterMethod;
        return this;
    }

    public Charset getCharset() {
        return charset;
    }

    public ExportParam setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }
}
