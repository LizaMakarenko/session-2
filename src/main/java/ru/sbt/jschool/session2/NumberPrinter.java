package ru.sbt.jschool.session2;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;

public class NumberPrinter implements Printer {
    private final DecimalFormat numberFormat;

    public NumberPrinter() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');

        numberFormat = new DecimalFormat("###,###");
        numberFormat.setDecimalFormatSymbols(symbols);
        numberFormat.setGroupingUsed(true);
        numberFormat.setGroupingSize(3);
    }

    @Override
    public List<Class<?>> supported() {
        return Arrays.asList(Byte.class, Short.class, Integer.class, Long.class);
    }

    @Override
    public String print(Object obj) {
        if (obj instanceof Number) {
            return numberFormat.format(((Number) obj).longValue());
        }
        return "-";
    }

    @Override
    public int length(Object obj) {
        return print(obj).length();
    }
}
