package com.cencoe.cencoe.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private final static Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone")
    private Long customerPhone;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "customer_state")
    private Boolean customerState;

    @Column(name = "customer_numdoc", unique = true)
    private String customerNumDoc;

    //Relacion de muchos clientes a un tipo de docuemento
    @ManyToOne(targetEntity = DocumentType.class, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "customer_doctype")
    private DocumentType customerDocType;
}
