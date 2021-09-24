package bikeProject.dataservice;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import bikeProject.PasswordUtils;
import bikeProject.config.Config;
import bikeProject.exception.*;

public class User implements DataserviceInterface {

    private static User user = null;

    private static /* @ not_null @ */ long ID;
    private static /* @ not_null @ */ String name;
    private static /* @ not_null @ */ String surname;
    private static /* @ not_null @ */ String email;
    private static /* @ not_null @ */ boolean isStudent;
    private static List<CreditCard> creditCard;
    private static List<Subscription> subscription;

    private User(long id, String name, String surname, String email, boolean isStudent) throws SQLException {
        setID(id);
        setName(name);
        setSurname(surname);
        setEmail(email);
        setIsStudent(isStudent);
        setCreditCard(credCardDB.getCreditCardByUserID(id));
        Subscription subscription = new Subscription();
        setSubscription(subscription.getSubscriptionByUser(this));
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

        user = new User(id, name, surname, email, isStudent);
    }

    /**
     * @param password
     * @throws UserNotFoundException
     * @throws SQLException
     */
    public static void login(String email, String password) throws SQLException, WrongPasswordException {

        userDB.login(email, password);
        System.out.println("New login from: " + email);
    }

    public static void setUser(long id, String name, String surname, String email, boolean isStudent) throws SQLException {
        user = new User(id, name, surname, email, isStudent);
    }

    /**
     * @param number
     * @param cvv
     * @param expireDate
     * @throws SQLException
     */
    public void addCreditCard(long number, long cvv, LocalDate expireDate) throws SQLException,
            InvalidCreditCardException {

        // register the new credit card
        CreditCard newCreditCard = new CreditCard(number, cvv, expireDate, this);

        creditCard.add(newCreditCard);
    }

    public static Subscription getValidSubscription() throws InvalidSubscriptionException {
        Subscription subscriptionRes = new Subscription();
        boolean found = false;

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

    /**
     * @param subType
     * @param selectedCreditCard
     * @return
     * @throws InvalidCreditCardException
     * @throws PaymentException
     */
    public void addSubscription(SubscriptionType subType, CreditCard selectedCreditCard) throws SQLException,
            InvalidCreditCardException, PaymentException {

        // check if the credit card is valid for the subscription selected
        if ( !selectedCreditCard.isCreditCardValidForSubscription(subType) ) {
            throw new InvalidCreditCardException("The selected creditCard isn't valid for the subscription");
        }

        // creation of the subscription
        Subscription subscription = new Subscription();
        subscription.createNewSubscription(this, subType);

        // payment for the subscription
        selectedCreditCard.pay(subType.getPrice());

        this.subscription.add(subscription);

    }

    /**
     * request to the university to check if the user is a student
     */
    public static boolean checkIsUserAStudent() {

        // check if is in production or test mode
        if ( Config.getInstance().isProductionMode() ) {

            try {
                // this.isStudent = send request to university
            } catch ( Exception e ) {
                e.printStackTrace();
            }

        } else {
            // get mocked response for test
            isStudent = Config.getInstance().getUniversityMockResponse();

        }
        return isStudent;
    }

    /**
     * @param password
     * @return
     */
    public boolean checkPassword(String password) throws SQLException {

        return userDB.checkPasswordByID(this.ID, password);
    }

    public CreditCard getCreditCardValidForSubscription(Subscription subscription) throws InvalidCreditCardException {

        for ( CreditCard creditCard : creditCard ) {
            if ( creditCard.isCreditCardValidForSubscription(subscription.getType()) ) {
                return creditCard;
            }
        }

        throw new InvalidCreditCardException("There are no valid credit card");
    }

    /*
    public boolean isValidUniqueCodeSubscriptionForUser(String uniqueCode, String password) throws SQLException {
        Subscription subscription = new Subscription(uniqueCode);

        // TODO fix
        // login(subscription.getUserID(), password);

        // check the validity of the subscription
        return subscription.isValid();

    }*/

    /*public void setUser(User user) throws SQLException {
        setID(user.ID);
        setName(user.name);
        setSurname(user.surname);
        setEmail(user.email);
        setIsStudent(user.isStudent);
        setCreditCard(credCardDB.getCreditCardByUserID(id));
    }*/

    // GETTERS AND SETTERS
    public long getID() {
        return ID;
    }

    public void setID(long iD) {
        ID = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setIsStudent(boolean isStudent) {
        this.isStudent = isStudent;
    }

    public List<CreditCard> getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(List<CreditCard> creditCard) {
        this.creditCard = creditCard;
    }

    public List<Subscription> getSubscription() {
        return subscription;
    }

    public void setSubscription(List<Subscription> subscription) {
        this.subscription = subscription;
    }
}