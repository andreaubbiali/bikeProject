package bikeProject.database;

import bikeProject.dataservice.SubscriptionType;

public class SubscriptionTypeDatabase extends Database implements SubscriptionTypeDatabaseInterface {

    public SubscriptionType getTypeByID() {
        return new SubscriptionType();
    }

}