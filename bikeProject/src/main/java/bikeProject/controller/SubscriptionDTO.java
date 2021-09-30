package bikeProject.controller;

import bikeProject.dataservice.Subscription;
import bikeProject.dataservice.User;
import bikeProject.dataservice.UserGeneric;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SubscriptionDTO {

    public long ID;
    public String typeName;
    public LocalDateTime subscriptionDate;
    public UserGeneric user;
    public int countExceededTime;
    public LocalDateTime startDate;
    public boolean deleted;

    public SubscriptionDTO(Subscription subscription) {
        this.ID = subscription.getID();
        this.typeName = subscription.getType().getName();
        this.subscriptionDate = subscription.getSubscriptionDate();
        this.user = subscription.getUser();
        this.countExceededTime = subscription.getCountExceededTime();
        this.startDate = subscription.getStartDate();
        this.deleted = subscription.isDeleted();
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public UserGeneric getUser() {
        return user;
    }

    public void setUser(UserGeneric user) {
        this.user = user;
    }

    public int getCountExceededTime() {
        return countExceededTime;
    }

    public void setCountExceededTime(int countExceededTime) {
        this.countExceededTime = countExceededTime;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}