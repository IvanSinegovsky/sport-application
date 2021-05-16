package org.tevlrp.sportapp.service;

import org.tevlrp.sportapp.model.workout.Workout;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutService {
    List<Workout> getAll();
    Optional<Workout> insert(Workout workout);
    List<Workout> findByUserId(Long userId);
    void deleteByUserIdAndDate(Long userId, String date);
    Optional<Workout> findByUserIdAndDate(Long userId, LocalDate date);
    List<List<String>> findClassifiedByUserId(Long userId);
    Optional<List[]> findCurrentClassifiedByUserId(Long userId, String exerciseClassificationName);
}
