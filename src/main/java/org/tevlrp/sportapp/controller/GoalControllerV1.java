package org.tevlrp.sportapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tevlrp.sportapp.dto.GoalFulfillingDto;
import org.tevlrp.sportapp.exception.WorkoutRepositoryException;
import org.tevlrp.sportapp.model.Goal;
import org.tevlrp.sportapp.security.jwt.JwtTokenProvider;
import org.tevlrp.sportapp.service.GoalService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity getUserGoalsFulfilments(@RequestHeader Map<String, String> headers) {
        Long userId = jwtTokenProvider.getId(headers.get("authorization"));
        List<GoalFulfillingDto> goalsFulfilling = goalService.getGoalsFulfillmentPercentsByUserId(userId);
        return ResponseEntity.ok(goalsFulfilling);
    }

    @PostMapping("add")
    public ResponseEntity addWorkout(@RequestHeader Map<String, String> headers,
                                     @RequestBody GoalFulfillingDto goalFulfillingDto) {
        log.info(goalFulfillingDto.toString());
        Long userId = jwtTokenProvider.getId(headers.get("authorization"));
        goalFulfillingDto.setUserId(userId);
        Optional<GoalFulfillingDto> goalDtoOptional = goalService.add(goalFulfillingDto.toGoal());

        if (goalDtoOptional.isEmpty()) {
            throw new WorkoutRepositoryException("Something in repository went wrong:( Cannot save new goal.");
        }

        return ResponseEntity.ok(goalDtoOptional.get());
    }
}
