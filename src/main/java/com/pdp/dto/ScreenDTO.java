package com.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Doniyor Nishonov
 * @since 08/August/2024  06:42
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScreenDTO {
    private Integer screenId;
    private String name;
    private Integer seatingCapacity;
    private String soundSystem;
    private String imageName;
    private String imageExtension;
}
