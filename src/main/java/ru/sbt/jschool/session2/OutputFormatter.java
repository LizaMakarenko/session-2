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
    private PrintStream out;

    public OutputFormatter(PrintStream out) {

        this.out = out;
    }

    public void output(String[] names, Object[][] data) {
        int columns = names.length;
        int[] columnWidths = new int[columns];
        String[][] table = new String[data.length][columns];
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat moneyformat = new DecimalFormat("###,##0.00", symbols);
        moneyformat.setGroupingSize(3);
        moneyformat.setGroupingUsed(true);

        DecimalFormat numberformat = new DecimalFormat("###.###", symbols);
        numberformat.setGroupingSize(3);
        numberformat.setGroupingUsed(true);

        for (int i = 0; i < columns; i++) {
            columnWidths[i] = names[i].length();
        }

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < columns; col++) {
               Object value = data[row][col];
               String text;
               if(value == null){
                   text = "-";
               }
               else if(value instanceof String){
                   text=((String)value).replace("\n"," ");
               }
               else if(value instanceof Date){
                   text=sdf.format((Date)value);
               }
               else if(value instanceof Double|| value instanceof Float){
                   text=moneyformat.format(((Number)value).doubleValue());
               }
               else if(value instanceof Number){
                   text=numberformat.format(((Number)value).doubleValue());
               }
               else{
                   text=value.toString();
               }
               table[row][col]=text;

               if(text.length()>columnWidths[col]){
                   columnWidths[col]=text.length();
               }
            }
        }
        printBorder(columnWidths);
        out.print("|");

        for (int i = 0; i < columns; i++) {
            String name = center(names[i], columnWidths[i]);
            out.print(name+"|");
        }
        out.println();
        printBorder(columnWidths);

        for (int row = 0; row < data.length; row++) {
            out.print("|");
            for (int col = 0; col < columns; col++) {
                String text = table[row][col];
                boolean rightAligned = isRightAligned(data[row][col]);
                String formatted = rightAligned ? padLeft(text, columnWidths[col]) : padRight(text, columnWidths[col]);
                out.print(formatted+"|");
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

    private boolean isRightAligned(Object value) {
        return value instanceof Date || value instanceof Number || value == null;
    }

    private String repeat(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
