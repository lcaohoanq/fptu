package com.fpt.mss.msbrand_se181513.domain.product;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String brandName;

}
