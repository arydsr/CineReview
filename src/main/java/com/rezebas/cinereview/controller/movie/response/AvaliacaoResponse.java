package com.rezebas.cinereview.controller.movie.response;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoResponse {
    private String titulo;
    private String filmeId;
    private String mensagem;
}
