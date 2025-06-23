package com.orchid.orchidbe.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @JsonProperty("orchidName")
    @Field("name")
    private String name;

    @JsonProperty("image")
    @Field("url")
    private String url;

    private Double price;

    private String categoryId;


}
