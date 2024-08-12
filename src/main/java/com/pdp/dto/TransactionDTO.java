package com.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * @author Doniyor Nishonov
 * @since 09/August/2024  22:03
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
    private String movieName;
    private LocalDate transactionDate;
    private String seats;
    private Double totalAmount;
    private String paymentMethod;
}
