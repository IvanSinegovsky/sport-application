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
        ExerciseClassification goalExerciseClassification = exerciseClassificationRepository
                .findByName(goalRequestDto.getExerciseClassificationName());
        List<Exercise> allUserExercises = getAllUserExercises(goalRequestDto.getUserId());
        String exerciseClassificationName = goalExerciseClassification.getName();

        Goal savedGoal = goalRepository.save(new Goal(
                goalRequestDto.getUserId(),
                goalExerciseClassification,
                goalRequestDto.getWeight())
        );

        Optional<Map.Entry<String, Double>> classificationToMaxWeight =
                allUserExercisesClassificationsMaxWeights(allUserExercises)
                        .entrySet()
                        .stream()
                        .filter(map -> map.getKey().equals(exerciseClassificationName))
                        .findAny();

        if (classificationToMaxWeight.isPresent()){
            Double goalFulfilling = percentsFulfilling(classificationToMaxWeight.get().getValue(), savedGoal.getWeight());
            GoalResponseDto goalResponseDto = new GoalResponseDto(exerciseClassificationName, goalFulfilling);

            return Optional.of(goalResponseDto);
        }

        return Optional.empty();
    }


    @Override
    public List<GoalResponseDto> getGoalsFulfillmentPercentsByUserId(Long userId) {
        List<Goal> userGoals = goalRepository.findAllByUserId(userId);
        List<Exercise> allUserExercises = getAllUserExercises(userId);
        Map<String, Double> classificationNameToWeight = allUserExercisesClassificationsMaxWeights(allUserExercises);

        List<GoalResponseDto> allUserGoals = classificationNameToWeight
                .entrySet()
                .stream()
                .map((entry) ->  {
                    String currentClassificationName = entry.getKey();
                    Double currentMaxWeight = entry.getValue();
                    Optional<Goal> currentExerciseClassificationGoal
                            = userGoals.stream()
                            .filter(goal -> goal.getExerciseClassification().getName().equals(currentClassificationName))
                            .findFirst();

                    Double fulfilling = currentExerciseClassificationGoal
                            .map(goal -> percentsFulfilling(currentMaxWeight, goal.getWeight()))
                            .orElse(0.0);

                    return new GoalResponseDto(currentClassificationName, fulfilling);
                })
                .filter(goalResponseDto -> !goalResponseDto.getFulfillingInPercents().equals(0.0))
                .collect(Collectors.toList());

        return allUserGoals;
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
