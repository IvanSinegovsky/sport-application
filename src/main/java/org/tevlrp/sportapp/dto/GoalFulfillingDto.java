package org.tevlrp.sportapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tevlrp.sportapp.model.Goal;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalFulfillingDto {

    @JsonIgnore
    private Long userId;
    private ExerciseClassification exerciseClassification;

    @JsonIgnore
    private Double weight;
    private Double fulfillingInPercents;

    public GoalFulfillingDto(ExerciseClassification exerciseClassification, Double fulfillingInPercents) {
        this.exerciseClassification = exerciseClassification;
        this.fulfillingInPercents = fulfillingInPercents;
    }

    public Goal toGoal() {
        Goal goal = new Goal();
        goal.setUserId(userId);
        goal.setExerciseClassification(exerciseClassification);
        goal.setWeight(weight);

        return goal;
    }

    public GoalFulfillingDto toGoalFulfillingDto(Goal goal) {
        GoalFulfillingDto goalFulfillingDto = new GoalFulfillingDto();
        goalFulfillingDto.setUserId(goal.getUserId());
        goalFulfillingDto.setExerciseClassification(goal.getExerciseClassification());
        goalFulfillingDto.setWeight(goal.getWeight());

        return goalFulfillingDto;
    }
}
