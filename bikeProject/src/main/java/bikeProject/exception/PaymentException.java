package bikeProject.exception;

public class PaymentException extends Exception {

	public PaymentException() {
		super("The payment has been refused, there are some problem with the bank");
	}
}
