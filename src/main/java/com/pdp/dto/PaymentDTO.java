package com.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Doniyor Nishonov
 * @since 09/August/2024  19:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private Integer showtimeId;
    private String selectedSeats;
    private Double totalAmount;
    private String cardHolderName;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
}
