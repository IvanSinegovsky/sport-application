package org.tevlrp.sportapp.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tevlrp.sportapp.model.Workout;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO change hardcode field injection

@RestController
@RequestMapping("/api/v1/workouts")
public class WorkoutRestControllerV1 {
    private List<Workout> workouts = Stream.of(
            new Workout(1L),
            new Workout(2L),
            new Workout(3L)
    ).collect(Collectors.toList());

    @GetMapping
    public List<Workout> getAll() {
        return workouts;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('workouts:read')")
    public Workout getById(@PathVariable Long id) {
        return workouts.stream().filter(workout -> workout.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('workouts:write')")
    public Workout create(@RequestBody Workout workout) {
        this.workouts.add(workout);
        return workout;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('workouts:write')")
    public void deleteById(@PathVariable Long id) {
        this.workouts.removeIf(workout -> workout.getId().equals(id));
    }
}
