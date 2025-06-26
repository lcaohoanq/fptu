package com.orchid.orchidbe.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "orchids")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orchid {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isNatural;

    private String description;

    @JsonProperty("orchidName")
    private String name;

    @JsonProperty("image")
    private String url;

    private Double price;

    private Long categoryId;


}
