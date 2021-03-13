package org.tevlrp.sportapp.service;

import org.tevlrp.sportapp.dto.WorkoutsDto;
import org.tevlrp.sportapp.exception.ValidationException;

import java.util.Date;

public interface WorkoutsService {

    WorkoutsDto saveWorkout(WorkoutsDto workoutsDto) throws ValidationException;

    void deleteWorkout(Long userId, Date date);

    WorkoutsDto findByUserIdAndDate(Long userId, Date date);
}
