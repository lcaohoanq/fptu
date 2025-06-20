package com.orchid.orchidbe.pojos;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "orchids")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orchid {

    @Id
    private String id;

    private boolean isNatural;

    private String description;

    private String name;

    private String url;

    private Double price;

    private String categoryId;


}
