package org.tevlrp.sportapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tevlrp.sportapp.dto.WorkoutDto;
import org.tevlrp.sportapp.exception.WorkoutRepositoryException;
import org.tevlrp.sportapp.model.workout.Workout;
import org.tevlrp.sportapp.security.jwt.JwtTokenProvider;
import org.tevlrp.sportapp.service.WorkoutService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("api/v1/calendar/")
public class WorkoutControllerV1 {
    private WorkoutService workoutService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public WorkoutControllerV1(WorkoutService workoutService, JwtTokenProvider jwtTokenProvider) {
        this.workoutService = workoutService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("add")
    public ResponseEntity addWorkout(@RequestHeader Map<String, String> headers,
                                     @RequestBody WorkoutDto workoutDto) {
        Long userId = jwtTokenProvider.getId(headers.get("authorization"));
        workoutDto.setUserId(userId);
        Workout workout = workoutService.insert(workoutDto.toWorkout());

        if (workout == null) {
            throw new WorkoutRepositoryException("Something in repository went wrong:( Cannot save new workout.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(workout);
    }

    //todo change userid transfer to query string
    @GetMapping("workouts")
    public ResponseEntity getAllUserWorkouts(@RequestHeader Map<String, String> headers) {
        Long userId = jwtTokenProvider.getId(headers.get("authorization"));

        List<Workout> userWorkouts = workoutService.findByUserId(userId);

        if (userWorkouts == null) {
            throw new WorkoutRepositoryException("Something in repository went wrong:( Cannot get users workouts.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userWorkouts);
    }

    @DeleteMapping("delete")
    public ResponseEntity deleteByUserIdAndDate(@RequestParam(value = "date", required = false) String date,
                                                @RequestHeader Map<String, String> headers) {
        Long userId = jwtTokenProvider.getId(headers.get("authorization"));

        workoutService.deleteByUserIdAndDate(userId, date);

        return  ResponseEntity.status(HttpStatus.OK).body("Workout was successfully deleted.");
    }

    @GetMapping("workout")
    public ResponseEntity getWorkoutByUserIdAndDate(@RequestParam(value = "date", required = false) LocalDate date,
                                                    @RequestHeader Map<String, String> headers) {
        Long userId = jwtTokenProvider.getId(headers.get("authorization"));

        Workout workout = workoutService.findByUserIdAndDate(userId, date);

        if (workout == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User has no workouts this day.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(workout);
    }

    @GetMapping("classified_workouts")
    public ResponseEntity getGroupedUserWorkouts(@RequestHeader Map<String, String> headers) {
        Long userId = jwtTokenProvider.getId(headers.get("authorization"));

        List<List<String>> classifiedWorkouts = workoutService.findClassifiedByUserId(userId);

        return  ResponseEntity.status(HttpStatus.OK).body(classifiedWorkouts);
    }
}