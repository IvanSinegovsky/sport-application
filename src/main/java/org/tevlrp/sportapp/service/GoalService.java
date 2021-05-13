package org.tevlrp.sportapp.service;

import org.tevlrp.sportapp.dto.GoalResponseDto;
import org.tevlrp.sportapp.model.Goal;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;

import java.util.List;
import java.util.Optional;

public interface GoalService {
    Optional<GoalResponseDto> add(Goal exercise);

    List<GoalResponseDto> getGoalsFulfillmentPercentsByUserId(Long userId);

    void deleteByUserIdAndExerciseClassification(Long userId, ExerciseClassification exerciseClassification);
}
