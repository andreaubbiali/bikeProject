package bikeProject.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("The payment has been refused, there are some problem with the bank");
    }

}