package org.tevlrp.sportapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tevlrp.sportapp.service.ExerciseClassificationService;

import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/classification/")
public class ExerciseClassificationControllerV1 {
    private ExerciseClassificationService exerciseClassificationService;

    @Autowired
    public ExerciseClassificationControllerV1(ExerciseClassificationService exerciseClassificationService) {
        this.exerciseClassificationService = exerciseClassificationService;
    }

    @GetMapping("exercises_classifications")
    public ResponseEntity getAllExercisesClassifications() {
        List<String> allExercisesClassifications = exerciseClassificationService.getAllExercisesClassifications();

        if (allExercisesClassifications == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(allExercisesClassifications);
    }
}
