package org.tevlrp.sportapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tevlrp.sportapp.dto.GoalFulfillingDto;
import org.tevlrp.sportapp.model.Goal;
import org.tevlrp.sportapp.model.workout.Exercise;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;
import org.tevlrp.sportapp.model.workout.Workout;
import org.tevlrp.sportapp.repository.GoalRepository;
import org.tevlrp.sportapp.repository.WorkoutRepository;
import org.tevlrp.sportapp.service.GoalService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GoalServiceImpl implements GoalService {
    private GoalRepository goalRepository;
    private WorkoutRepository workoutRepository;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository, WorkoutRepository workoutRepository) {
        this.goalRepository = goalRepository;
        this.workoutRepository = workoutRepository;
    }

    @Override
    public Optional<GoalFulfillingDto> add(Goal goal) {
        Goal savedGoal = goalRepository.save(goal);
        List<Workout> userWorkouts = workoutRepository.findByUserId(savedGoal.getUserId());

        List<Exercise> allUserExercises = userWorkouts.stream().map(Workout::getExercises)
                .flatMap(Collection::stream).collect(Collectors.toList());

        Map<ExerciseClassification, Double> classificationToWeight = allUserExercises.stream().collect(
                Collectors.groupingBy(Exercise::getExerciseClassification,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Exercise::getWeight)),
                                Optional::get))
        ).values()
                .stream().filter(exercise -> exercise.getExerciseClassification().equals(savedGoal.getExerciseClassification()))
                .collect(Collectors.toMap(Exercise::getExerciseClassification, Exercise::getWeight));

        GoalFulfillingDto fulfilledInPercents = new GoalFulfillingDto(savedGoal);

        for (Map.Entry<ExerciseClassification, Double> entry : classificationToWeight.entrySet()) {
                if (entry.getKey().equals(savedGoal.getExerciseClassification())) {
                    fulfilledInPercents.setFulfillingInPercents(entry.getValue() / savedGoal.getWeight());

                    return Optional.of(fulfilledInPercents);
                }
        }

        return Optional.empty();
    }


    @Override
    public List<GoalFulfillingDto> getGoalsFulfillmentPercentsByUserId(Long userId) {
        List<Goal> userGoals = goalRepository.findAllByUserId(userId);
        List<Workout> userWorkouts = workoutRepository.findByUserId(userId);

        List<Exercise> allUserExercises = userWorkouts.stream().map(Workout::getExercises)
                .flatMap(Collection::stream).collect(Collectors.toList());

        Map<ExerciseClassification, Double> classificationToWeight = allUserExercises.stream().collect(
                Collectors.groupingBy(Exercise::getExerciseClassification,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Exercise::getWeight)),
                                Optional::get))
        ).values().stream().collect(Collectors.toMap(Exercise::getExerciseClassification, Exercise::getWeight));

        classificationToWeight.forEach((key, value) -> log.info(key + ":" + value));

        List<GoalFulfillingDto> fulfilledInPercents = new ArrayList<>(userGoals.size());

        for (Map.Entry<ExerciseClassification, Double> entry : classificationToWeight.entrySet()) {
            for (Goal goal : userGoals) {
                if (entry.getKey().equals(goal.getExerciseClassification())) {
                    fulfilledInPercents.add(new GoalFulfillingDto(goal.getExerciseClassification(),
                            entry.getValue() / goal.getWeight()));
                }
            }
        }

        return fulfilledInPercents;
    }

    @Override
    public void deleteByUserIdAndExerciseClassification(Long userId, ExerciseClassification exerciseClassification) {

    }
}
