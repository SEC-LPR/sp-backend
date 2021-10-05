package com.sec.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {

    private String cardName;

    private String cardNumber;

    private String expDate;

    private String cvv;
}
