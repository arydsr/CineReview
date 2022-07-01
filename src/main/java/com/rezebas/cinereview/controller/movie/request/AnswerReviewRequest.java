package com.rezebas.cinereview.controller.movie.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerReviewRequest {

    @NotNull(message = "O campo User Email deve ser preenchido!")
    @JsonProperty(namespace = "user_email")
    private String email;

    @NotNull(message = "O campo Answer deve ser preenchido!")
    @JsonProperty(namespace = "answer")
    private String answerText;
}

