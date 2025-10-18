package com.fpt.mss.msblindbox_se181513;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlindBoxResponse {

    private Integer id;
    private String name;
    private String rarity;
    private Double price;
    private Date releaseDate;
    private Integer stock;
    private String categoryName;

}
