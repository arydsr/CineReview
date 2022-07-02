package com.rezebas.cinereview.controller.movie.response;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PontosResponse {
    private String titulo;
    private String imdbID;
    private String mensagem;
}
