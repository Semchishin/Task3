package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private List<User> usersList = new ArrayList<>();
    private ResultSet usersSet;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Util.openConnectionToDatabase();
        try {
            Statement statement = Util.connection.createStatement();
            statement.execute("CREATE TABLE user(" +
                                        "id serial," +
                                        "name VARCHAR(128)," +
                                        "lastname VARCHAR(128)," +
                                         "age SMALLINT);");
        } catch (SQLException e) {
            System.out.println("Таблица уже существет");
        }
        Util.openConnectionToDatabase();
    }

    public void dropUsersTable() {
        Util.openConnectionToDatabase();
        try {
            Statement statement = Util.connection.createStatement();
            statement.execute("DROP TABLE user;");
        } catch (SQLException e) {
            System.out.println("Таблица не существет");
        }
        Util.closeConnectionToDatabase();
    }

    public void saveUser(String name, String lastName, byte age) {
        Util.openConnectionToDatabase();
        try {
            Statement statement = Util.connection.createStatement();
            statement.execute("INSERT INTO user (name, lastname, age) " +
                                        "VALUES ('" +name + "','" +
                                        lastName + "','" +
                                        age + "');");
            System.out.println("User с именем - "+name+" добавлен в базу данных" );
        } catch (SQLException e) {
            System.out.println("Юзер не добавлен");
            e.printStackTrace();
        }
        Util.closeConnectionToDatabase();
    }

    public void removeUserById(long id) {
        Util.openConnectionToDatabase();
        try {
            Statement statement = Util.connection.createStatement();
            statement.execute("DELETE FROM user " +
                                        "WHERE id =" + id+";");
        } catch (SQLException e) {
            System.out.println("Юзер не удален");
            e.printStackTrace();
        }
        Util.closeConnectionToDatabase();
    }

    public List<User> getAllUsers() {
        Util.openConnectionToDatabase();
        try {
            Statement statement = Util.connection.createStatement();
           usersSet = statement.executeQuery("SELECT * FROM user");
            while (usersSet.next()) {
                usersList.add(new User(usersSet.getString("name"),
                                    usersSet.getString("lastname"),
                                    usersSet.getByte("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Util.closeConnectionToDatabase();
        return usersList;

    }

    public void cleanUsersTable() {
        Util.openConnectionToDatabase();
        try {
            Statement statement = Util.connection.createStatement();
            statement.execute("TRUNCATE TABLE user;");
        } catch (SQLException e) {
            System.out.println("Таблица не существет");
        }
        Util.closeConnectionToDatabase();
    }
}
