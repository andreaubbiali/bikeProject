package bikeProject.exception;

public class WrongPasswordException extends Exception {

    public WrongPasswordException() {
        super("The inserted password is wrong");
    }

}