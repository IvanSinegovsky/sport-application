package org.tevlrp.sportapp.dto;

import lombok.Data;
import org.tevlrp.sportapp.model.workout.Exercise;
import org.tevlrp.sportapp.model.workout.Workout;

import java.util.Date;
import java.util.List;

@Data
public class WorkoutDto {
    private Long userId;
    private Date date;
    private List<Exercise> exercises;

    public Workout toWorkout() {
        Workout workout = new Workout();
        workout.setUserId(userId);
        workout.setDate(date);
        workout.setExercises(exercises);

        return workout;
    }

    public WorkoutDto workoutDto(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setUserId(workout.getUserId());
        workoutDto.setDate(workout.getDate());
        workoutDto.setExercises(workout.getExercises());

        return workoutDto;
    }
}
