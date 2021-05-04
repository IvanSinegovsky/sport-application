package org.tevlrp.sportapp.service;

import org.tevlrp.sportapp.model.workout.Workout;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutService {
    List<Workout> getAll();

    Workout insert(Workout workout);

    List<Workout> findByUserId(Long userId);

    void deleteByUserIdAndDate(Long userId, LocalDate date);

    Workout findByUserIdAndDate(Long userId, LocalDate date);

    List<List<String>> findClassifiedByUserId(Long userId);
}
