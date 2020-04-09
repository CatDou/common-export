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

package com.github.shootercheng.export;


import com.github.shootercheng.common.Constants;
import com.github.shootercheng.exception.ExportException;
import com.github.shootercheng.param.ExportParam;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * export data from db
 * @author chengdu
 */
public class CsvExport implements BaseExport, QueryExport, DataListExport {

    private BufferedWriter bufferedWriter;

    private ExportParam exportParam;

    private String recordSeparator;

    public CsvExport(BufferedWriter bufferedWriter, ExportParam exportParam) {
        this.bufferedWriter = bufferedWriter;
        this.exportParam = exportParam;
        this.recordSeparator = exportParam.getRecordSeparator() == null ?
                Constants.CRLF : exportParam.getRecordSeparator();
    }

    /**
     * export data from db
     * @param dataGetFun query function
     */
    @Override
    public void exportQueryPage(Function<Map<String, Object>, List<String>> dataGetFun) {
        processRowData(exportParam.getHeader());
        exportQuery(dataGetFun, exportParam);
    }

    @Override
    public void processRowData(String rowData) {
        if (exportParam.getRowFormat() != null) {
            rowData = exportParam.getRowFormat().formatRow(rowData);
        }
        try {
            bufferedWriter.append(rowData).append(recordSeparator);
        } catch (IOException e) {
            throw new ExportException("write row data error", e);
        }
    }

    @Override
    public void close() {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public <T> void exportList(List<T> dataList) {
        processRowData(exportParam.getHeader());
        exportList(dataList, exportParam);
    }
}
