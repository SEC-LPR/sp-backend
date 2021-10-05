package com.sec.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartGetDto {

    private Long productId;

    private String productName;

    private Integer amount;

    private BigDecimal price;

    private Integer productAmount;

}
