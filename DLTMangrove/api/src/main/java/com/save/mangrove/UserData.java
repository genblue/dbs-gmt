package com.save.mangrove;

public class UserData {

    String id;

    String firstName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(boolean kycStatus) {
        this.kycStatus = kycStatus;
    }

    String lastName;

    String email;

    boolean kycStatus;
}
