package bikeProject.dataservice;

import bikeProject.config.Config;
import bikeProject.database.*;

public interface DataserviceInterface {

    UserDatabaseInterface userDB = new UserDatabase();
    SubscriptionDatabaseInterface subscriptionDB = new SubscriptionDatabase();
    CreditCardDatabaseInterface credCardDB = new CreditCardDatabase();
    RentDatabaseInterface rentDB = new RentDatabase();
    RackPositionDatabaseInterface rackPositionDB = new RackPositionDatabase();
    BikeDatabaseInterface bikeDB = new BikeDatabase();
    DamagedBikeDatabaseInterface damagedBikeDB = new DamagedBikeDatabase();
    TariffDatabaseInterface tariffDB = new TariffDatabase();
    SubscriptionTypeDatabaseInterface subTypeDB = new SubscriptionTypeDatabase();
    BikeTypeDatabaseInterface bikeTypeDB = new BikeTypeDatabase();
    TotemRackDatabaseInterface totemRackDB = new TotemRackDatabase();

}