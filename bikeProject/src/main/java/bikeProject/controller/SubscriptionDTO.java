package bikeProject.controller;

import bikeProject.dataservice.Subscription;
import bikeProject.dataservice.User;

import java.time.LocalDate;

public class SubscriptionDTO {

    public long ID;
    public String typeName;
    public LocalDate subscriptionDate;
    public User user;
    public int countExceededTime;
    public LocalDate startDate;
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

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCountExceededTime() {
        return countExceededTime;
    }

    public void setCountExceededTime(int countExceededTime) {
        this.countExceededTime = countExceededTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}