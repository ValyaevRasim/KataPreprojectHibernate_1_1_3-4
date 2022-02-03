package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.transaction.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Transaction transaction = null;
        String strSql = "CREATE TABLE IF NOT EXISTS user("
                + "id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY ,"
                + "name varchar(30) NOT NULL, "
                + "lastName varchar(30) NOT NULL, "
                + "age tinyint DEFAULT 1 "
                + ");";
        try (PreparedStatement statement = connection.prepareStatement(strSql)){
            statement.executeUpdate(strSql);
            connection.commit();
            System.out.println("createUsersTable - OK");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        String strSql = "DROP TABLE IF EXISTS user;";
        try (PreparedStatement statement = connection.prepareStatement(strSql)){
            statement.executeUpdate(strSql);
            connection.commit();
            System.out.println("dropUsersTable - OK");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String strSql = "INSERT IGNORE INTO user(name, lastName, age ) VALUES(?, ?, ?);";
        try (PreparedStatement prStmnt = connection.prepareStatement(strSql)) {
            prStmnt.setString(1, name);
            prStmnt.setString(2, lastName);
            prStmnt.setByte(3, age);
            prStmnt.executeUpdate();
            connection.commit();
            System.out.println("saveUser - OK");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void removeUserById(long id) {
        String strSql = "DELETE FROM user WHERE id = ?;";

        try (PreparedStatement prStmnt = connection.prepareStatement(strSql)) {
            prStmnt.setLong(1, id);
            prStmnt.executeUpdate();
            connection.commit();
            System.out.println("removeUserById - OK");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String strSql = "SELECT * FROM user;";

        try (PreparedStatement prStmnt = connection.prepareStatement(strSql);
             ResultSet resultSet = prStmnt.executeQuery(strSql)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
                System.out.println("getAllUsers - OK");
            }
            connection.commit();
            Objects.requireNonNull(resultSet).close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return userList;
    }

    public void cleanUsersTable() {
        String strSql = "DELETE FROM user;";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(strSql);
            connection.commit();
            System.out.println("cleanUsersTable - OK");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
