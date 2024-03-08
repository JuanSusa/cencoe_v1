package com.cencoe.cencoe.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name = "creation_date", updatable = false)
    private LocalDate creationDate;

    //Relacion de muchos equipos a muchos usuarios
    @ManyToMany(mappedBy = "teams")
    private List<User> users;

    //relacion de un equipo a muchas campañas
    @OneToMany(targetEntity = Campaign.class, fetch = FetchType.LAZY, mappedBy = "team")
//    @JsonIgnore
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonBackReference
    private List<Campaign> campaigns;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDate.now();
        System.out.println("Método onCreate() ejecutado. Fecha de creación: " + this.creationDate);
    }

//    public boolean isRecent() {
//        LocalDate currentDate = LocalDate.now();
//        return creationDate.isAfter(currentDate.minusDays(30)); // Por ejemplo, considerando que "reciente" significa creado en los últimos 30 días.
//    }
}





