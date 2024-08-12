package com.pdp.domains;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * @since 02/August/2024  19:44
 **/
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public abstract class BaseDomain {
    private Integer id;
    private boolean isActive;
    private LocalDateTime createdDate;
}
