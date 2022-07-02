package com.rezebas.cinereview.controller.movie.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoRequest {

    @NotNull(message = "O campo ID deve ser preenchido!")
    private String filmeId;

    @NotNull(message = "O campo Title deve ser preenchido!")
    private String titulo;

    @NotNull(message = "O campo Review deve ser preenchido!")
    @JsonProperty(namespace = "review")
    private String avaliacaoTexto;

    @NotNull(message = "O campo User Email deve ser preenchido!")
    @JsonProperty(namespace = "user_email")
    private String email;




}
