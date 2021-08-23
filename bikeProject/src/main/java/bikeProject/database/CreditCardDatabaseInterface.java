package bikeProject.database;

import java.util.Date;
import java.sql.SQLException;

public interface CreditCardDatabaseInterface {

    long registerNewCreditCard(long userid, long number, long cvv, Date expireDate) throws SQLException;
}