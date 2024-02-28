package com.cencoe.cencoe.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "campaign")
public class Campaign implements Serializable {

    private final static Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    private Long campaignId;

    @Column(name = "campaign_name")
    private String campaignName;

    @Column(name = "campaign_capacity")
    private Integer campaignCapacity;

    @Column(name = "campaign_start_date")
    private Date campaignStartDate;

    @Column(name = "campaign_end_date")
    private Date campaignEndDate;

    @Column(name = "campaign_observations")
    private String campaignObservations;

    @Column(name = "campaign_state")
    private Boolean campaignState;

    //Relacion de muchas campañas a un equipo
    @ManyToOne(targetEntity = Team.class)
    @JoinColumn(name = "campaign_team")
    private Team team;

    //Relacion de muchas campañas a un proveedor
    @ManyToOne(targetEntity = Provider.class)
    @JoinColumn(name = "campaign_provider")
    private Provider provider;
}
