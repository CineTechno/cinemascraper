package com.cinemascraper.service.aiscrapers;

import com.cinemascraper.model.EventModel;
import com.cinemascraper.model.FilmModel;
import com.cinemascraper.utils.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatPromptTemplate;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScraperWajda {

    ChatClient chatClient;

    public ScraperWajda(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    public List<EventModel> scrapeWajda() {
        List<FilmModel> list = new ArrayList<>();
        try {
            Document website = Jsoup.connect("https://ckf.waw.pl/kalendarz-wydarzen/").get();
            String visibleText = website.body().text();
            String prompt = " Look through this code. I have filtered it so there is only visible text on the " +
                    "website, so there are fewer tokens for you. Extract events and return only a" +
                    " JSON array in strict JSON format. Do not include any additional text," +
                    " explanations, or formatting. Don't add any more text before or after" +
                    " the json structure, not even commas, or apostrophes.. Here is the code: {visibleText}" +
                    """
                    Extract information from each event and return it in this format
                    dateAndTime: in LOCALDATETIME format
                    title:
                    organiser: CKF im. Andrzeja Wajdy
                    description:
                    imagePath:here I want you to propose an appropriate image to the event basing on its title and the description. Best if it's a
                    royalty free public link.

                     
                    """;



            String response =  chatClient.prompt()
            .user(u->u.text(prompt)
                    .param("visibleText", visibleText))
                            .call()
                            .content();

            List<EventModel> eventList = JsonParser.jsonParser(response);
            return eventList;

//

        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
