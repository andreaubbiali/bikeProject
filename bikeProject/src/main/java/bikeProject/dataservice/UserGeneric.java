package bikeProject.dataservice;

import java.sql.SQLException;
import java.util.List;

public class UserGeneric implements DataserviceInterface {
    private static /* @ not_null @ */ long ID;
    private static /* @ not_null @ */ String name;
    private static /* @ not_null @ */ String surname;
    private static /* @ not_null @ */ String email;
    private static /* @ not_null @ */ boolean isStudent;
    private static List<CreditCard> creditCardList;
    private static List<Subscription> subscriptionList;
    private static boolean isAdmin;

    public UserGeneric(UserGeneric userGeneric) {
    }

    public UserGeneric(long id, String name, String surname, String email, boolean isStudent, boolean isAdmin) throws SQLException {
        setID(id);
        setName(name);
        setSurname(surname);
        setEmail(email);
        setIsStudent(isStudent);
        setCreditCardList(credCardDB.getCreditCardByUserID(id));
        Subscription subscription = new Subscription();
        setSubscriptionList(subscription.getSubscriptionByUser(this));
        setIsAdmin(isAdmin);
    }

    public void addNewCreditCard(CreditCard cr) {
        creditCardList.add(cr);
    }

    public void addNewSubscription(Subscription sub) {
        subscriptionList.add(sub);
    }

    public static long getID() {
        return ID;
    }

    public static void setID(long ID) {
        UserGeneric.ID = ID;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserGeneric.name = name;
    }

    public static String getSurname() {
        return surname;
    }

    public static void setSurname(String surname) {
        UserGeneric.surname = surname;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserGeneric.email = email;
    }

    public static boolean getIsStudent() {
        return isStudent;
    }

    public static void setIsStudent(boolean isStudent) {
        UserGeneric.isStudent = isStudent;
    }

    public static List<CreditCard> getCreditCardList() {
        return creditCardList;
    }

    public static void setCreditCardList(List<CreditCard> creditCard) {
        UserGeneric.creditCardList = creditCard;
    }

    public static List<Subscription> getSubscriptionList() {
        return subscriptionList;
    }

    public static void setSubscriptionList(List<Subscription> subscription) {
        UserGeneric.subscriptionList = subscription;
    }

    public static boolean getIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        UserGeneric.isAdmin = isAdmin;
    }

}