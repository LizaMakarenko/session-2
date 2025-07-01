package ru.sbt.jschool.session2;

import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {
        OutputFormatter outputFormatter = new OutputFormatter(System.out);
        String[] headers = {"String", "Date", "Money", "Number"};
        if (args.length % 4 != 0) {
            System.out.println("Ошибка: количество аргументов должно быть кратно 4: (String, Date, Money, Number)");
            return;
        }
        int rows = args.length / 4;
        Object[][] data = new Object[rows][4];
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        for (int i = 0; i < rows; i++) {
            int index = i * 4;
            String str = args[index];
            data[i][0] = str.equalsIgnoreCase("null") ? null : str.replace("\n", " ");
            try {
                data[i][1] = args[index + 1].equalsIgnoreCase("null") ? null : sdf.parse(args[index + 1]);
            } catch (Exception e) {
                System.out.println("Неверный формат для date");
                return;
            }
            try {
                data[i][2] = args[index + 2].equalsIgnoreCase("null") ? null : Double.parseDouble(args[index + 2]);
            } catch (Exception e) {
                System.out.println("Неверный формат для поля money");
                return;
            }
            try {
                data[i][3] = args[index + 3].equalsIgnoreCase("null") ? null : Integer.parseInt(args[index + 3]);
            } catch (Exception e) {
                System.out.println("Неверный формат для number");
                return;
            }
        }
        outputFormatter.output(headers, data);
    }
}