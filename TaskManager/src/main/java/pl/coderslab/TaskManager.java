package pl.coderslab;

import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.lang3.ArrayUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static String[][] tasks;

    public static void main(String[] args) {

        tasks = loadDataToTab("tasks.csv");

        System.out.println(ConsoleColors.PURPLE_BRIGHT + "Please select an option:" + ConsoleColors.RESET);
        System.out.println("* add");
        System.out.println("* remove");
        System.out.println("* list");
        System.out.println("* exit");


        Scanner newscan = new Scanner(System.in);

        try {
            while (newscan.hasNextLine()) {
                String input1 = newscan.nextLine();
                switch (input1) {
                    case "add":
                        addTask();
                        break;
                    case "remove":
                        removeTask();
                        break;
                    case "list":
                        listTask();
                        break;
                    case "exit":
                        exitTask();
                        break;
                    default:
                        System.out.println("Please select a correct option.");
                }
            }
        } catch (InputMismatchException e) {
        }
    }

    private static void exitTask() {
        System.out.println(ConsoleColors.RED_BRIGHT + "Bye, bye :-)" + ConsoleColors.RESET);
        System.exit(0);
    }

    private static void listTask() {
        Path path1 = Paths.get("tasks.csv");
        int i = 0;
        try {
            for (String line : Files.readAllLines(path1)) {
                i++;
                System.out.println(i +": "+ line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(ConsoleColors.PURPLE_BRIGHT + "Please select an option:" + ConsoleColors.RESET);
        System.out.println("* add");
        System.out.println("* remove");
        System.out.println("* list");
        System.out.println("* exit");
    }

    private static void removeTask() {

        tasks = loadDataToTab("tasks.csv");
        Scanner delete = new Scanner(System.in);
        System.out.println(ConsoleColors.BLACK_BACKGROUND + ConsoleColors.WHITE_BOLD_BRIGHT+"Please give me number of table which you'd like to delete."+ConsoleColors.RESET);


        try {
            while (delete.hasNextLine()) {
            String n = delete.nextLine();
            int delete1 = Integer.parseInt(n);
            if (delete1< tasks.length) {
                tasks = ArrayUtils.remove(tasks, delete1);
                System.out.println("Value was successfully delete.");
            }}
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element not exist in tab");

        }

        System.out.println(ConsoleColors.PURPLE_BRIGHT + "Please select an option:" + ConsoleColors.RESET);
        System.out.println("* add");
        System.out.println("* remove");
        System.out.println("* list");
        System.out.println("* exit");

    }
    private static void addTask() {

        Scanner desc = new Scanner(System.in);
        System.out.println("Please give me description for your task.");
        String desc1 = desc.nextLine();

        Scanner date = new Scanner(System.in);
        System.out.println("Please give me date for your task.(date format = YYYY-MM-DD)");
        String date1 = date.nextLine();

        Scanner impor = new Scanner(System.in);
        System.out.println("Please tell your task is important: true or false");
        String impor1 = impor.nextLine();

        Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length-1] = new String[3];
        tasks[tasks.length-1][0]=desc1;
        tasks[tasks.length-1][1]=date1;
        tasks[tasks.length-1][2]=impor1;

        Path dir = Paths.get("tasks.csv");

        String[] lines = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            lines[i] = String.join(",", tasks[i]);
        }

        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println(ConsoleColors.PURPLE_BRIGHT + "Please select an option:" + ConsoleColors.RESET);
        System.out.println("* add");
        System.out.println("* remove");
        System.out.println("* list");
        System.out.println("* exit");
    }

    public static String[][] loadDataToTab(String fileName) {
        Path dir = Paths.get(fileName);
        if (!Files.exists(dir)) {
            System.out.println("File not exist.");
            System.exit(0);
        }

        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(dir);
            tab = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tab[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }
}

