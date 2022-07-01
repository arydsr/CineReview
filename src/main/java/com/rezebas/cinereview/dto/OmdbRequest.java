package com.rezebas.cinereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmdbRequest {

    private String movieTitle;
    private String movieId;
    private String movieYear;

}
