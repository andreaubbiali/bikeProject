package bikeProject.exception;

public class AccessDeniedException extends Exception {

    public AccessDeniedException() {
        super("The user can't access, something went wrong");
    }
}