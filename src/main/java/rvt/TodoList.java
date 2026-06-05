package rvt;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoList {
    private ArrayList<String> tasks;
    private String databaseUrl;

    public TodoList() {
        this.tasks = new ArrayList<>();
        this.databaseUrl = null;
    }

    public void loadFromFile(String filename) {
        this.databaseUrl = "jdbc:sqlite:" + filename;
        createTable();
        loadTasks();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(this.databaseUrl);
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "task TEXT NOT NULL"
                + ")";

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Neizdevās izveidot tabulu: " + e.getMessage());
        }
    }

    private void loadTasks() {
        this.tasks.clear();

        String sql = "SELECT task FROM tasks ORDER BY id";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                this.tasks.add(resultSet.getString("task"));
            }

        } catch (SQLException e) {
            System.out.println("Neizdevās nolasīt datubāzi: " + e.getMessage());
        }
    }

    private void saveToDatabase() {
        if (this.databaseUrl == null) {
            return;
        }

        String deleteSql = "DELETE FROM tasks";
        String insertSql = "INSERT INTO tasks(task) VALUES(?)";

        try (Connection connection = connect()) {
            connection.setAutoCommit(false);

            try (Statement deleteStatement = connection.createStatement()) {
                deleteStatement.executeUpdate(deleteSql);
            }

            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                for (String task : this.tasks) {
                    insertStatement.setString(1, task);
                    insertStatement.executeUpdate();
                }
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Neizdevās saglabāt datubāzē: " + e.getMessage());
        }
    }

    public void add(String task) {
        this.tasks.add(task);
        saveToDatabase();
    }

    public void print() {
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println((i + 1) + ": " + this.tasks.get(i));
        }
    }

    public void remove(int number) {
        int index = number - 1;
        this.tasks.remove(index);
        saveToDatabase();
    }
}
