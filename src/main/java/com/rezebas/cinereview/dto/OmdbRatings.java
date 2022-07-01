package com.rezebas.cinereview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmdbRatings {

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Value")
    private String value;

}
