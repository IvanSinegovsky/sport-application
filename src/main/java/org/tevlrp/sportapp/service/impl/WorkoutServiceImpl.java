package org.tevlrp.sportapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tevlrp.sportapp.model.workout.Exercise;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;
import org.tevlrp.sportapp.model.workout.Workout;
import org.tevlrp.sportapp.repository.ExerciseClassificationRepository;
import org.tevlrp.sportapp.repository.WorkoutRepository;
import org.tevlrp.sportapp.service.WorkoutService;

import java.time.LocalDate;
import java.util.*;

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
        return workouts;
    }

    @Override
    public void deleteByUserIdAndDate(Long userId, String date) {
        Integer year = Integer.parseInt(date.substring(0, 4));
        Integer month = Integer.parseInt(date.substring(5, 7));
        Integer day = Integer.parseInt(date.substring(8, 10));

        LocalDate localDate = LocalDate.of(year,month, day);
        workoutRepository.deleteByUserIdAndDate(userId, localDate);
        log.info("IN WorkoutServiceImpl deleteByUserIdAndDate() - workout with userId: {} and Date: {} successfully deleted",
                userId, date);
    }

    @Override
    public Workout findByUserIdAndDate(Long userId, LocalDate date) {
        Workout workoutByDate = workoutRepository.findByUserIdAndDate(userId, date);
        log.info("IN WorkoutServiceImpl findByUserIdAndDate() - {} workouts found", workoutByDate.toString());
        return workoutByDate;
    }

    @Override
    public List<List<String>> findClassifiedByUserId(Long userId) {
        List<Workout> userWorkouts = getAllUsersWorkouts(userId);
        List<List<String>> datesAndClassifiedWorkouts = new ArrayList<>();
        List<String> dates = new ArrayList<>(userWorkouts.size());
        List<ExerciseClassification> exercisesClassifications = exerciseClassificationRepository.findAll();

        for (Workout userWorkout : userWorkouts) {
            dates.add(userWorkout.getDate().toString());
        }

        Collections.reverse(dates);
        datesAndClassifiedWorkouts.add(dates);

        for (ExerciseClassification classification : exercisesClassifications) {
            List<String> userResults = new ArrayList<>(userWorkouts.size());

            for (Workout userWorkout : userWorkouts) {
                List<Exercise> exercises = userWorkout.getExercises();

                for (Exercise exercise : exercises) {
                    if (exercise.getExerciseClassificationName().equals(classification.getName())){
                        userResults.add(String.valueOf(exercise.getWeight()));
                    }
                }
            }

            datesAndClassifiedWorkouts.add(userResults);
        }

        return datesAndClassifiedWorkouts;
    }

    @Override
    public Optional<List[]> findCurrentClassifiedByUserId(Long userId, String exerciseClassificationName) {
        List<Workout> userWorkouts = getAllUsersWorkouts(userId);
        List[] datesAndWeights = new List[2];
        List<String> dates = new ArrayList<>(userWorkouts.size());
        List<String> results = new ArrayList<>(userWorkouts.size());
        datesAndWeights[0] = dates;
        datesAndWeights[1] = results;

        for (Workout userWorkout : userWorkouts) {
                Optional<Exercise> exerciseToAdd = userWorkout.getExercises().stream()
                        .filter(exercise -> exercise.getExerciseClassificationName().equals(exerciseClassificationName))
                        .findAny();
                if (exerciseToAdd.isPresent()) {
                    dates.add(userWorkout.getDate().toString());
                    results.add(String.valueOf(exerciseToAdd.get().getWeight()));
                }
        }

        return Optional.of(datesAndWeights);
    }

    private List<Workout> getAllUsersWorkouts(Long userId) {
        return workoutRepository.findByUserId(userId);
    }
}
