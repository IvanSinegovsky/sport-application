package org.tevlrp.sportapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tevlrp.sportapp.model.workout.Exercise;
import org.tevlrp.sportapp.model.workout.Workout;
import org.tevlrp.sportapp.repository.ExerciseClassificationRepository;
import org.tevlrp.sportapp.repository.WorkoutRepository;
import org.tevlrp.sportapp.service.WorkoutService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WorkoutServiceImpl implements WorkoutService {
    private WorkoutRepository workoutRepository;
    private ExerciseClassificationRepository exerciseClassificationRepository;

    @Autowired
    public WorkoutServiceImpl(WorkoutRepository workoutRepository, ExerciseClassificationRepository exerciseClassificationRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseClassificationRepository = exerciseClassificationRepository;
    }

    @Override
    public List<Workout> getAll() {
        List<Workout> workouts = workoutRepository.findAll();
        return workouts;
    }

    @Override
    public Optional<Workout> insert(Workout workout) {
        Optional<Workout> savedWorkoutOptional = Optional.ofNullable(workoutRepository.insert(workout));
        return savedWorkoutOptional;
    }

    @Override
    public List<Workout> findByUserId(Long userId) {
        List<Workout> workouts = workoutRepository.findByUserId(userId);
        return workouts;
    }

    @Override
    public void deleteByUserIdAndDate(Long userId, String date) {
        Integer year = Integer.parseInt(date.substring(0, 4));
        Integer month = Integer.parseInt(date.substring(5, 7));
        Integer day = Integer.parseInt(date.substring(8, 10));

        LocalDate localDate = LocalDate.of(year,month, day);
        workoutRepository.deleteByUserIdAndDate(userId, localDate);
    }

    @Override
    public Optional<Workout> findByUserIdAndDate(Long userId, LocalDate date) {
        Optional<Workout> workoutByDateOptional = Optional.ofNullable(workoutRepository.findByUserIdAndDate(userId, date));
        return workoutByDateOptional;
    }

    @Override
    public Optional<List[]> findCurrentClassifiedByUserId(Long userId, String exerciseClassificationName) {
        List<Workout> userWorkouts = getAllUsersWorkouts(userId);
        List[] datesAndWeights = new List[2];
        List<String> dates = new ArrayList<>(userWorkouts.size());
        List<String> results = new ArrayList<>(userWorkouts.size());
        datesAndWeights[0] = dates;
        datesAndWeights[1] = results;

        userWorkouts.forEach(
                (workout) -> {
                    Optional<Exercise> exerciseToAdd = workout.getExercises().stream()
                            .filter(exercise -> exercise.getExerciseClassificationName().equals(exerciseClassificationName))
                            .findAny();

                    if (exerciseToAdd.isPresent()) {
                        dates.add(workout.getDate().toString());
                        results.add(String.valueOf(exerciseToAdd.get().getWeight()));
                    }
                }
        );

        return Optional.ofNullable(datesAndWeights);
    }

    @Override
    public List<String> findUserWorkoutsDates(Long userId) {
        List<Workout> userWorkouts = getAllUsersWorkouts(userId);
        List<String> dates = userWorkouts.stream()
                .map(workout -> workout.getDate().toString()).collect(Collectors.toList());

        return dates;
    }

    private List<Workout> getAllUsersWorkouts(Long userId) {
        return workoutRepository.findByUserId(userId);
    }
}
