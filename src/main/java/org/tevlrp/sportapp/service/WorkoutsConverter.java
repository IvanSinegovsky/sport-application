package org.tevlrp.sportapp.service;

import org.springframework.stereotype.Component;
import org.tevlrp.sportapp.dto.WorkoutsDto;
import org.tevlrp.sportapp.model.workout.Workout;

@Component
public class WorkoutsConverter {

    public Workout fromWorkoutDtoToWorkout(WorkoutsDto workoutsDto) {
        Workout workout = new Workout();

        workout.setUserId(workoutsDto.getUserId());
        workout.setDate(workoutsDto.getDate());
        workout.setWorkoutType(workoutsDto.getWorkoutType());
        workout.setExercises(workoutsDto.getExercises());
        workout.setBodyWeight(workoutsDto.getBodyWeight());

        return workout;
    }

    public WorkoutsDto fromWorkoutToWorkoutDto(Workout workout) {
        return WorkoutsDto.builder()
                .userId(workout.getUserId())
                .date(workout.getDate())
                .workoutType(workout.getWorkoutType())
                .exercises(workout.getExercises())
                .bodyWeight(workout.getBodyWeight())
                .build();
    }
}
