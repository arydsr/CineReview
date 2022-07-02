package com.rezebas.cinereview.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pontos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filme;

    @ManyToOne
    @JoinColumn(name = "user_client_id")
    private Usuario userClient;

    @ManyToOne
    @JoinColumn(name = "movie_client_id")
    private Filme movieClient;
}
