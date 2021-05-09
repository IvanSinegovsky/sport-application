package org.tevlrp.sportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tevlrp.sportapp.model.Goal;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;

import java.util.List;

public interface GoalRepository  extends JpaRepository<Goal, Long> {
    List<Goal> findAllByUserId(Long userId);
    void deleteByUserIdAndExerciseClassification(Long userId, ExerciseClassification exerciseClassification);
}
