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
        Optional<Workout> workoutOptional = workoutService.insert(workoutDto.toWorkout());

        if (workoutOptional.isEmpty()) {
            throw new WorkoutRepositoryException("Something in repository went wrong:( Cannot save new workout.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(workoutOptional.get());
    }

    //todo change userid transfer to query string
    @GetMapping("workouts")
    public ResponseEntity<List<Workout>> getAllUserWorkouts(@RequestHeader Map<String, String> headers) {
        Long userId = getUserIdFromHeaders(headers);
        List<Workout> userWorkouts = workoutService.findByUserId(userId);

        if (userWorkouts.isEmpty()) {
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
        Optional<Workout> workoutOptional = workoutService.findByUserIdAndDate(userId, date);

        if (workoutOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(workoutOptional.get(), HttpStatus.OK);
    }

    @GetMapping("current_classified_workouts")
    public ResponseEntity<List[]> getCurrentClassifiedByUserId(
            @RequestParam(value = "exerciseClassification", required = true) String exerciseClassificationName,
            @RequestHeader Map<String, String> headers
    ) {
        Long userId = getUserIdFromHeaders(headers);
        Optional<List[]> datesAndWeightsOptional = workoutService
                .findCurrentClassifiedByUserId(userId, exerciseClassificationName);

        if (datesAndWeightsOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(datesAndWeightsOptional.get(), HttpStatus.OK);
    }

    @GetMapping("workouts_dates")
    public ResponseEntity<List<String>> getUserWorkoutsDates(@RequestHeader Map<String, String> headers) {
        Long userId = getUserIdFromHeaders(headers);
        List<String> dates = workoutService.findUserWorkoutsDates(userId);

        if (dates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(dates, HttpStatus.OK);
    }

    private Long getUserIdFromHeaders(Map<String, String> headers) {
        return jwtTokenProvider.getId(headers.get("authorization"));
    }
}
