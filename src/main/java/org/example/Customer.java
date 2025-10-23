package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Customer {

    private final String personalIdentityNumber;
    private final String phoneNumber;
    private final String email;
    private final String name;
    private final HashMap<Item, Integer> itemList;
    private MembershipInterface membership;
    private int bonusPoints;

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

        this.itemList = new HashMap<Item, Integer>();
        this.membership = new NoMembership();
    }

    public String getPersonalIdentityNumber() {return personalIdentityNumber;}

    public String getPhoneNumber() {return phoneNumber;}

    public String getEmail() {return email;}

    public String getName() {return name;}

    public boolean isMember() {
        return membership.isActive();
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public HashMap<Item, Integer> getItemList() {
        return new HashMap<>(itemList);
    }

    public void addItem(Item item) {
        for(Item i : itemList.keySet()) {
            if (i.equals(item)) {
                int x = itemList.get(i) + 1;
                itemList.put(i, x);
            }
        }
        itemList.putIfAbsent(item, 1);
    }

    public int getBonusPoints() {
        if(membership.isActive()) {
            return bonusPoints;
        }
        return 0;
    }
//hej
    public void addOrSubtractBonusPoints(long bonusPoints) {
        if(membership.isActive()) {
            if(this.bonusPoints + bonusPoints < 0) {
                this.bonusPoints = 0;
            } else {
                this.bonusPoints += bonusPoints;
            }
        }
    }

    @Override
    public String toString() {
        return "Name: " + name + ", PID: " + personalIdentityNumber +
                ", Phone Number: " + phoneNumber + ", Email: " + email;
    }

}
