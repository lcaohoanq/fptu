package com.fpt.pe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "blind_boxes")
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
    private String name;
    private String rarity;
    private Double price;
    private Date releaseDate;
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;


    //---------------------DTO-------------------------------------------
    public record BlindBoxRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 10, message = "Name must be greater than 10 characters")
        String name,

        @NotBlank(message = "Rarity is required")
        String rarity,

        @NotNull(message = "Price is required")
        Double price,

        @NotNull(message = "Stock is required")
        @Min(value = 1, message = "Stock must be at least 1")
        @Max(value = 100, message = "Stock must be at most 100")
        Integer stock,

        @NotNull(message = "Category ID is required")
        Integer categoryId
    ) {}

    // For partial updates (inline editing)
    public record BlindBoxUpdateRequest(
        @Size(min = 10, message = "Name must be greater than 10 characters")
        String name,

        String rarity,

        Double price,

        @Min(value = 1, message = "Stock must be at least 1")
        @Max(value = 100, message = "Stock must be at most 100")
        Integer stock,

        Integer categoryId
    ) {}

    public record BlindBoxResponse(
        Integer id,
        String name,
        String rarity,
        Double price,
        Date releaseDate,
        Integer stock,
        String categoryName,
        Integer categoryId
    ){

        public static BlindBoxResponse from(BlindBox blindBox) {
            return new BlindBoxResponse(
                blindBox.getId(),
                blindBox.getName(),
                blindBox.getRarity(),
                blindBox.getPrice(),
                blindBox.getReleaseDate(),
                blindBox.getStock(),
                blindBox.getCategory() != null ? blindBox.getCategory().getName() : null,
                blindBox.getCategory() != null ? blindBox.getCategory().getId() : null
            );
        }

    }

}
