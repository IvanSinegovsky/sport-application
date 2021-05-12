package org.tevlrp.sportapp.model.workout;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "exercises_classifications")
@Data
public class ExerciseClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
