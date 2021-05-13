package org.tevlrp.sportapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tevlrp.sportapp.dto.GoalRequestDto;
import org.tevlrp.sportapp.dto.GoalResponseDto;
import org.tevlrp.sportapp.model.Goal;
import org.tevlrp.sportapp.model.workout.Exercise;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;
import org.tevlrp.sportapp.model.workout.Workout;
import org.tevlrp.sportapp.repository.ExerciseClassificationRepository;
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
    private ExerciseClassificationRepository exerciseClassificationRepository;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository,
                           WorkoutRepository workoutRepository,
                           ExerciseClassificationRepository exerciseClassificationRepository) {
        this.goalRepository = goalRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseClassificationRepository = exerciseClassificationRepository;
    }

    @Override
    public Optional<GoalResponseDto> add(GoalRequestDto goalRequestDto) {
        ExerciseClassification goalToAddExerciseClassification = exerciseClassificationRepository
                .findByName(goalRequestDto.getExerciseClassificationName());

        System.out.println(goalRequestDto.getExerciseClassificationName());

        Goal goalToAdd = new Goal(goalRequestDto.getUserId(),
                goalToAddExerciseClassification,
                goalRequestDto.getWeight());

        Goal savedGoal = goalRepository.save(goalToAdd);
        List<Exercise> allUserExercises = getAllUserExercises(goalToAdd.getUserId());

        Map<String, Double> classificationToWeight = allUserExercisesClassificationsMaxWeights(allUserExercises);
        Map<String, Double> currentGoalClassificationToWeight = classificationToWeight.entrySet().stream()
                .filter(map -> map.getKey().equals(savedGoal.getExerciseClassification().getName()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<String, Double> entry : currentGoalClassificationToWeight.entrySet()) {
                if (entry.getKey().equals(savedGoal.getExerciseClassification().getName())) {
                    GoalResponseDto goalResponseDto = new GoalResponseDto(
                            savedGoal.getExerciseClassification().getName(),
                            percentsFulfilling(entry.getValue(), savedGoal.getWeight())
                    );

                    return Optional.of(goalResponseDto);
                }
        }

        return Optional.empty();
    }


    @Override
    public List<GoalResponseDto> getGoalsFulfillmentPercentsByUserId(Long userId) {
        List<Goal> userGoals = goalRepository.findAllByUserId(userId);
        List<Exercise> allUserExercises = getAllUserExercises(userId);
        Map<String, Double> classificationNameToWeight = allUserExercisesClassificationsMaxWeights(allUserExercises);
        List<GoalResponseDto> goalResponseDtos = new ArrayList<>();

        classificationNameToWeight.forEach((key, value) -> log.info(key + ":" + value));

        for (Map.Entry<String, Double> entry : classificationNameToWeight.entrySet()) {
            for (Goal goal : userGoals) {
                log.info("GOALS {}", goal.toString());
                if (entry.getKey().equals(goal.getExerciseClassification().getName())) {
                    goalResponseDtos.add(new GoalResponseDto(
                            goal.getExerciseClassification().getName(),
                            percentsFulfilling(entry.getValue(), goal.getWeight())
                    ));
                }
            }
        }
        log.info("FULFILLED {}", goalResponseDtos);

        return goalResponseDtos;
    }

    @Override
    public void deleteByUserIdAndExerciseClassification(Long userId, ExerciseClassification exerciseClassification) {

    }

    private List<Exercise> getAllUserExercises(Long userId) {
        List<Workout> userWorkouts = workoutRepository.findByUserId(userId);
        List<Exercise> allUserExercises = userWorkouts.stream().map(Workout::getExercises)
                .flatMap(Collection::stream).collect(Collectors.toList());

        return allUserExercises;
    }

    private Map<String, Double> allUserExercisesClassificationsMaxWeights(List<Exercise> allUserExercises) {
        Map<String, Double> exerciseClassificationToWeight = allUserExercises.stream().collect(
                Collectors.groupingBy(Exercise::getExerciseClassificationName,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Exercise::getWeight)),
                                Optional::get))
        ).values().stream().collect(Collectors.toMap(Exercise::getExerciseClassificationName, Exercise::getWeight));

        return exerciseClassificationToWeight;
    }

    private Double percentsFulfilling(Double currentWeight, Double expectedWeight) {
        return currentWeight / expectedWeight * 100;
    }
}
