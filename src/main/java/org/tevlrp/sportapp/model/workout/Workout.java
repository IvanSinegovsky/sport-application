package org.tevlrp.sportapp.model.workout;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "workouts")
@Data
public class Workout {
    @Id
    private String id;

    @NotNull
    private Long userId;

    @NotNull
    private LocalDate date;

    //todo validate length
    private List<Exercise> exercises;

    private String description;
}
