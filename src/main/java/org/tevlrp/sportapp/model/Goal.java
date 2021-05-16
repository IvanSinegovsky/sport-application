package org.tevlrp.sportapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.tevlrp.sportapp.model.workout.ExerciseClassification;

import javax.persistence.*;

@Entity
@Table(name = "goals")
@Data
@NoArgsConstructor
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_classification_id")
    private ExerciseClassification exerciseClassification;

    private Double weight;

    public Goal(Long userId, ExerciseClassification exerciseClassification, Double weight) {
        this.userId = userId;
        this.exerciseClassification = exerciseClassification;
        this.weight = weight;
    }
}
