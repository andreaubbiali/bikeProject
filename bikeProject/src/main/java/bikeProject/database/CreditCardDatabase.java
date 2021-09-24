package bikeProject.database;

import bikeProject.dataservice.CreditCard;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class CreditCardDatabase implements CreditCardDatabaseInterface {

    public List<CreditCard> getCreditCardByUserID(long userID) throws SQLException {
        List<CreditCard> creditCards = new ArrayList<>();

        PreparedStatement statement = Database.getConn().prepareStatement("SELECT * FROM credit_card WHERE user_id = "
                + "? ORDER BY expire_date DESC;");
        statement.setLong(1, userID);
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {
            LocalDate expireDate =
                    Instant.ofEpochMilli(res.getDate("expire_date").getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            creditCards.add(new CreditCard(res.getLong("id"), res.getLong("number"), res.getLong("cvv"), expireDate));
        }

        return creditCards;
    }

    public long registerNewCreditCard(long userid, long number, long cvv, LocalDate expireDate) throws SQLException {

        PreparedStatement statement = Database.getConn().prepareStatement("INSERT INTO credit_card (number, cvv, " +
                "expire_date, user_id) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, number);
        statement.setLong(2, cvv);
        statement.setDate(3, Date.valueOf(expireDate));
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