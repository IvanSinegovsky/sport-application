package org.tevlrp.sportapp.model.workout;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table("workouts")
public class Workout {

    //TODO add many to one dependency(many workouts to one user)


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "date")
    private Date date; //TODO add date format
    @Column(name = "wotkout_type")
    private WorkoutType workoutType;
    @Column(name = "exercise")
    private List<Exercise> exercises;
    @Column(name = "body_weight")
    private Double bodyWeight;
}