package com.rezebas.cinereview.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
public class Reacoes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int like;

    private int dislike;

    @ManyToOne
    @JoinColumn(name = "review_client_id")
    private Avaliacao avaliacao;

    @ManyToOne
    @JoinColumn(name = "user_client_id")
    private Usuario userClient;
}
