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
 * @since 03/August/2024  20:09
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Showtime extends BaseDomain {
    private Integer moveId;
    private Integer screenId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String language;
    private Double price;
}
