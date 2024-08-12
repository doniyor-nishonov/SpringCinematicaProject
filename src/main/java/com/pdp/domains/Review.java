package com.pdp.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  11:27
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseDomain {
    private Integer userId;
    private Integer moveId;
    private Double rating;
    private String comment;
}
