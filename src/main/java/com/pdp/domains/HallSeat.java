package com.pdp.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Doniyor Nishonov
 * @since 08/August/2024  11:52
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class HallSeat extends BaseDomain {
    private Integer showtimeId;
    private Integer seatNumber;
}
