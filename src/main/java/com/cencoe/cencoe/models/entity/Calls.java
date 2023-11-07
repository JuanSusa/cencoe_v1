package com.cencoe.cencoe.models.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.swing.text.StyledEditorKit;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Timer;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "calls")
public class Calls implements Serializable {

    private final static Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calls_id")
    private Long callsId;

    private String callsObservations;
    private LocalTime callsStartTime;
    private LocalTime callsEndTime;
    private LocalDate callsDate;
    private Boolean callsState;
}
