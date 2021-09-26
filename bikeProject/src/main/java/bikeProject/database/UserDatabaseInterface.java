package bikeProject.database;

import bikeProject.dataservice.User;
import bikeProject.dataservice.UserGeneric;
import bikeProject.exception.UserNotFoundException;
import bikeProject.exception.WrongPasswordException;

import java.sql.SQLException;

public interface UserDatabaseInterface {

    UserGeneric login(String email, String password) throws SQLException, WrongPasswordException;

    int registerNewUser(String name, String surname, String email, boolean isStudent, String password, String salt) throws SQLException;

    boolean checkPasswordByID(long id, String password) throws SQLException;

    /*void getUser(User user) throws SQLException;*/
}