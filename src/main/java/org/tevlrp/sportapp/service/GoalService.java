package org.tevlrp.sportapp.service;

import org.tevlrp.sportapp.dto.GoalRequestDto;
import org.tevlrp.sportapp.dto.GoalResponseDto;

import java.util.List;
import java.util.Optional;

public interface GoalService {
    Optional<GoalResponseDto> add(GoalRequestDto exercise);

    List<GoalResponseDto> getGoalsFulfillmentPercentsByUserId(Long userId);

    void deleteByUserIdAndExerciseClassification(Long userId, String exerciseClassificationName);
}
