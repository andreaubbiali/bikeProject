package bikeProject.database;

import bikeProject.dataservice.CreditCard;

import java.util.Date;
import java.sql.SQLException;
import java.util.List;

public interface CreditCardDatabaseInterface {

    long registerNewCreditCard(long userid, long number, long cvv, Date expireDate) throws SQLException;

    List<CreditCard> getCreditCardByUserID(long userID) throws SQLException;
}