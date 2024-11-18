package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.sql.Delete;

import javax.persistence.Query;
import javax.security.auth.login.Configuration;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final String sqlCreateTable = "CREATE TABLE if not exists User " +
            "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(128) NOT NULL," +
            "lastName VARCHAR(128) NOT NULL ," +
            "age TINYINT NOT NULL );";

    private static final String sqlDropTable = "DROP TABLE IF EXISTS User;";
    private static final String sqlCleanUsersTable = "TRUNCATE User;";

    private static List <User> users;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            Session session = Util.openConnectionToDatabase().openSession();
            session.beginTransaction();
            session.createSQLQuery(sqlCreateTable).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ошибка при создании таблицы");
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.openConnectionToDatabase().openSession();
            session.beginTransaction();
            session.createSQLQuery(sqlDropTable).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ошибка при удалении таблицы");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = Util.openConnectionToDatabase().openSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем - "+name+" добавлен в базу данных" );
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении юзера");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = Util.openConnectionToDatabase().openSession();
            session.beginTransaction();
            session.createQuery("delete User WHERE id = " + id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ошибка при удалении юзера");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            Session session = Util.openConnectionToDatabase().openSession();
            session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Ошибка при получении списка юзеров");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.openConnectionToDatabase().openSession();
            session.beginTransaction();
            session.createSQLQuery(sqlCleanUsersTable).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ошибка при очистке таблицы юзеров");
        }
    }
}
