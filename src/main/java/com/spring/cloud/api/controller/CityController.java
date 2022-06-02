package com.spring.cloud.api.controller;

import com.spring.cloud.api.controller.request.SuggestionRequest;
import com.spring.cloud.api.controller.response.Suggestion;
import com.spring.cloud.api.services.CityService;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CityController {

	private final CityService citiService;

	@GetMapping("/suggestions")
	@ResponseBody
	public Map findCities(SuggestionRequest request) throws FileNotFoundException {
		Map<String,List<Suggestion>> map =new HashMap<String,List<Suggestion>>();
        List<Suggestion> suggestions =citiService.findSuggestions(request);
		map.put("suggestions",suggestions);
		return map;
	}



}
