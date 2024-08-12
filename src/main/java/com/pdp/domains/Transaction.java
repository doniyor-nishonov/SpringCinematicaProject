package com.pdp.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  11:29
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends BaseDomain {
    private Integer userId;
    private Integer ticketId;
    private Double totalAmount;
    private String cardHolderName;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private LocalDateTime transactionDate;
    private String paymentMethod;
}
