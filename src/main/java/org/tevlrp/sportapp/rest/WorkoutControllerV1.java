package org.tevlrp.sportapp.rest;

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
    public ResponseEntity addWorkout(@RequestHeader Map<String, String> headers, @RequestBody WorkoutDto workoutDto) {
        String token = headers.get("authorization");
        String userId = jwtTokenProvider.getId(token);
        workoutDto.setUserId(Long.valueOf(userId));

        Workout workout = workoutService.insert(workoutDto.toWorkout());
        log.info("IN WorkoutsControllerV1 addWorkout() inserted and pop:{}", workout);

        if (workout == null) {
            throw new WorkoutRepositoryException("Something in repository went wrong:( Cannot save new workout.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(workout);
    }

    @GetMapping("workouts")
    public ResponseEntity getAllUserWorkouts(@RequestHeader Map<String, String> headers) {
/*for (Map.Entry<String, String> entry : headers.entrySet()) {
            log.info("MAP KEY: {}, MAP VALUE: {}", entry.getKey(), entry.getValue());
        }*/
        String token = headers.get("authorization");
        String userId = jwtTokenProvider.getId(token);

        List<Workout> userWorkouts = workoutService.findByUserId(Long.valueOf(userId));

        if (userWorkouts == null) {
            throw new WorkoutRepositoryException("Something in repository went wrong:( Cannot get users workouts.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userWorkouts);
    }

    @DeleteMapping("delete")
    public ResponseEntity deleteByUserIdAndDate(@RequestParam(value = "date", required = false) String date,
                                                @RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization");
        String userId = jwtTokenProvider.getId(token);

        workoutService.deleteByUserIdAndDate(Long.valueOf(userId), date);

        return  ResponseEntity.status(HttpStatus.OK).body("Workout was successfully deleted.");
    }

    @GetMapping("dateWorkout")
    public ResponseEntity getWorkoutByUserIdAndDate(@RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization");
        String date = headers.get("date");
        String userId = jwtTokenProvider.getId(token);

        Workout workout = workoutService.findByUserIdAndDate(Long.valueOf(userId), date);

        if (workout == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("User has no workouts this day.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(workout);
    }
}
