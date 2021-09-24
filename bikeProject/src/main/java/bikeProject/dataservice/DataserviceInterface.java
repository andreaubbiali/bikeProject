package bikeProject.dataservice;

import bikeProject.config.Config;
import bikeProject.database.*;

public interface DataserviceInterface {

    Config config = Config.getInstance();

    UserDatabaseInterface userDB = new UserDatabase();
    SubscriptionDatabaseInterface subscriptionDB = new SubscriptionDatabase();
    CreditCardDatabaseInterface credCardDB = new CreditCardDatabase();
    RentDatabaseInterface rentDB = new RentDatabase();
    RackPositionDatabaseInterface rackDB = new RackPositionDatabase();
    BikeDatabaseInterface bikeDB = new BikeDatabase();
    DamagedBikeDatabaseInterface damagedBikeDB = new DamagedBikeDatabase();
    TariffDatabaseInterface tariffDB = new TariffDatabase();
    SubscriptionTypeDatabaseInterface subTypeDB = new SubscriptionTypeDatabase();

}