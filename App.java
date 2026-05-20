import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String [] args){
        try (
            Connection connection = 
            DriverManager.getConnection(
                "jdbc:sqlite:todo.db"
            );
            Statement statement = connection.createStatement();
    )
    {
        String sql = "CREATE TABL IF NOT EXISTS todo"
        + "(id INTEGER PRIMRY KEY, task TEXT NOT NULL) STRICT";
        statement.executeUpdate(sql);
    } catch (SQLException e) { }
    }
}