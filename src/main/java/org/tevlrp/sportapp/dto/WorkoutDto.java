package org.tevlrp.sportapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.tevlrp.sportapp.model.workout.Exercise;
import org.tevlrp.sportapp.model.workout.Workout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class WorkoutDto {
    private Long userId;
    //yyyy-MM-dd
    private String date;
    private List<Exercise> exercises;

    public WorkoutDto(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setUserId(workout.getUserId());
        workoutDto.setDate(workout.getDate());
        workoutDto.setExercises(workout.getExercises());
    }

    public Workout toWorkout() {
        Workout workout = new Workout();
        workout.setUserId(userId);
        workout.setDate(date);
        workout.setExercises(exercises);

        return workout;
    }

    public WorkoutDto toWorkoutDto(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setUserId(workout.getUserId());
        workoutDto.setDate(workout.getDate());
        workoutDto.setExercises(workout.getExercises());

        return workoutDto;
    }
}
