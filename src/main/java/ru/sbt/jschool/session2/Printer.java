package ru.sbt.jschool.session2;

import java.util.List;

public interface Printer {
    List<Class<?>> supported();
    int length(Object obj);
    String print(Object obj);
}