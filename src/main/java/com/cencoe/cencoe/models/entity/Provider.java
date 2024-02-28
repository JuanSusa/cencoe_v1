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

  //  @Column(name = "provider_state")
  //private Boolean providerState;

    @Column(name = "provider_email")
    private String providerEmail;

    @Column(name = "provider_details")
    private String providerDetails;

    @Column(name = "provider_contact")
    private String providerContact;


    @ManyToOne(targetEntity = DocumentType.class, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "provider_doctype")
    private DocumentType providerDocType;


    //relacion de un proveedor a muchas campañas
    /*@OneToMany(targetEntity = Campaign.class, fetch = FetchType.LAZY, mappedBy = "campaignProvider")
    @JsonBackReference
    //@JsonIgnoreProperties({"campaignProvider", "hibernateLazyInitializer", "handler"})
    private List<Campaign> campaigns;*/
}
