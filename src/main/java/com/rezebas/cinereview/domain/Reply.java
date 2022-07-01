package com.rezebas.cinereview.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply {

    @JsonProperty("username")
    private String username;

    @JsonProperty("answer")
    private String answerTxt;
}
