package org.tevlrp.sportapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;
import org.tevlrp.sportapp.repository.ExerciseClassificationRepository;
import org.tevlrp.sportapp.service.ExerciseClassificationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExerciseClassificationServiceImpl implements ExerciseClassificationService {
    private ExerciseClassificationRepository exerciseClassificationRepository;

    @Autowired
    public ExerciseClassificationServiceImpl(ExerciseClassificationRepository exerciseClassificationRepository) {
        this.exerciseClassificationRepository = exerciseClassificationRepository;
    }

    @Override
    public List<String> getAllExercisesClassifications() {
        List<ExerciseClassification> exerciseClassifications = exerciseClassificationRepository.findAll();
        List<String> exerciseClassificationsNames = exerciseClassifications.stream().map(ExerciseClassification::getName)
                .collect(Collectors.toList());

        return exerciseClassificationsNames;
    }
}
