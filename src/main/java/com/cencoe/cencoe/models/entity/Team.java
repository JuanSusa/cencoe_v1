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
@Table(name = "team")
public class Team implements Serializable {

    private final static Long SerialVerionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "team_capacity")
    private Integer teamCapacity;

    @Column(name = "team_state")
    private Boolean teamState;

    //relacion de un equipo a muchas campañas
    @OneToMany(targetEntity = Campaign.class, fetch = FetchType.LAZY, mappedBy = "campaignTeam")
    @JsonBackReference
    //@JsonIgnoreProperties({"campaignTeam", "hibernateLazyInitializer", "handler"})
    private List<Campaign> campaigns;
}
