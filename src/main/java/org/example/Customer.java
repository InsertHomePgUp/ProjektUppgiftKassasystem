package org.example;

import java.util.ArrayList;
import java.util.Objects;

public class Customer {

    private final String personalIdentityNumber;
    private final String phoneNumber;
    private final String email;
    private final String name;
    private boolean hasMembership;
    private final ArrayList<Item> boughtItemList;

    public Customer(String name, String personalIdentityNumber, String phoneNumber, String email) {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (personalIdentityNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Personal identity number cannot be null or empty");
        }
        if (phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.name = Objects.requireNonNull(name);
        this.personalIdentityNumber = Objects.requireNonNull(personalIdentityNumber);
        this.phoneNumber = Objects.requireNonNull(phoneNumber);
        this.email = Objects.requireNonNull(email);
        this.boughtItemList = new ArrayList<Item>();
        this.hasMembership = false;
    }
    public String getPersonalIdentityNumber() {return personalIdentityNumber;}
    public String getPhoneNumber() {return phoneNumber;}
    public String getEmail() {return email;}
    public String getName() {return name;}
    public boolean getMembershipStatus() {return hasMembership;}
    public void setMembershipStatus(boolean areTheyAMember) {hasMembership = areTheyAMember;}
    public ArrayList<Item> getBoughtItemList() {
        return new ArrayList<>(boughtItemList);
    }
    public boolean addItem(Item item) {
        boughtItemList.add(item);
        return true;
    }
    @Override
    public String toString() {
        return "Name: " + name + ", PID: " + personalIdentityNumber +
                ", Phone Number: " + phoneNumber + ", Email: " + email;
    }
}
