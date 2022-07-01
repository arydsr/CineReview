package com.rezebas.cinereview.domain;

import com.rezebas.cinereview.enums.ProfileEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @MapsId
    private User user;

    @Id
    @Enumerated(EnumType.STRING)
    private ProfileEnum profileName;

    @Override
    public String toString(){
        return profileName.toString();
    }
}
