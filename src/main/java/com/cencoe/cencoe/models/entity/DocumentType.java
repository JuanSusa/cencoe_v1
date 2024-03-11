package com.cencoe.cencoe.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "document_type")
public class DocumentType implements Serializable {

    public final static Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctype_id")
    private Long docTypeId;

    @Column(name = "doctype_name")
    private String docTypeName;

    //relacion de un tipo de documento a muchos usuarios
    @OneToMany(targetEntity = User.class, fetch = FetchType.LAZY, mappedBy = "userDocType")
    @JsonBackReference
    private List<User> users;


    //relacion de un tipo de documento a muchos clientes
    @OneToMany(targetEntity = Customer.class, fetch = FetchType.LAZY, mappedBy = "customerDocType")
    @JsonIgnore
    private List<Customer> customers;

}
