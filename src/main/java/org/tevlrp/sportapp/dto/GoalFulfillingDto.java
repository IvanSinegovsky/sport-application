package org.tevlrp.sportapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tevlrp.sportapp.model.Goal;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalFulfillingDto {
    private Long userId;
    private ExerciseClassification exerciseClassification;
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
