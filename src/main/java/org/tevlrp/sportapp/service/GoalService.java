package org.tevlrp.sportapp.service;

import org.tevlrp.sportapp.model.Goal;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;

import java.util.List;

public interface GoalService {
    Goal add(Goal exercise);

    List<Goal> getGoalsFulfillmentPercentsByUserId(Long userId);

    void deleteByUserIdAndExerciseClassification(Long userId, ExerciseClassification exerciseClassification);
}
