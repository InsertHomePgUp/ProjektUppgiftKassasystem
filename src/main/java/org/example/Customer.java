package org.example;

public class Customer {

    private final long personalIdentityNumber;
    private final long phoneNumber;
    private final String email;
    private final String name;

    public Customer(String name, long personalIdentityNumber, long phoneNumber, String email) {
        this.name = name;
        this.personalIdentityNumber = personalIdentityNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public long getPersonalIdentityNumber() {return personalIdentityNumber;}
    public long getPhoneNumber() {return phoneNumber;}
    public String getEmail() {return email;}
    public String getName() {return name;}

}
