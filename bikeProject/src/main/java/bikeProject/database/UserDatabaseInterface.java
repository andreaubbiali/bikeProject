package bikeProject.database;

import bikeProject.dataservice.User;
import bikeProject.exception.UserNotFoundException;

import java.sql.SQLException;

public interface UserDatabaseInterface {

    User login(String email, String password) throws SQLException;

    int registerNewUser(String name, String surname, String email, boolean isStudent, String password, String salt) throws SQLException;

    boolean checkPassword(long id, String password) throws SQLException;
}