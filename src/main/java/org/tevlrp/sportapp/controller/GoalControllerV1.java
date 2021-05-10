package org.tevlrp.sportapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tevlrp.sportapp.dto.GoalFulfillingDto;
import org.tevlrp.sportapp.dto.WorkoutDto;
import org.tevlrp.sportapp.exception.WorkoutRepositoryException;
import org.tevlrp.sportapp.model.Goal;
import org.tevlrp.sportapp.model.workout.Workout;
import org.tevlrp.sportapp.security.jwt.JwtTokenProvider;
import org.tevlrp.sportapp.service.GoalService;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("api/v1/goal/")
public class GoalControllerV1 {
    private final GoalService goalService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public GoalControllerV1(GoalService goalService, JwtTokenProvider jwtTokenProvider) {
        this.goalService = goalService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("goals")
    public ResponseEntity getUserGoalsFulfillments(@RequestHeader Map<String, String> headers) {
        Long userId = jwtTokenProvider.getId(headers.get("authorization"));
        List<GoalFulfillingDto> goalsFulfilling = goalService.getGoalsFulfillmentPercentsByUserId(userId);
        return ResponseEntity.ok(goalsFulfilling);
    }

    @PostMapping("add")
    public ResponseEntity addWorkout(@RequestHeader Map<String, String> headers,
                                     @RequestBody GoalFulfillingDto goalFulfillingDto) {
        Long userId = jwtTokenProvider.getId(headers.get("authorization"));
        goalFulfillingDto.setUserId(userId);
        Goal goal = goalService.add(goalFulfillingDto.toGoal());
        System.out.println(goalFulfillingDto.getExerciseClassification());
        System.out.println(goal.getExerciseClassification());

        if (goal == null) {
            throw new WorkoutRepositoryException("Something in repository went wrong:( Cannot save new goal.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(goal);
    }
}
