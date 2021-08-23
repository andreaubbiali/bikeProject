package bikeProject.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CreditCardDatabase extends Database implements CreditCardDatabaseInterface {

    public long registerNewCreditCard(long userid, long number, long cvv, Date expireDate) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("INSERT INTO credit_card (number, cvv, expire_date, user_id) VALUES (?,?,?);");
        statement.setLong(1, number);
        statement.setLong(2, cvv);
        statement.setDate(3, (java.sql.Date) expireDate);
        statement.setLong(4, userid);

        // execute the query
        statement.execute();

        // get the id
        ResultSet resSet = statement.getGeneratedKeys();
        long id = 0;
        if ( resSet.next() ) {
            id = resSet.getLong(1);
        }

        return id;
    }
}