package com.fpt.pe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "BlindBoxes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlindBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true, name = "BlindBoxID")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Rarity")
    private String rarity;

    @Column(name = "Price")
    private Double price;

    @Column(name = "ReleaseDate")
    private Date releaseDate;

    @Column(name = "Stock")
    private Integer stock;


    @ManyToOne
    @JoinColumn(name = "CategoryId")
    private Category category;

}
