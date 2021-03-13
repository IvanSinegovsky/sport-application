package org.tevlrp.sportapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tevlrp.sportapp.model.workout.Exercise;
import org.tevlrp.sportapp.model.workout.WorkoutType;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutsDto {

    private Long userId;
    private Date date; //TODO add date format
    private WorkoutType workoutType;
    private List<Exercise> exercises;
    private Double bodyWeight;
}
