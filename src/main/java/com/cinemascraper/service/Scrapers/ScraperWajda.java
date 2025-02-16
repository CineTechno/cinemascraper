package com.cinemascraper.service.Scrapers;


import com.cinemascraper.model.EventModel;
import com.cinemascraper.utils.DateParser;
import jdk.jfr.Event;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScraperWajda extends Scraper {

    public ScraperWajda(
            @Value("${scraper.wajda.date-selector}") String dateSelector,
            @Value("${scraper.wajda.title-selector}") String titleSelector,
            @Value("${scraper.wajda.url}") String url,
            @Value("${scraper.wajda.show-time-selector}") String showTimeSelector)

     {
        super(dateSelector,titleSelector,url,showTimeSelector);
    }


    public List<EventModel> getEventSchedule() {
        List<EventModel> eventList = new ArrayList<>();
        try {
            Document website = Jsoup.connect(url).get();
            Elements block = website.select(".wp-block-post");
            for (Element element : block) {
                String title = element.select(titleSelector).text();
                String date = element.select(dateSelector).text();
                String showTime = element.select(showTimeSelector).text();
                LocalDateTime dateAndTime  = DateParser.parseDateWajda(date, showTime);
                String description = element.select(".kalendarz-lead").text();
                String link = element.select(".kalendarz-link").attr("href");
                EventModel eventModel = new EventModel(title, dateAndTime,"CKF Wajda", description, link);
                eventList.add(eventModel);
            }
        }catch (IOException e){}
        return eventList;
    }

    @Override
    public Map<String, String> getFilmDetails(String title) {
        return Map.of();
    }
}
