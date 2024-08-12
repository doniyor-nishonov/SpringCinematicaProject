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
 * @since 04/August/2024  11:21
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Ticket extends BaseDomain {
    private Integer userId;
    private Integer showtimeId;
    private String seatNumber;
    private LocalDateTime purchaseTime;
    private Double price;
}
