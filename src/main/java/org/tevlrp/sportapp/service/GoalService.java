package org.tevlrp.sportapp.service;

import org.tevlrp.sportapp.dto.GoalFulfillingDto;
import org.tevlrp.sportapp.model.Goal;

import java.util.List;
import java.util.Optional;

public interface GoalService {
    Optional<GoalFulfillingDto> add(Goal exercise);

    List<GoalFulfillingDto> getGoalsFulfillmentPercentsByUserId(Long userId);

    void deleteByUserIdAndExerciseClassification(Long userId, String exerciseClassification);
}
