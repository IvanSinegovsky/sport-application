package org.tevlrp.sportapp.model.workout;

import java.util.Date;
import java.util.List;

//TODO use MongoDB
public class Workout {
    private Long userId;

    private Date date; //TODO add date format

    private WorkoutType workoutType;

    private List<Exercise> exercises;

    private Double bodyWeight;
}