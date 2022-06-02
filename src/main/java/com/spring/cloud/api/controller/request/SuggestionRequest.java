package com.spring.cloud.api.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SuggestionRequest {
    private Double latitude;
    private Double longitude;
    private String q;
}
