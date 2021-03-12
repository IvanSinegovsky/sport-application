package org.tevlrp.sportapp.model.workout;

//for radio button in calendar notes
public enum WorkoutType {
    STRENGTH(1L),
    CARDIO(2L);

    private final Long id;

    WorkoutType(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
