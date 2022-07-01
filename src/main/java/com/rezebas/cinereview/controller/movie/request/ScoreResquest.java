package com.rezebas.cinereview.controller.movie.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreResquest {

    @NotNull(message = "O campo User Email deve ser preenchido!")
    @JsonProperty(namespace = "user_email")
    private String email;

    @NotNull(message = "O campo Title deve ser preenchido!")
    private String title;

    @NotNull(message = "O campo ID deve ser preenchido!")
    private String movieId;

    //regex 0 a 5
    @NotNull(message = "O campo Score deve ser preenchido!")
    @Size(min = 1, max = 1 , message = "O campo Score deve conter numeros de 0 a 5")
    private int score;
}