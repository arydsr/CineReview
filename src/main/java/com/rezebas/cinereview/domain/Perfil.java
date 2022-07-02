package com.rezebas.cinereview.domain;

import com.rezebas.cinereview.enums.PerfilEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
public class Perfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @MapsId
    private Usuario usuario;

    @Id
    @Enumerated(EnumType.STRING)
    private PerfilEnum profileName;

    @Override
    public String toString(){
        return profileName.toString();
    }
}
