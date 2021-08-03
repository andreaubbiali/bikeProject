package bikeProject.dataservice;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import bikeProject.PasswordUtils;
import bikeProject.exception.InvalidCreditCardException;
import bikeProject.exception.PaymentException;
import bikeProject.exception.UserNotFoundException;
import bikeProject.exception.WrongPasswordException;

public class User implements DataserviceInterface {

    /**
     * @invariant
     */

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ String name;
    private /* @ not_null @ */ String surname;
    private /* @ not_null @ */ String username;
    private /* @ not_null @ */ String email;
    private /* @ not_null @ */ boolean isStudent;
    private List<CreditCard> creditCard;
    private List<Subscription> subscription;

    /**
     * @param name     string the name of the user
     * @param surname  string the surname of the user
     * @param email    string the email of the user
     * @param username string the username of the user
     * @param password string the password of the user
     */
    public void registerNewUser(String name, String surname, String username, String email, String password, boolean isStudent) throws SQLException {

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
        id = userDB.registerNewUser(name, surname, username, email, isStudent, mySecurePassword, salt);
        // set user attributes
        setUser(id, name, surname, username, email, isStudent);

        System.out.println("New user registered: " + this.username);
    }

    /**
     * @param username
     * @param password
     * @throws UserNotFoundException
     * @throws SQLException
     */
    public void login(String username, String password) throws UserNotFoundException, SQLException {

        User user = userDB.login(username, password);
        if ( user == null ) {
            throw new UserNotFoundException();
        }
        setUser(user);

        System.out.println("New login from: " + username);
    }

    /**
     * @param number
     * @param cvv
     * @param expireDate
     * @throws SQLException
     */
    public void addCreditCard(long number, long cvv, Date expireDate) throws SQLException, InvalidCreditCardException {

        CreditCard creditCard = new CreditCard();
        // register the new credit card
        creditCard.registerNewCreditCard(this.ID, number, cvv, expireDate);

        this.creditCard.add(creditCard);
    }

    /**
     * @param subType
     * @param selectedCreditCard
     * @return
     * @throws InvalidCreditCardException
     * @throws PaymentException
     */
    public String addSubscription(String password, SubscriptionType subType, CreditCard selectedCreditCard) throws WrongPasswordException, SQLException, InvalidCreditCardException, PaymentException {

        // check the user password to add subscription
        if ( !checkPassword(password) ) {
            throw new WrongPasswordException();
        }

        // check if the credit card is valid for the subscription selected
        if ( !selectedCreditCard.isCreditCardValidForSubscription(subType) ) {
            throw new InvalidCreditCardException("The selected creditCard isn't valid for the subscription");
        }

        // creation of the subscription
        Subscription subscription = new Subscription();
        String uniqueCode = subscription.createNewSubscription(this.ID, subType, selectedCreditCard.getID());

        // payment for the subscription
        selectedCreditCard.pay(subType.getPrice());
        
        this.subscription.add(subscription);

        return uniqueCode;
    }

    /**
     * request to the university to check if the user is a student
     */
    public boolean checkIsUserAStudent() {

        // check if is in production or test mode
        if ( config.IsProductionMode() ) {

            try {
                // this.isStudent = send request to university
            } catch ( Exception e ) {
                e.printStackTrace();
            }

        } else {
            // get mocked response for test
            this.isStudent = config.getUniversityMockResponse();

        }
        return this.isStudent;
    }

    /**
     * @param password
     * @return
     */
    public boolean checkPassword(String password) throws SQLException {

        return userDB.checkPassword(this.ID, password);
    }

    /*  *//**
     * @param uniqueCode
     * @param password
     * @return
     * @throws SQLException
     *//*
    public boolean isValidUniqueCodeSubscriptionForUser(String uniqueCode, String password) throws SQLException {
        Subscription subscription = new Subscription(uniqueCode);

        // TODO fix
        // login(subscription.getUserID(), password);

        // check the validity of the subscription
        return subscription.isValid();

    }*/

    /**
     * @param id
     * @param name
     * @param surname
     * @param username
     * @param email
     * @param isStudent
     */
    public void setUser(int id, String name, String surname, String username, String email, boolean isStudent) {
        setID(id);
        setName(name);
        setSurname(surname);
        setUsername(username);
        setEmail(email);
        setIsStudent(isStudent);
    }

    /**
     * @param user
     */
    public void setUser(User user) {
        setID(user.ID);
        setName(user.name);
        setSurname(user.surname);
        setUsername(user.username);
        setEmail(user.email);
        setIsStudent(user.isStudent);
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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