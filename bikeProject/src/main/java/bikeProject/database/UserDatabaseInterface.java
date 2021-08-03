package bikeProject.database;

import bikeProject.dataservice.User;
import bikeProject.exception.UserNotFoundException;

import java.sql.SQLException;

public interface UserDatabaseInterface {

    User login(String username, String password) throws SQLException;

    int registerNewUser(String name, String surname, String username, String email, boolean isStudent, String password, String salt) throws SQLException;
}