package bikeProject.database;

import bikeProject.dataservice.CreditCard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreditCardDatabase implements CreditCardDatabaseInterface {

    public List<CreditCard> getCreditCardByUserID(long userID) throws SQLException {
        List<CreditCard> creditCards = new ArrayList<CreditCard>();

        PreparedStatement statement = Database.getConn().prepareStatement("SELECT * FROM credit_card WHERE user_id = "
                + "? ORDER BY expire_date DESC;");
        statement.setLong(1, userID);
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {

            creditCards.add(new CreditCard(res.getLong("id"), res.getLong("number"), res.getLong("cvv"), res.getDate(
                    "expire_date")));
        }

        return creditCards;
    }

    public long registerNewCreditCard(long userid, long number, long cvv, Date expireDate) throws SQLException {

        PreparedStatement statement = Database.getConn().prepareStatement("INSERT INTO credit_card (number, cvv, " +
                "expire_date, user_id) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, number);
        statement.setLong(2, cvv);
        statement.setDate(3, (java.sql.Date) expireDate);
        statement.setLong(4, userid);

        // execute the query
        statement.execute();

        // get the id
        try ( ResultSet generatedKeys = statement.getGeneratedKeys() ) {
            if ( generatedKeys.next() ) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Register credit card failed, no ID obtained.");
            }
        }
    }
}