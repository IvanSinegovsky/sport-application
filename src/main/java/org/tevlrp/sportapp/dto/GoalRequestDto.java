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
    private ExerciseClassification exerciseClassification;
    private Double weight;

    public GoalRequestDto(ExerciseClassification exerciseClassification) {
        this.exerciseClassification = exerciseClassification;
    }

    public GoalRequestDto(Goal goal) {
        this.userId = goal.getUserId();
        this.exerciseClassification = goal.getExerciseClassification();
        this.weight = goal.getWeight();
    }

    public Goal toGoal() {
        Goal goal = new Goal();
        goal.setUserId(userId);
        goal.setExerciseClassification(exerciseClassification);
        goal.setWeight(weight);

        return goal;
    }

    public GoalRequestDto toGoalFulfillingDto(Goal goal) {
        GoalRequestDto goalRequestDto = new GoalRequestDto();
        goalRequestDto.setUserId(goal.getUserId());
        goalRequestDto.setExerciseClassification(goal.getExerciseClassification());
        goalRequestDto.setWeight(goal.getWeight());

        return goalRequestDto;
    }
}
