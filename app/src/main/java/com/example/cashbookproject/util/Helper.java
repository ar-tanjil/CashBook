package com.example.cashbookproject.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public static String formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        return dateFormat.format(date);
    }

}