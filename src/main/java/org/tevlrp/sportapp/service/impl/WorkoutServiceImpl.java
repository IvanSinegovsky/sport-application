package org.tevlrp.sportapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tevlrp.sportapp.model.workout.Workout;
import org.tevlrp.sportapp.repository.WorkoutRepository;
import org.tevlrp.sportapp.service.WorkoutService;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WorkoutServiceImpl implements WorkoutService {
    private WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public List<Workout> getAll() {
        List<Workout> workouts = workoutRepository.findAll();
        log.info("IN WorkoutServiceImpl getAll() - {} workouts found", workouts.toString());
        return workouts;
    }

    @Override
    public Workout insert(Workout workout) {
        Workout savedWorkout = workoutRepository.insert(workout);
        log.info("IN WorkoutServiceImpl insert() - {}", savedWorkout);
        return savedWorkout;
    }

    @Override
    public List<Workout> findByUserId(Long userId) {
        List<Workout> workouts = workoutRepository.findByUserId(userId);
        //some logic here with training types

        log.info("IN WorkoutServiceImpl findByUser() - {} workouts found", workouts.toString());
        return  workouts;
    }

    @Override
    public void deleteByUserIdAndDate(Long userId, String date) {
        workoutRepository.deleteByUserIdAndDate(userId, date);
        log.info("IN WorkoutServiceImpl deleteByUserIdAndDate() - workout with userId: {} and Date: {} successfully deleted",
                userId, date);
    }

    @Override
    public Workout findByUserIdAndDate(Long userId, String date) {
        Workout workoutByDate = workoutRepository.findByUserIdAndDate(userId, date);
        log.info("IN WorkoutServiceImpl findByUserIdAndDate() - {} workouts found", workoutByDate.toString());
        return workoutByDate;
    }
}
