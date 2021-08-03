package bikeProject.database;

import java.util.Date;
import java.sql.SQLException;

public interface CreditCardDatabaseInterface {

    int registerNewCreditCard(long userid, long number, long cvv, Date expireDate) throws SQLException;
}