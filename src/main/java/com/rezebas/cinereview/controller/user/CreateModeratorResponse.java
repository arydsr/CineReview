package com.rezebas.cinereview.controller.user;

import lombok.*;


@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateModeratorResponse {

    private String username;
    private String perfil;
    private String mensagem;
}
