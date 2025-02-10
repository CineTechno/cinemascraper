package com.cinemascraper.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String strDay = polishDate.replaceAll("[^0-9]","");
        int day = Integer.parseInt(strDay);
        String monthName = polishDate.replaceAll("^[a-zA-Ząćęłńóśźż]+", "").replaceAll("\\d", "");
        String month = monthMap.get(monthName);


        return String.format("2025-%s-%02d", month, day );
    }


    public static String parseDateIluzjon (String polishDate)  {
        Map<String,String> monthMap = new HashMap<String,String>();
        monthMap.put("Stycznia", "01");
        monthMap.put("Lutego", "02");
        monthMap.put("Marca", "03");
        monthMap.put("Kwietnia", "04");
        monthMap.put("Maja", "05");
        monthMap.put("Czerwca", "06");
        monthMap.put("Lipca", "07");
        monthMap.put("Sierpnia", "08");
        monthMap.put("Września", "09");
        monthMap.put("Października", "10");
        monthMap.put("Listopada", "11");
        monthMap.put("Grudnia", "12");

        String strDay = polishDate.replaceAll("[^0-9]","");
        int day = Integer.parseInt(strDay);
        Pattern pattern = Pattern.compile("Stycznia|Lutego|Marca|Kwietnia|Maja|Czerwca|Lipca|Sierpnia|Września|Października|Listopada|Grudnia");
        Matcher matcher = pattern.matcher(polishDate);
        if(matcher.find()) {
            String month = monthMap.get(matcher.group());
            return String.format("2025-%s-%02d", month, day );
        }
        else return null;

    }

    public static String parseDateKinoteka(String dateString) {
        // Input dateString example: "23 maja 2024"
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("pl", "PL"));
        LocalDate date = LocalDate.parse(dateString, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(outputFormatter);
    }

}
