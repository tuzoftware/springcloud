package com.spring.cloud.api.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Suggestion {

    private String name;
    private Double latitude;
    private Double longitude;
    private Double score;


}
