package org.tevlrp.sportapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tevlrp.sportapp.model.workout.Workout;
import org.tevlrp.sportapp.repository.WorkoutsRepository;

import java.util.Date;
import java.util.Optional;

//TODO change hardcode field injection

@RestController
@RequestMapping("/api/v1/workouts")
public class WorkoutsController {
    private WorkoutsRepository workoutRepository;

    @GetMapping("/{userId}/{date}")
    @PreAuthorize("hasAuthority('workouts:read')")
    public Workout getByUserIdAndDate(@PathVariable Long id, @PathVariable Date date) {
        return workoutRepository.findWorkoutByUserIdAndDate(id, date);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('workouts:write')")
    public Workout create(@RequestBody Workout workout) {
        workoutRepository.save(workout);
        return workout;
    }
}