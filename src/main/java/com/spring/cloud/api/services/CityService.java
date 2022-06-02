package com.spring.cloud.api.services;

import com.spring.cloud.api.controller.request.SuggestionRequest;
import com.spring.cloud.api.controller.response.Suggestion;

import java.util.List;

public interface CityService {

    List<Suggestion> findSuggestions(SuggestionRequest suggestionRequest);

}
