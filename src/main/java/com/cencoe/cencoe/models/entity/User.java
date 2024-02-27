package com.cencoe.cencoe.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements Serializable {

    private final static Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_numdoc", unique = true)
    private String userNumDoc;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_lastname")
    private String userLastName;

    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_email", unique = true)
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_state")
    private Boolean userState;

    //Relacion de muchos usuarios a un tipo de docuemento
    @ManyToOne(targetEntity = DocumentType.class, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "user_doctype")
    private DocumentType userDocType;

    //Relacion de muchos usuarios a muchos equipos
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_team",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> teams;

//    //Relacion de muchos usuarios a muchos equipos
//    @ManyToMany
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(referencedColumnName = "user_id"),
//            inverseJoinColumns = @JoinColumn(referencedColumnName = "role_id"))
//    private List<Role> roles;

//    //Relacion de muchos usuarios a muchos equipos
//    @ManyToMany(targetEntity = Team.class, fetch = FetchType.LAZY)
//    @JoinTable(name = "user_team")
//    private List<Calls> callsList;

}
