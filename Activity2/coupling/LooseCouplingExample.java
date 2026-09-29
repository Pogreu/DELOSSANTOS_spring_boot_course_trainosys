package com.loose.coupling;

public class LooseCouplingExample {

    public static void main(String[] args) {
        UserDataProvider databaseProvider = new UserDatabaseProvider();
        UserManager userManager = new UserManager(databaseProvider);

        System.out.println("User Details: " + userManager.getUserDetails());
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");

        UserDataProvider webServiceProvider = new WebServiceDataProvider();
        UserManager userManagerWeb = new UserManager(webServiceProvider);

        System.out.println("Web Output: " + userManagerWeb.getUserDetails());

        UserDataProvider dbServiceProvider = new NewDatabaseProvider();
        UserManager userManagerDb = new UserManager(dbServiceProvider);

        System.out.println("DB Output: " + userManagerDb.getUserDetails());
    }
}