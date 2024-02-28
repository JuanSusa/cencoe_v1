package com.cencoe.cencoe.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "provider")
public class Provider implements Serializable {

    private final static Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private Long providerId;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "provider_address")
    private String providerAddress;

    @Column(name = "provider_state")
    private Boolean providerState;

    //relacion de un proveedor a muchas campañas
    @OneToMany(targetEntity = Campaign.class, fetch = FetchType.LAZY, mappedBy = "provider")
    @JsonBackReference
    private List<Campaign> campaigns;
}
