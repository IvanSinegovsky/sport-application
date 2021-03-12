package org.tevlrp.sportapp.model.workout;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class Workout {
    private Long id;
    private Date date; //TODO add date format
    private WorkoutType workoutType;
    private List<Exercise> exercises;
    private Double bodyWeight;
}