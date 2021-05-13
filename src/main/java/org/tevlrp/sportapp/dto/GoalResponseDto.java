package org.tevlrp.sportapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalResponseDto {
    private String exerciseClassification;
    private Double fulfillingInPercents;
}
