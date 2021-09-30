package bikeProject.dataservice;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import bikeProject.PasswordUtils;
import bikeProject.config.Config;
import bikeProject.exception.*;

public class User extends UserGeneric implements DataserviceInterface {

    private static User user = null;

    public User(long id, String name, String surname, String email, boolean isStudent, boolean isAdmin) throws SQLException {
        super(id, name, surname, email, isStudent, isAdmin);
    }

    private User(UserGeneric userGeneric) throws SQLException {
        super(userGeneric);
    }

    public static User getInstance() throws AccessDeniedException {
        if ( user == null ) {
            throw new AccessDeniedException();
        }
        return user;
    }

    /**
     * @param name     string the name of the user
     * @param surname  string the surname of the user
     * @param email    string the email of the user
     * @param password string the password of the user
     */
    public static void registerNewUser(String name, String surname, String email, String password, boolean isStudent) throws SQLException {

        // Generate Salt. The generated value can be stored in DB.
        String salt = PasswordUtils.getSalt(30);

        // Protect user's password. The generated value can be stored in DB as pw.
        String mySecurePassword = PasswordUtils.generateSecurePassword(password, salt);

        if ( isStudent ) {
            // check with the university if the user is a student
            isStudent = checkIsUserAStudent();
        }

        int id;

        // Add user into DB
        id = userDB.registerNewUser(name, surname, email, isStudent, mySecurePassword, salt);

        System.out.println("New user registered: " + email);

        user = new User(id, name, surname, email, isStudent, false);
    }

    public static void login(String email, String password) throws SQLException, WrongPasswordException {

        UserGeneric userGeneric = userDB.login(email, password);

        user = new User(userGeneric);

        System.out.println("New login from: " + email);
    }

    public static void logout() {
        user = null;
    }

    public void addCreditCard(long number, long cvv, LocalDate expireDate) throws SQLException,
            InvalidCreditCardException {

        // register the new credit card
        CreditCard newCreditCard = new CreditCard(number, cvv, expireDate, this);

        addNewCreditCard(newCreditCard);
    }

    public static Subscription getValidSubscription() throws InvalidSubscriptionException {
        Subscription subscriptionRes = new Subscription();
        boolean found = false;

        List<Subscription> subscription = getSubscriptionList();
        // inverted loop
        for ( int i = subscription.size() - 1; i >= 0; i-- ) {
            subscriptionRes = subscription.get(i);

            // check if the subscription is valid
            if ( subscriptionRes.isValid() ) {
                found = true;
                break;
            }
        }

        if ( !found ) {
            throw new InvalidSubscriptionException("There are no subscription valid for the user");
        }

        return subscriptionRes;
    }

    public static Subscription getSubscriptionByRent(Rent rent) {
        Subscription result = null;

        for ( Subscription sub : getSubscriptionList() ) {
            for ( Rent r : sub.getRentList() ) {
                if ( r.getID() == rent.getID() ) {
                    result = sub;
                    break;
                }
            }
        }

        return result;
    }

    public void addSubscription(SubscriptionType subType, CreditCard selectedCreditCard) throws SQLException,
            InvalidCreditCardException, PaymentException, InvalidSubscriptionException {

        // check if the credit card is valid for the subscription selected
        if ( !selectedCreditCard.isCreditCardValidForSubscription(subType) ) {
            throw new InvalidCreditCardException("The selected creditCard isn't valid for the subscription");
        }

        // check that user has not other active subscription
        for ( Subscription sub : getSubscriptionList() ) {
            if ( sub.isValid() ) {
                throw new InvalidSubscriptionException("You have an active subscription or a subscription that can " + "be" + " used");
            }
        }

        // creation of the subscription
        Subscription subscription = new Subscription();
        subscription.createNewSubscription(this, subType);

        // payment for the subscription
        selectedCreditCard.pay(subType.getPrice());

        addNewSubscription(subscription);

    }

    public static Rent activeUserRent() {

        for ( Subscription subscription : getSubscriptionList() ) {
            Rent rent = subscription.isThereAnActiveRent();
            if ( rent != null ) {
                return rent;
            }
        }

        return null;
    }

    public static Rent lastUserRent() throws SQLException {
        Rent lastRent = new Rent();

        for ( Subscription subscription : getSubscriptionList() ) {
            List<Rent> rentList = subscription.getRentList();
            for ( Rent r : rentList ) {
                if ( r.getEndDate().compareTo(lastRent.getEndDate()) > 0 ) {
                    lastRent = r;
                }
            }
        }

        return lastRent;

    }

    /**
     * request to the university to check if the user is a student
     */
    public static boolean checkIsUserAStudent() throws SQLException {

        // check if is in production or test mode
        if ( Config.getInstance().isProductionMode() ) {

            try {
                // this.isStudent = send request to university
            } catch ( Exception e ) {
                e.printStackTrace();
            }

        } else {
            // get mocked response for test
            setIsStudent(Config.getInstance().getUniversityMockResponse());

        }

        userDB.updateIsStudent(getID(), getIsStudent());

        return getIsStudent();
    }

    public boolean checkPassword(String password) throws SQLException {

        return userDB.checkPasswordByID(getID(), password);
    }

    public static CreditCard getCreditCardValidForSubscription(Subscription subscription) throws InvalidCreditCardException {

        for ( CreditCard creditCard : getCreditCardList() ) {
            if ( creditCard.isCreditCardValidForSubscription(subscription.getType()) ) {
                return creditCard;
            }
        }

        throw new InvalidCreditCardException("There are no valid credit card");
    }
}