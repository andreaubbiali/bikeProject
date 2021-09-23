package bikeProject.controller;

import bikeProject.dataservice.User;
import bikeProject.exception.UserNotFoundException;
import bikeProject.exception.WrongPasswordException;

import java.sql.SQLException;

public class Controller_User {
    public User user;

    public Controller_User() {
        if ( user == null ) {
            this.user = new User();
        }
    }

    public void login(String username, String password) throws UserNotFoundException, WrongPasswordException,
            SQLException {

        user.login(username, password);

    }

    public void registerUser(String name, String surname, String email, String password, boolean isStudent) throws SQLException {

        user.registerNewUser(name, surname, email, password, isStudent);
    }

}