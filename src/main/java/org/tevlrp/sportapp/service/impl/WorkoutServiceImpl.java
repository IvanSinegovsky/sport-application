package org.tevlrp.sportapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tevlrp.sportapp.model.workout.Exercise;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;
import org.tevlrp.sportapp.model.workout.Workout;
import org.tevlrp.sportapp.repository.WorkoutRepository;
import org.tevlrp.sportapp.service.WorkoutService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WorkoutServiceImpl implements WorkoutService {
    private WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public List<Workout> getAll() {
        List<Workout> workouts = workoutRepository.findAll();
        log.info("IN WorkoutServiceImpl getAll() - {} workouts found", workouts.toString());
        return workouts;
    }

    @Override
    public Workout insert(Workout workout) {
        Workout savedWorkout = workoutRepository.insert(workout);
        log.info("IN WorkoutServiceImpl insert() - {}", savedWorkout);
        return savedWorkout;
    }

    @Override
    public List<Workout> findByUserId(Long userId) {
        List<Workout> workouts = workoutRepository.findByUserId(userId);
        log.info("IN WorkoutServiceImpl findByUser() - {} workouts found", workouts.toString());
        return  workouts;
    }

    @Override
    public void deleteByUserIdAndDate(Long userId, String date) {
        workoutRepository.deleteByUserIdAndDate(userId, date);
        log.info("IN WorkoutServiceImpl deleteByUserIdAndDate() - workout with userId: {} and Date: {} successfully deleted",
                userId, date);
    }

    @Override
    public Workout findByUserIdAndDate(Long userId, String date) {
        Workout workoutByDate = workoutRepository.findByUserIdAndDate(userId, date);
        log.info("IN WorkoutServiceImpl findByUserIdAndDate() - {} workouts found", workoutByDate.toString());
        return workoutByDate;
    }

    @Override
    public Map<ExerciseClassification, Map<String, Double>> findClassifiedByUserId(Long userId) {
        List<Workout> userWorkouts = workoutRepository.findByUserId(userId);
        Map<ExerciseClassification, Map<String, Double>> groupedUserResults = new HashMap<>();

        //todo сделать через стрим по красоте
        //это уродство и костыль, просто стримы отказываются корректно работать:(((((
        for (ExerciseClassification classification : ExerciseClassification.values()) {
            Map<String, Double> userResults = new HashMap<>(userWorkouts.size());

            for (Workout userWorkout : userWorkouts) {
                List<Exercise> exercises = userWorkout.getExercises();
                String key = userWorkout.getDate();
                for (Exercise exercise : exercises) {
                    if (exercise.getExerciseClassification().equals(classification)){
                        Double value = exercise.getWeight();

                        userResults.put(key, value);
                    }
                }
            }

            groupedUserResults.put(classification, userResults);
        }

        return groupedUserResults;
    }
}
