package com.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Doniyor Nishonov
 * @since 08/August/2024  08:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowtimeDTO {
    private Integer showtimeId;
    private String eventName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer screenId;
    private Integer movieId;
    private String language;
    private Double price;
}
