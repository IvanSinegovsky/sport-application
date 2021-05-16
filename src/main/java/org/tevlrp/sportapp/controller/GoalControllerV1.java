package org.tevlrp.sportapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tevlrp.sportapp.dto.GoalRequestDto;
import org.tevlrp.sportapp.dto.GoalResponseDto;
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
    public ResponseEntity<List<GoalResponseDto>> getUserGoalsPercents(@RequestHeader Map<String, String> headers) {
        Long userId = jwtTokenProvider.getId(headers.get("authorization"));
        List<GoalResponseDto> goalsFulfilling = goalService.getGoalsFulfillmentPercentsByUserId(userId);

        if (goalsFulfilling.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(goalsFulfilling, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<GoalResponseDto> addGoal(@RequestHeader Map<String, String> headers,
                                     @RequestBody GoalRequestDto goalRequestDto) {
        Long userId = jwtTokenProvider.getId(headers.get("authorization"));
        goalRequestDto.setUserId(userId);
        Optional<GoalResponseDto> goalDtoOptional = goalService.add(goalRequestDto);

        if (goalDtoOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(goalDtoOptional.get(), HttpStatus.CREATED);
    }
}
