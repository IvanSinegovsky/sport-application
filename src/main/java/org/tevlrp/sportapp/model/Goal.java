package org.tevlrp.sportapp.model;

import lombok.Data;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "goals")
@Data
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private ExerciseClassification exerciseClassification;

    private Double weight;
}
