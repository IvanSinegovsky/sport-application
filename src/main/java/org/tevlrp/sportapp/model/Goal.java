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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercises_classifications", referencedColumnName = "name")
    private ExerciseClassification exerciseClassification;

    private Double weight;
}
