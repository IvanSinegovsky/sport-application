package org.tevlrp.sportapp.service;

import org.tevlrp.sportapp.model.workout.Exercise;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;
import org.tevlrp.sportapp.model.workout.Workout;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WorkoutService {
    List<Workout> getAll();

    Workout insert(Workout workout);

    List<Workout> findByUserId(Long userId);

    void deleteByUserIdAndDate(Long userId, String date);

    Workout findByUserIdAndDate(Long userId, String date);

    Map<ExerciseClassification, Map<String, Double>> findClassifiedByUserId(Long userId);
}
