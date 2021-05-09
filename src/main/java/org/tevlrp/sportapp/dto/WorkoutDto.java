package org.tevlrp.sportapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.tevlrp.sportapp.model.workout.Exercise;
import org.tevlrp.sportapp.model.workout.Workout;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class WorkoutDto {
    private Long userId;
    private LocalDate date;
    private List<Exercise> exercises;
    private String description;

    public WorkoutDto(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setUserId(workout.getUserId());
        workoutDto.setDate(workout.getDate());
        workoutDto.setExercises(workout.getExercises());
        workoutDto.setDescription(workout.getDescription());
    }

    public Workout toWorkout() {
        Workout workout = new Workout();
        workout.setUserId(userId);
        workout.setDate(date);
        workout.setExercises(exercises);
        workout.setDescription(description);

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
