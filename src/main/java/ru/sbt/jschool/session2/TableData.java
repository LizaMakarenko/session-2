package ru.sbt.jschool.session2;

class TableData {
    private final String[] headers;
    private final Object[][] rawData;
    private final int[] columnWidths;

    public TableData(String[] headers, Object[][] rawData, DataFormatter formatter) {
        this.headers = headers;
        this.rawData = rawData;
        int columns = headers.length;
        int rows = rawData.length;
        this.columnWidths = new int[columns];

        for (int i = 0; i < columns; i++) {
            columnWidths[i] = headers[i].length();
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Object value = rawData[row][col];
                int len = formatter.length(value);
                if (len > columnWidths[col]) {
                    columnWidths[col] = len;
                }
            }
        }
    }

    public String[] getHeaders() {
        return headers;
    }

    public int[] getColumnWidths() {
        return columnWidths;
    }

    public Object[][] getRawData() {
        return rawData;
    }
}
