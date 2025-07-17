package ru.sbt.jschool.session2;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;

public class DoublePrinter implements Printer {
    private final DecimalFormat moneyf;

    public DoublePrinter() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        moneyf = new DecimalFormat("###,##0.00");
        moneyf.setDecimalFormatSymbols(symbols);
    }

    @Override
    public List<Class<?>> supported() {
        return Arrays.asList(Double.class, Float.class);
    }

    @Override
    public int length(Object obj) {
        if (obj == null) return 1;
        double value = ((Number) obj).doubleValue();

        int intPartLength = (value == 0) ? 1 : (int) Math.floor(Math.log10(Math.abs(value))) + 1;
        int groupSeparators = (intPartLength - 1) / 3;
        int decimalLength = 3;
        int sign = value < 0 ? 1 : 0;

        return intPartLength + groupSeparators + decimalLength + sign;
    }

    @Override
    public String print(Object obj) {
        return moneyf.format(((Number) obj).doubleValue());
    }

    @Override
    public boolean isRightAligned() {
        return true;
    }
}
