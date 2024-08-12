package com.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Doniyor Nishonov
 * @since 10/August/2024  06:45
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
    private Integer reviewId;
    private String fullName;
    private String comment;
    private Float rating;
    private Integer userId;
    private Integer movieId;
    private String extension;
}
