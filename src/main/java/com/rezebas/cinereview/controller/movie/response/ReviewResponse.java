package com.rezebas.cinereview.controller.movie.response;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private String title;
    private String movieId;
    private String message;
}
