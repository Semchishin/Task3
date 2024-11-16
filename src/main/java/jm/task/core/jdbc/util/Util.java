package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Mangust322";
    public static Connection connection;
    public static void openConnectionToDatabase() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Соединение не создано");
        }
    }

    public static void closeConnectionToDatabase() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при закрытии соединения с базой данных");
        }
    }
}
