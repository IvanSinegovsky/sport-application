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
    public List<List<String>> findClassifiedByUserId(Long userId) {
        List<Workout> userWorkouts = workoutRepository.findByUserId(userId);

        //todo сделать через стрим по красоте
        //это уродство и костыль, просто стримы отказываются корректно работать:(((((

        List<List<String>> datesAndClassifiedWorkouts = new ArrayList<>(4);

        List<String> dates = new ArrayList<>(userWorkouts.size());
        Set<String> datesSet = new HashSet<>();

        for (ExerciseClassification classification : ExerciseClassification.values()) {
            List<String> userResults = new ArrayList<>(userWorkouts.size());

            for (Workout userWorkout : userWorkouts) {
                datesSet.add(userWorkout.getDate());
                List<Exercise> exercises = userWorkout.getExercises();

                for (Exercise exercise : exercises) {
                    if (exercise.getExerciseClassification().equals(classification)){
                        userResults.add(String.valueOf(exercise.getWeight()));
                    }
                }
            }

            datesAndClassifiedWorkouts.add(userResults);
        }

        dates.addAll(datesSet);
        datesAndClassifiedWorkouts.add(0, dates);

        return datesAndClassifiedWorkouts;
    }
}
