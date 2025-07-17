package ru.sbt.jschool.session2;

import java.util.Collections;
import java.util.List;

public class StringPrinter implements Printer {
    @Override
    public List<Class<?>> supported() {
        return Collections.singletonList(String.class);
    }

    @Override
    public int length(Object obj) {
        return ((String) obj).replace("\n", " ").length();
    }

    @Override
    public String print(Object obj) {
        return ((String) obj).replace("\n", " ");
    }
}
