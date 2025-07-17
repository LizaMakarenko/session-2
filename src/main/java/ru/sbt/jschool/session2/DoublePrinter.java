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
        return print(obj).length();
    }


    @Override
    public String print(Object obj) {
        return moneyf.format(((Number) obj).doubleValue());
    }
}
