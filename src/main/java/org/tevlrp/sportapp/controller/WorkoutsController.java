package org.tevlrp.sportapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tevlrp.sportapp.dto.WorkoutsDto;
import org.tevlrp.sportapp.exception.ValidationException;
import org.tevlrp.sportapp.service.WorkoutsService;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/workouts")
public class WorkoutsController {
    private WorkoutsService workoutsService;

    @GetMapping("/find/{userId}/{date}")
    @PreAuthorize("hasAuthority('workouts:read')")
    public WorkoutsDto findWorkoutByUserIdAndDate(@PathVariable Long userId, @PathVariable Date date) {
        return workoutsService.findWorkoutByUserIdAndDate(userId, date);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('workouts:write')")
    public WorkoutsDto saveWorkout(@RequestBody WorkoutsDto workoutsDto) throws ValidationException {
        return workoutsService.saveWorkout(workoutsDto);
    }

    @DeleteMapping("/delete/{userId}/{date}")
    @PreAuthorize("hasAuthority('workouts:write')")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id, @PathVariable Date date) {
        workoutsService.deleteWorkout(id, date);
        return ResponseEntity.ok().build();
    }
}