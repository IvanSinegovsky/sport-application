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
import java.util.Optional;

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
        Long userId = getUserIdFromHeaders(headers);
        workoutDto.setUserId(userId);
        Workout workout = workoutService.insert(workoutDto.toWorkout());

        if (workout == null) {
            throw new WorkoutRepositoryException("Something in repository went wrong:( Cannot save new workout.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(workout);
    }

    //todo change userid transfer to query string
    @GetMapping("workouts")
    public ResponseEntity<List<Workout>> getAllUserWorkouts(@RequestHeader Map<String, String> headers) {
        Long userId = getUserIdFromHeaders(headers);
        List<Workout> userWorkouts = workoutService.findByUserId(userId);

        if (userWorkouts == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userWorkouts, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity deleteByUserIdAndDate(@RequestParam(value = "date", required = false) String date,
                                                  @RequestHeader Map<String, String> headers) {
        Long userId = getUserIdFromHeaders(headers);
        workoutService.deleteByUserIdAndDate(userId, date);

        return ResponseEntity.status(HttpStatus.OK).body("Workout was successfully deleted.");
    }

    @GetMapping("workout")
    public ResponseEntity<Workout> getWorkoutByUserIdAndDate(
            @RequestParam(value = "date", required = false) LocalDate date,
            @RequestHeader Map<String, String> headers)
    {
        Long userId = getUserIdFromHeaders(headers);
        Workout workout = workoutService.findByUserIdAndDate(userId, date);

        if (workout == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(workout, HttpStatus.OK);
    }

    @GetMapping("classified_workouts")
    public ResponseEntity<List<List<String>>> getClassifiedByUserId(@RequestHeader Map<String, String> headers) {
        Long userId = getUserIdFromHeaders(headers);
        List<List<String>> classifiedWorkouts = workoutService.findClassifiedByUserId(userId);

        if (classifiedWorkouts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(classifiedWorkouts, HttpStatus.OK);
    }

    @GetMapping("current_classified_workouts")
    public ResponseEntity<List[]> getCurrentClassifiedByUserId(
            @RequestParam(value = "exerciseClassification", required = true) String exerciseClassificationName,
            @RequestHeader Map<String, String> headers
    ) {
        Long userId = getUserIdFromHeaders(headers);
        Optional<List[]> datesAndWeights = workoutService
                .findCurrentClassifiedByUserId(userId, exerciseClassificationName);

        if (datesAndWeights.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(datesAndWeights.get(), HttpStatus.OK);
    }

    private Long getUserIdFromHeaders(Map<String, String> headers) {
        return jwtTokenProvider.getId(headers.get("authorization"));
    }
}
