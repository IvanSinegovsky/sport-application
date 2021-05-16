package org.tevlrp.sportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;

public interface ExerciseClassificationRepository extends JpaRepository<ExerciseClassification, Long> {
    ExerciseClassification findByName(String name);
}
