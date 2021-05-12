package org.tevlrp.sportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;

import java.util.List;

public interface ExerciseClassificationRepository extends JpaRepository<ExerciseClassification, Long> {
}
