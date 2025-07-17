package ru.sbt.jschool.session2;

import java.io.PrintStream;

public class OutputFormatter {
    private final PrintStream out;
    private final DataFormatter formatter;

    public OutputFormatter(PrintStream out) {
        this.out = out;
        this.formatter = new DataFormatter();
    }

    public void output(String[] headers, Object[][] data) {
        TableData tableData = new TableData(headers, data, formatter);
        printTable(tableData);
    }

    private void printTable(TableData tableData) {
        int[] columnWidths = tableData.getColumnWidths();
        String[] headers = tableData.getHeaders();
        Object[][] rawData = tableData.getRawData();
        Class<?>[] columnTypes = tableData.getColumnTypes();

        printBorder(columnWidths);

        out.print("|");
        for (int i = 0; i < headers.length; i++) {
            out.print(center(headers[i], columnWidths[i]) + "|");
        }
        out.println();
        printBorder(columnWidths);

        for (int row = 0; row < rawData.length; row++) {
            out.print("|");
            for (int col = 0; col < headers.length; col++) {
                Object value = rawData[row][col];
                String text = formatter.format(value);
                boolean rightAligned = formatter.isRightAligned(value, columnTypes[col]);
                String formatted = rightAligned
                        ? padLeft(text, columnWidths[col])
                        : padRight(text, columnWidths[col]);
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
