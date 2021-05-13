package org.tevlrp.sportapp.model;

import lombok.Data;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;

import javax.persistence.*;

@Entity
@Table(name = "goals")
@Data
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_classification_id")
    private ExerciseClassification exerciseClassification;

    private Double weight;
}
