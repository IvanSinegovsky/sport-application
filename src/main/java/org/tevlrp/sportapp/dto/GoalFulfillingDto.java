package org.tevlrp.sportapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalFulfillingDto {
    private ExerciseClassification exerciseClassification;
    private Double fulfillingInPercents;
}
