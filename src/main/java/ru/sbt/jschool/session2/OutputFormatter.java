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

package ru.sbt.jschool.session2;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class OutputFormatter {
    private final PrintStream out;
    private final DataFormatter formatter;

    public OutputFormatter(PrintStream out) {
        this.out = out;
        this.formatter = new DataFormatter();
    }

    public void output(String[] headers, Object[][] data) {
        TableData tableData = new TableData(headers, data);
        tableData.prepareFormattedData(formatter);

        printTable(tableData);
    }
    private void printTable(TableData tableData) {
        int[] columnWidths = tableData.getColumnWidths();
        String[] headers = tableData.getHeaders();
        String[][] formattedData = tableData.getFormattedData();
        Object[][] rawData = tableData.getRawData();

        printBorder(columnWidths);

        out.print("|");
        for (int i = 0; i < headers.length; i++) {
            out.print(center(headers[i], columnWidths[i]) + "|");
        }
        out.println();
        printBorder(columnWidths);

        for (int row = 0; row < formattedData.length; row++) {
            out.print("|");
            for (int col = 0; col < headers.length; col++) {
                String text = formattedData[row][col];
                boolean rightAligned = formatter.isRightAligned(rawData[row][col]);
                String formatted = rightAligned ? padLeft(text, columnWidths[col]) : padRight(text, columnWidths[col]);
                out.print(formatted + "|");
            }
            out.println();
            printBorder(columnWidths);
        }
    }
    private void printBorder(int[] widths) {
        out.print("+");
        for (int w : widths) {
            out.print(repeat('-', w));
            out.print("+");
        }
        out.println();
    }

    private String padLeft(String text, int width) {
        return repeat(' ', width - text.length()) + text;
    }

    private String padRight(String text, int width) {
        return text + repeat(' ', width - text.length());
    }

    private String center(String text, int width) {
        int padding = width - text.length();
        int left = padding / 2;
        int right = padding - left;
        return repeat(' ', left) + text + repeat(' ', right);
    }

    private String repeat(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }
}

class TableData {
    private final String[] headers;
    private final Object[][] rawData;
    private final String[][] formattedData;
    private final int[] columnWidths;

    public TableData(String[] headers, Object[][] rawData) {
        this.headers = headers;
        this.rawData = rawData;
        int columns = headers.length;
        int rows = rawData.length;

        this.formattedData = new String[rows][columns];
        this.columnWidths = new int[columns];

        for (int i = 0; i < columns; i++) {
            columnWidths[i] = headers[i].length();
        }
    }
    public void prepareFormattedData(DataFormatter formatter) {
        int columns = headers.length;
        int rows = rawData.length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                String formatted = formatter.format(rawData[row][col]);
                formattedData[row][col] = formatted;

                if (formatted.length() > columnWidths[col]) {
                    columnWidths[col] = formatted.length();
                }
            }
        }
    }
    public String[] getHeaders() {
        return headers;
    }

    public String[][] getFormattedData() {
        return formattedData;
    }

    public int[] getColumnWidths() {
        return columnWidths;
    }

    public Object[][] getRawData() {
        return rawData;
    }
}
class DataFormatter {
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private final DecimalFormat moneyf;
    private final DecimalFormat numberf;

    public DataFormatter() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        moneyf = new DecimalFormat();
        moneyf.setDecimalFormatSymbols(symbols);
        moneyf.applyPattern("###,##0.00");
        moneyf.setGroupingSize(3);
        moneyf.setGroupingUsed(true);

        numberf = new DecimalFormat();
        numberf.setDecimalFormatSymbols(symbols);
        numberf.applyPattern("###.###");
        numberf.setGroupingSize(3);
        numberf.setGroupingUsed(true);
    }
    public String format(Object value) {
        if(value==null){
            return "-";
        }
        else if(value instanceof String ){
            return((String)value).replace("\n"," ");
        }
        else if(value instanceof Date){
            return sdf.format((Date)value);
        }
        else if(value instanceof Double || value instanceof Float){
            return moneyf.format(((Number) value).doubleValue());
        }
        else if(value instanceof Number){
            return numberf.format(((Number)value).doubleValue());
        }
        else {
            return value.toString();
        }
    }
    public boolean isRightAligned(Object value) {
        return value instanceof Date|| value instanceof Number||value==null;
        }
    }
