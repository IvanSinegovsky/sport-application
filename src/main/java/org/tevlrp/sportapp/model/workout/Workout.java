package org.tevlrp.sportapp.model.workout;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "workouts")
@Data
public class Workout {
    @Id
    private String id;
    private Long userId;
    private Date date;
    private List<Exercise> exercises;
}
