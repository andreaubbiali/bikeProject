package bikeProject.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bikeProject.PasswordUtils;
import bikeProject.dataservice.User;
import bikeProject.exception.UserNotFoundException;
import bikeProject.exception.WrongPasswordException;

public class UserDatabase extends Database implements UserDatabaseInterface {

    public void login(User user, String email, String password) throws SQLException, WrongPasswordException {

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE email = ? LIMIT 1;");
        statement.setString(1, email);
        ResultSet res = statement.executeQuery();

        if ( !checkPassword(res, password) ) {
            throw new WrongPasswordException();
        }

        user.setUser(res.getInt("id"), res.getString("name"), res.getString("surname"), res.getString("email"),
                res.getBoolean("isStudent"));

        res.close();
    }

    public int registerNewUser(String name, String surname, String email, boolean isStudent, String password,
                               String salt) throws SQLException {

        // prepare the statement
        PreparedStatement statement = conn.prepareStatement("INSERT INTO user (name, surname, email, password, " +
                "salt, is_student) VALUES (?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, name);
        statement.setString(2, surname);
        statement.setString(3, email);
        statement.setString(4, password);
        statement.setString(5, salt);
        statement.setBoolean(6, isStudent);

        // execute the query
        int res = statement.executeUpdate();
        if ( res == 0 ) {
            throw new SQLException("Register user failed, no rows affected.");
        }

        try ( ResultSet generatedKeys = statement.getGeneratedKeys() ) {
            if ( generatedKeys.next() ) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Register user failed, no ID obtained.");
            }
        }

    }

    public boolean checkPasswordByID(long id, String password) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE id = ? LIMIT 1;");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();

        boolean valid = checkPassword(res, password);
        res.close();

        return valid;
    }

    public void getUser(User user) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE id = ? LIMIT 1;");
        statement.setLong(1, user.getID());
        ResultSet res = statement.executeQuery();

        user.setUser(res.getInt("id"), res.getString("name"), res.getString("surname"), res.getString("email"),
                res.getBoolean("isStudent"));

        res.close();

    }

    private boolean checkPassword(ResultSet res, String password) throws SQLException {

        while ( res.next() ) {

            // Encrypted and Base64 encoded password read from database
            String dbPassword = res.getString("password");

            // Salt value stored in database
            String salt = res.getString("salt");

            return PasswordUtils.verifyUserPassword(password, dbPassword, salt);
        }

        return false;
    }

}