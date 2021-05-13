package org.tevlrp.sportapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tevlrp.sportapp.model.Goal;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalRequestDto {

    @JsonIgnore
    private Long userId;
    private String exerciseClassificationName;
    private Double weight;

    public GoalRequestDto(ExerciseClassification exerciseClassification) {
        this.exerciseClassificationName = exerciseClassification.getName();
    }

    public GoalRequestDto(Goal goal) {
        this.userId = goal.getUserId();
        this.exerciseClassificationName = goal.getExerciseClassification().getName();
        this.weight = goal.getWeight();
    }


    public GoalRequestDto toGoalFulfillingDto(Goal goal) {
        GoalRequestDto goalRequestDto = new GoalRequestDto();
        goalRequestDto.setUserId(goal.getUserId());
        goalRequestDto.setExerciseClassificationName(goal.getExerciseClassification().getName());
        goalRequestDto.setWeight(goal.getWeight());

        return goalRequestDto;
    }
}
