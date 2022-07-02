package com.rezebas.cinereview.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String texto;

    private String username;

    private boolean repeated;

    private int like;

    private int dislike;

    @ManyToOne
    @JoinColumn(name = "movie_client_id")
    private Filme movieClient;

    @OneToMany(mappedBy = "review")
    private List<Reacoes> reacoes = new ArrayList<>();

    private List<ReAvaliacao> avaliacaos = new ArrayList<>();


}
