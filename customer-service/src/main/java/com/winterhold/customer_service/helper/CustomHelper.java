package com.winterhold.customer_service.helper;

public class CustomHelper {

    public static String buildFullName(String firstName, String lastName){
        return String.format("%s %s", firstName, lastName);
    }
}
