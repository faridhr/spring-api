package com.springapi.springapi.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
/**
 * @JsonIdentityInfo cara lain untuk menghindari loop pada manytomany
 * di entity relasi juga harus ditambahkan
 */
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is Required")
    private String name;

    @NotEmpty(message = "Description is Required")
    private String description;

    private Double price;

    @ManyToOne
    private Category category;

    @ManyToMany
    @JoinTable(name = "product_supplier",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    /**
     * JsonManagedReference
     * berfungsi untuk menghindari loop pada entity manytomany
     * di entity relasinya juga harus ditambah anotasi JsonBackreference
     */
    @JsonManagedReference
    private Set<Supplier> suppliers;

}
