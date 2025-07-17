package ru.sbt.jschool.session2;

import java.util.*;

public class DataFormatter {

    private final Map<Class<?>, Printer> knownPrinters = new HashMap<>();

    private final Printer anyPrinter = new Printer() {
        @Override
        public List<Class<?>> supported() {
            return Collections.emptyList();
        }

        @Override
        public String print(Object obj) {
            return Objects.toString(obj, "-");
        }

        @Override
        public int length(Object obj) {
            return obj == null ? 1 : print(obj).length();
        }

        @Override
        public boolean isRightAligned() {
            return false;
        }


    };
    private Printer printerFor(Object obj) {
        if (obj == null) return anyPrinter;
        return knownPrinters.getOrDefault(obj.getClass(), anyPrinter);
    }

    public DataFormatter() {
        registerPrinter(new StringPrinter());
        registerPrinter(new DatePrinter());
        registerPrinter(new DoublePrinter());
        registerPrinter(new NumberPrinter());
    }

    private void registerPrinter(Printer printer) {
        for (Class<?> cls : printer.supported()) {
            knownPrinters.put(cls, printer);
        }
    }


    public String format(Object value) {
        return printerFor(value).print(value);
    }

    public int length(Object value) {
        return printerFor(value).length(value);
    }

    public boolean isRightAligned(Object value, Class<?> expectedType) {
        if (value != null) {
            return printerFor(value).isRightAligned();
        }
        if (expectedType != null) {
            if (String.class.isAssignableFrom(expectedType)) return false;
            if (Number.class.isAssignableFrom(expectedType)) return true;
            if (Date.class.isAssignableFrom(expectedType)) return true;
        }
        return false;
    }
}