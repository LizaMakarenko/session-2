package ru.sbt.jschool.session2;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DatePrinter implements Printer {
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public List<Class<?>> supported() {
        return Arrays.asList(Date.class);
    }

    @Override
    public int length(Object obj) {
        return 10;
    }

    @Override
    public String print(Object obj) {
        return sdf.format((Date) obj);
    }
}
