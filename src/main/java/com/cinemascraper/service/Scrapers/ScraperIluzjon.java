package com.cinemascraper.service.Scrapers;

import com.cinemascraper.model.FilmModel;
import com.cinemascraper.utils.DateParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ScraperIluzjon extends Scraper{

    public ScraperIluzjon(
            @Value("${scraper.iluzjon.date-selector}") String dateSelector,
            @Value("${scraper.iluzjon.title-selector}") String titleSelector,
            @Value("${scraper.iluzjon.url}") String url,
            @Value("${scraper.iluzjon.show-time-selector}") String showtimeSelector) {
        super(dateSelector, titleSelector, url);
    };

    @Override
    public List<FilmModel> getFilmSchedule() {
        String currentDate = "";
        LocalDateTime dateShowTime;
        List<FilmModel> listOfFilms = new ArrayList<>();
        try {
            Document website = Jsoup.connect("https://www.iluzjon.fn.org.pl/repertuar.html").get();
            Elements elements = website.select("h3, span.hour a");
            for (Element element : elements) {
                if (element.is("h3")) {
                    currentDate = element.text();
                    System.out.println(currentDate);
                } else if (element.is("span.hour a")) {
                    Pattern pattern = Pattern.compile("(\\d{1,2}:\\d{2}) - (.+)");
                    Matcher matcher = pattern.matcher(element.text());
                    String filmURL = "https://www.iluzjon.fn.org.pl/" +element.attr("href");
                    Map<String,String> filmDetails = getFilmDetails(filmURL);
                    String description = filmDetails.getOrDefault("description", "");
                    String director = filmDetails.getOrDefault("director","");
                    String year = filmDetails.getOrDefault("year", "");
                    String imgPath = filmDetails.getOrDefault("imgPath","");
                    if (matcher.find()) {
                        String title = matcher.group(2);
                        String showtime = matcher.group(1);
                        dateShowTime = dateShowTimeFormatter(currentDate, showtime);

                        FilmModel existingFilm = listOfFilms.stream().filter(f -> f.getTitle().equals(title)).findFirst().orElse(null);
                        if (existingFilm != null) {
                            existingFilm.getDateShowTime().add(dateShowTime);
                        } else {
                            FilmModel filmModel = new FilmModel("Iluzjon", title, description, director, year, imgPath, new ArrayList<>(List.of(dateShowTime)));
                            listOfFilms.add(filmModel);

                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfFilms;
    }

    public Map<String, String> getFilmDetails(String url) {
        Map<String, String> detailsMap = new HashMap<>();
        try {
            Document website = Jsoup.connect(url).get();
            String description = website.select("div.content p:nth-of-type(2) ").text();
            Element imgPathLink = website.selectFirst("div.movie-cover img");
            String imgPath = (imgPathLink!=null)?"https://www.iluzjon.fn.org.pl/" + imgPathLink.attr("src"):"";
            String director = website.select("div.content.movie-info.clearfix dl:nth-of-type(5) dd").text();
            String year = website.select("div.content.movie-info.clearfix dl:nth-of-type(4) dd").text();
            detailsMap.put("description", description);
            detailsMap.put("director", director);
            detailsMap.put("year", year);
            detailsMap.put("imgPath", imgPath);
            System.out.println(detailsMap);
            return detailsMap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LocalDateTime dateShowTimeFormatter(String date, String showtime){
        String formattedDate = DateParser.parseDateIluzjon(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(formattedDate + " " + showtime, formatter);
    }
}
