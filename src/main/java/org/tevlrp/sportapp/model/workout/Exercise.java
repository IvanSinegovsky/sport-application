package org.tevlrp.sportapp.model.workout;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Exercise {
    private String ExerciseClassificationName;
    private Double weight;
}
