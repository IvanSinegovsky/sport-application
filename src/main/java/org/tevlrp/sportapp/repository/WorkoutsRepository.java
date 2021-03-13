package org.tevlrp.sportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tevlrp.sportapp.model.User;
import org.tevlrp.sportapp.model.workout.Workout;

import java.util.Date;
import java.util.Optional;

public interface WorkoutsRepository extends JpaRepository<Workout, Long> {
    Workout findWorkoutByUserIdAndDate(Long userId, Date date);

    void deleteWorkoutsByUserIdAndDate(Long userId, Date date);
}
