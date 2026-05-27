package rvt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TodoDB {
    private static final String DB_URL = "jdbc:sqlite:todo.db";

    public TodoDB() {
        initSchema();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void initSchema() {
        String sql = "CREATE TABLE IF NOT EXISTS todo ("
            + "id INTEGER PRIMARY KEY,"
            + "task TEXT NOT NULL) STRICT";
        try (
            Connection conn = connect();
            Statement stmt = conn.createStatement()
        ) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Schema init failed: " + e.getMessage());
        }
    }
     // add
    public void add(String task) {
        String sql = "INSERT INTO todo(task) VALUES(?)";

        try (
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, task);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Neizdevās pievienot uzdevumu: " + e.getMessage());
        }
    }
    // find
    public List<String> findAll() {
        String sql = "SELECT task FROM todo";
        List<String> tasks = new ArrayList<>();

        try (
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                tasks.add(rs.getString("task"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Neizdevās ielādēt uzdevumus: " + e.getMessage());
        }

        return tasks;
    }
    //delete
    public void removeById(int id) {
        String sql = "DELETE FROM todo WHERE id = ?";

        try (
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Neizdevās dzēst uzdevumu: " + e.getMessage());
        }
    }
}