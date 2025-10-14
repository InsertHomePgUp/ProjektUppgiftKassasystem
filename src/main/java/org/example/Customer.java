package org.example;

import java.util.ArrayList;
import java.util.Objects;

public class Customer {

    private final String personalIdentityNumber;
    private final String phoneNumber;
    private final String email;
    private final String name;
    private boolean hasMembership;
    private final ArrayList<Item> itemList;

    public Customer(String name, String personalIdentityNumber, String phoneNumber, String email) {
        this.name = Objects.requireNonNull(name);
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.personalIdentityNumber = Objects.requireNonNull(personalIdentityNumber);
        if (personalIdentityNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Personal identity number cannot be empty");
        }
        this.phoneNumber = Objects.requireNonNull(phoneNumber);
        if (phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        this.email = Objects.requireNonNull(email);
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        this.itemList = new ArrayList<Item>();
        this.hasMembership = false;
    }

    public String getPersonalIdentityNumber() {return personalIdentityNumber;}
    public String getPhoneNumber() {return phoneNumber;}
    public String getEmail() {return email;}
    public String getName() {return name;}
    public boolean getMembershipStatus() {return hasMembership;}
    public void setMembershipStatus(boolean areTheyAMember) {hasMembership = areTheyAMember;}

    public ArrayList<Item> getItemList() {
        return new ArrayList<>(itemList);
    }
    public boolean addItem(Item item) {
        //osäker här, tror det inte bör gå att lägga till items med samma namn

        for(Item i : itemList) {
            if (i.getName().equals(item.getName())) {
                return false;
            }
        }
        itemList.add(item);
        return true;
    }
    @Override
    public String toString() {
        return "Name: " + name + ", PID: " + personalIdentityNumber +
                ", Phone Number: " + phoneNumber + ", Email: " + email;
    }
}
