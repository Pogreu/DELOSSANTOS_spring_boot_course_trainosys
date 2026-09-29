package com.loose.coupling;

public class NewDatabaseProvider implements UserDataProvider {

    @Override
    public String getUserDetails() {
        return "User details from the POSTGREST SQL Database. Christian Ay Sobrang Pogi";
    }
}
