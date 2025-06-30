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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 */
public class OutputFormatter {
    private PrintStream out;

    public OutputFormatter(PrintStream out) {

        this.out = out;
    }

    public void output(String[] names, Object[][] data) {
        int columns = names.length;
        int [] columnWidths = new int[columns];
        String [] [] table = new String[data.length][columns];
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        DecimalFormat moneyf = new DecimalFormat("#,##0.00");
        DecimalFormat  numberf = new DecimalFormat("#,###");

        for (int i=0; i<columns; i++) {
            columnWidths[i] = table[i][0].length();
        }
        for (int row=0; row<data.length; row++) {
            for(int col=0; col<columns; col++) {
                Object value = data[row][col];
                String text;
                if (value == null) {
                    text = "-";
                } else if (value instanceof String) {
                    text = ((String) value).replace("\n", " ");
                } else if (value instanceof Date) {
                    text = sdf.format((Date) value);
                } else if (value instanceof Float || value instanceof Double) {
                    text = moneyf.format(((Number) value).doubleValue());
                } else if (value instanceof Number) {
                    text = numberf.format(((Number) value).longValue());
                } else {
                    text = value.toString();
                }
                table[row][col]=text;
                columnWidths[col]=Math.max(columnWidths[col], text.length());
            }
        }

    }
}
