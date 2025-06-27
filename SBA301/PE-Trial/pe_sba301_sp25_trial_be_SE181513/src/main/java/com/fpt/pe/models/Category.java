package com.fpt.pe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "BlindBoxCategories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private Integer id;

    @Column(name = "CategoryName")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "RarityLevel")
    private String rarityLevel;

    @Column(name = "PriceRange")
    private String priceRange;

}
