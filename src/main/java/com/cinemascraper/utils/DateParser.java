package com.cinemascraper.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateParser {

    public static String parseDateMuranow(String polishDate)  {
        Map<String,String> monthMap = new HashMap<String,String>();
        monthMap.put("stycznia", "01");
        monthMap.put("lutego", "02");
        monthMap.put("marca", "03");
        monthMap.put("kwietnia", "04");
        monthMap.put("maja", "05");
        monthMap.put("czerwca", "06");
        monthMap.put("lipca", "07");
        monthMap.put("sierpnia", "08");
        monthMap.put("września", "09");
        monthMap.put("października", "10");
        monthMap.put("listopada", "11");
        monthMap.put("grudnia", "12");

        String day = polishDate.replaceAll("[^0-9]","");
        String monthName = polishDate.replaceAll("^[a-zA-Ząćęłńóśźż]+", "").replaceAll("\\d", "");
        String month = monthMap.get(monthName);


        return String.format("2025-%s-%s", month, day );
    }




}
