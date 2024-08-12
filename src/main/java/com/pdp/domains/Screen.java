package com.pdp.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * @since 03/August/2024  19:42
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Screen extends BaseDomain {
    private String name;
    private Integer cinemaID;
    private int seatingCapacity;
    private String soundSystem;
    private Integer imageId;
}
