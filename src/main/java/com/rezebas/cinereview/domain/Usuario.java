package com.rezebas.cinereview.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String nome;

    @NotNull(message = "O campo Email deve ser preenchido!")
    @Email
    private String email;

    @NotNull(message = "O campo Senha deve ser preenchido!")
    @Size(min = 8)
    private String senha;

    @JsonIgnore
    private int pontos;

    @OneToMany(mappedBy = "userClient")
    private List<Pontos> scores = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Perfil perfil;

    @OneToMany(mappedBy = "userClient")
    private List<Reacoes> reacoes = new ArrayList<>();

}
