package com.tight.coupling;

public class UserManager {
    private UserDatabase userDatabase = new UserDatabase();

    public String getUserDetails() {
        return userDatabase.getUserDetails();
    }
}