package rvt;

import java.util.Scanner;

public class MainTodo {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TodoList todoList = new TodoList();

        UserInterface ui = new UserInterface(todoList, scanner);
        ui.start();
    }
}

