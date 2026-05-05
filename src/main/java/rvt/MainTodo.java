package rvt;

import java.util.Scanner;

public class MainTodo {
    public static void main(String[] args) {
        TodoList todoList = new TodoList();
UserInterface ui = new UserInterface(todoList);
ui.start();

}

    }