package bikeProject.dataservice;

import bikeProject.exception.AccessDeniedException;
import bikeProject.exception.WrongPasswordException;

import java.sql.SQLException;

public class UserAdmin extends UserGeneric implements DataserviceInterface {

    private static UserAdmin userAdmin = null;

    public UserAdmin(UserGeneric userGeneric) {
        super(userGeneric);
    }

    public static UserAdmin getInstance() throws AccessDeniedException {
        if ( userAdmin == null ) {
            throw new AccessDeniedException();
        }
        return userAdmin;
    }

    public static void login(String email, String password) throws SQLException, WrongPasswordException,
            AccessDeniedException {

        UserGeneric userGeneric = userDB.login(email, password);

        if ( !getIsAdmin() ) {
            throw new AccessDeniedException();
        }

        userAdmin = new UserAdmin(userGeneric);

        System.out.println("New admin login from: " + email);
    }

}