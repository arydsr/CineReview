package com.rezebas.cinereview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingDto {

    private int id;
    @JsonProperty("movie_id")
    private String movieId;
    private String username;
    private int rating;
}
