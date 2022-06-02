package com.spring.cloud.api.services.impl;

import com.spring.cloud.api.controller.request.SuggestionRequest;
import com.spring.cloud.api.controller.response.Suggestion;
import com.spring.cloud.api.services.CityService;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CityServiceImpl implements CityService {

    List<Suggestion> suggestions;

    @PostConstruct
    private void init() throws IOException, URISyntaxException {
        log.info("Building.x....");
        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        TsvParser parser = new TsvParser(settings);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("cities_canada-usa.tsv");
        List<String[]> allRows = parser.parseAll(inputStream);
        List<Suggestion> suggestions =new ArrayList<>();
        int i=0;
        for(String str[]:allRows){
            if(i!=0){
                Suggestion suggestion =new Suggestion();
                for(String s:str){

                    if(str[4]!=null){
                        try {
                            suggestion.setLatitude(Double.valueOf(str[4]));
                        }catch (NumberFormatException n){

                        }
                    }
                    if(str[5]!=null){
                        try {
                            suggestion.setLongitude(Double.valueOf(str[5]));
                        }catch (NumberFormatException n){

                        }
                    }

                    if(str[8]!=null){
                        if(str[8].contains("CA")){
                            suggestion.setScore(0.3);
                        }
                        else if(str[8].contains("US")){
                            suggestion.setScore(0.5);
                        }else{
                            suggestion.setScore(0.9);
                        }

                    }
                    if(str[1]!=null){
                        suggestion.setName(str[1]);
                    }

                }
                suggestions.add(suggestion);
            }
            i++;
        }
        this.suggestions=suggestions;
    }

    @Override
    public List<Suggestion> findSuggestions(SuggestionRequest suggestionRequest) {
        Double latitude= suggestionRequest.getLatitude();
        Double longitude= suggestionRequest.getLongitude();
        List<Suggestion> r=new ArrayList<>();
        if(latitude!=null && longitude!=null && suggestionRequest.getQ()==null)
            r =suggestions.stream().filter(x->( (x.getLatitude()>=latitude-5 && x.getLatitude()<=latitude+5) && (x.getLongitude()>=longitude-5 && x.getLongitude()<=longitude+5))).collect(Collectors.toList());
        else if(latitude!=null && longitude!=null && suggestionRequest.getQ()!=null && !suggestionRequest.getQ().isEmpty()){
            r =suggestions.stream().filter(x->( (x.getLatitude()>=latitude-5 && x.getLatitude()<=latitude+5) && (x.getLongitude()>=longitude-5 && x.getLongitude()<=longitude+5)
                    && x.getName().contains(suggestionRequest.getQ())
            )).collect(Collectors.toList());
        }
        return r;
    }
}
