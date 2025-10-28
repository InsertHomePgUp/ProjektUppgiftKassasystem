package org.example;

import java.util.HashMap;
import java.util.Objects;

public class Customer {

    private final String personalIdentityNumber;
    private final String phoneNumber;
    private final String email;
    private final String name;
    private final HashMap<Item, Integer> itemList;
    private MembershipInterface membership;
    private long bonusPoints;

    public Customer(String name, String personalIdentityNumber, String phoneNumber, String email) {
        Objects.requireNonNull(name);
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;

        Objects.requireNonNull(personalIdentityNumber);
        if (personalIdentityNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Personal identity number cannot be empty");
        }
        if (!pIDChecker(personalIdentityNumber)) {
            throw new IllegalArgumentException("Invalid personal identity number");
        }
        this.personalIdentityNumber = personalIdentityNumber;

        Objects.requireNonNull(phoneNumber);
        if (phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        if (!phoneNumberChecker(phoneNumber)) {
            throw new IllegalArgumentException("Phone number is not valid");
        }
        this.phoneNumber = phoneNumber;

        Objects.requireNonNull(email);
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        this.email =  email;

        this.itemList = new HashMap<Item, Integer>();
        this.membership = new NoMembership();
    }

    private boolean phoneNumberChecker(String phoneNumber) {
        char[] charArray = phoneNumber.toCharArray();
        if (charArray.length <= 16 && charArray.length >= 9 && charArray[0] == '+' && charArray[1] != '0') {
            for (int i = 1; i < charArray.length; i++) {
                if (!Character.isDigit(charArray[i])) {
                    return false;
                }
            }
            return true;
        }
        if (charArray.length <= 15 && charArray.length >= 8 && Character.isDigit(charArray[0]) && charArray[0] != '0') {
            for (char c : charArray) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
        }
        if (charArray.length <= 14 && charArray.length >= 7 && charArray[0] == '0' && charArray[1] != '0') {
            for (char c : charArray) {
                if(!Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean pIDChecker(String pID) {
        int total = 0;
        char[] charArray = pID.toCharArray();
        if (charArray.length == 11 && (charArray[6] == '+' || charArray[6] == '-')) {
            pID = pID.replace("-", "");
            charArray = pID.toCharArray();
            if(!pID.chars().allMatch(Character::isDigit)) return false;
            if(charArray.length == 10) {
                for (int i = 0; i < charArray.length-1; i++) {
                    if (i % 2 == 1) {
                        total += (charArray[i] - '0');
                    } else {
                        int d = (charArray[i] - '0')*2;
                        String s = String.valueOf(d);
                        if(s.length() == 2) {
                            int t = (s.charAt(0)-'0') + (s.charAt(1)-'0');
                            total += t;
                        } else {
                            total += (charArray[i] - '0')*2;
                        }
                    }
                }
                int x = 10 - (total % 10);
                return ((charArray[9] - '0') == x);
            }
        }
        return false;
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

    public long getBonusPoints() {
        if(membership.isActive()) {
            return bonusPoints;
        }
        return 0;
    }

    public void addOrSubtractBonusPoints(long bonusPoints) {
        if(membership.isActive()) {
            if(this.bonusPoints + bonusPoints < 0) {
                this.bonusPoints = 0;
            } else {
                this.bonusPoints += bonusPoints;
            }
        }
    }

    public double bonusPointsToMoney() {
        return (this.bonusPoints /100.0); //ger i kr
    }

    @Override
    public String toString() {
        return "Name: " + name + ", PID: " + personalIdentityNumber +
                ", Phone Number: " + phoneNumber + ", Email: " + email;
    }

}
