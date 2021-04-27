package org.tevlrp.sportapp.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tevlrp.sportapp.dto.WorkoutDto;
import org.tevlrp.sportapp.exception.WorkoutRepositoryException;
import org.tevlrp.sportapp.model.workout.Workout;
import org.tevlrp.sportapp.service.WorkoutService;

import java.util.Date;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("api/v1/calendar/")
public class WorkoutControllerV1 {
    private WorkoutService workoutService;

    @Autowired
    public WorkoutControllerV1(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("add")
    public ResponseEntity addWorkout(@RequestBody WorkoutDto workoutDto) {
        log.info("IN WorkoutsControllerV1 addWorkout() workout:{}", workoutDto);
        Workout workout = workoutService.insert(workoutDto.toWorkout());

        if (workout == null) {
            throw new WorkoutRepositoryException("Something in repository went wrong:( Cannot save new workout.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Workout was successfully added.");
    }

    @GetMapping("workouts/{userId}")
    public ResponseEntity getAllUserWorkouts(@PathVariable Long userId) {
        List<Workout> userWorkouts = workoutService.findByUserId(userId);

        if (userWorkouts == null) {
            throw new WorkoutRepositoryException("Something in repository went wrong:( Cannot get users workouts.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userWorkouts);
    }

    @DeleteMapping("delete")
    public ResponseEntity deleteByUserIdAndDate(@RequestBody Long userId,
                                                @RequestBody Date date) {
        workoutService.deleteByUserIdAndDate(userId, date);
        return  ResponseEntity.status(HttpStatus.OK).body("Workout was successfully deleted.");
    }

    @GetMapping("dateWorkout")
    public ResponseEntity getWorkoutByUserIdAndDate(@RequestBody Long userId,
                                                    @RequestBody Date date) {
        Workout workout = workoutService.findByUserIdAndDate(userId, date);

        if (workout == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("User has no workouts this day.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(workout);
    }
}
