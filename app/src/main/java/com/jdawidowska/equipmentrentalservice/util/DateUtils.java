package com.jdawidowska.equipmentrentalservice.util;

public class DateUtils {

    public static boolean validateReturnDate(String returnDate) {
        if(returnDate != null && !returnDate.equals("") && !returnDate.equals("null")) {
            return true;
        } else {
            return false;
        }
    }
}
