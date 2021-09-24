package bikeProject.database;

import bikeProject.dataservice.CreditCard;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface CreditCardDatabaseInterface {

    long registerNewCreditCard(long userid, long number, long cvv, LocalDate expireDate) throws SQLException;

    List<CreditCard> getCreditCardByUserID(long userID) throws SQLException;
}