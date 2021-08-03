package bikeProject.database;

import java.sql.SQLException;
import java.util.Date;

public class CreditCardDatabase implements CreditCardDatabaseInterface {

    public int registerNewCreditCard(long userid, long number, long cvv, Date expireDate) throws SQLException {
        return 4;
    }
}