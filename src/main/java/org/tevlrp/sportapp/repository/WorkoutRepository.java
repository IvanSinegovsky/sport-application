package org.tevlrp.sportapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tevlrp.sportapp.model.workout.Workout;

import java.util.Date;
import java.util.List;

@Repository
public interface WorkoutRepository extends MongoRepository<Workout, String> {
    List<Workout> findByUserId(Long userId);

    void deleteByUserIdAndDate(Long userId, Date date);

    Workout findByUserIdAndDate(Long userId, Date date);
}
