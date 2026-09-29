package com.loose.coupling;

public class UserManager {

//    private UserDatabaseProvider userDatabaseProvider = new UserDatabaseProvider();
//    public String getUserDetails() {
//        return userDatabaseProvider.getUserDetails();
//    }


    private final UserDataProvider userDataProvider;

    public UserManager(UserDataProvider userDataProvider) {
        this.userDataProvider = userDataProvider;
    }

    public String getUserDetails() {
        return userDataProvider.getUserDetails();
    }
}