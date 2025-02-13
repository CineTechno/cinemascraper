package com.cinemascraper.utils;

import com.cinemascraper.model.EventModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser {

    public static List<EventModel> jsonParser(String rawText) throws IOException {

        try{
            Pattern pattern = Pattern.compile("\\[[^\\[\\]]*\\]");

            Matcher matcher = pattern.matcher(rawText);
            String extractedString = null;
            if (matcher.find()) {
                extractedString = matcher.group();
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            return mapper.readValue(extractedString, new TypeReference<List<EventModel>>() {});

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    }

