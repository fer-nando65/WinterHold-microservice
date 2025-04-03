package com.winterhold.library_service.helper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CustomHelper {
    public static String BuildFullName(String title, String firstName, String lastName){
        return String.format("%s %s %s",
                title.substring(0,1).toUpperCase()+ title.substring(1) + ((title.equals("miss")) ? "" : ". "),
                firstName,
                lastName
                );
    }

    public static String CheckStatus(LocalDate date){
        return (date == null) ? "Alive" : "Deceased";
    }

    public static Integer AgeCounter(LocalDate date){
        return (int) ChronoUnit.YEARS.between(date, LocalDate.now());
    }
}
