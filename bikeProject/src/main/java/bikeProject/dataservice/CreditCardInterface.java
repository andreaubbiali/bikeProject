package bikeProject.dataservice;

import bikeProject.exception.InvalidCreditCardException;

import java.sql.SQLException;
import java.util.Date;

public interface CreditCardInterface {

    void registerNewCreditCard(long userID, long number, long cvv, Date expireDate) throws SQLException, InvalidCreditCardException;

}