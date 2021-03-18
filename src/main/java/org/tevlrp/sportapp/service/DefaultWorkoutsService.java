package org.tevlrp.sportapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.tevlrp.sportapp.dto.WorkoutsDto;
import org.tevlrp.sportapp.exception.ValidationException;
import org.tevlrp.sportapp.model.workout.Workout;
import org.tevlrp.sportapp.repository.WorkoutsRepository;

import java.util.Date;

@Service
@AllArgsConstructor
public class DefaultWorkoutsService implements WorkoutsService {

    private final WorkoutsRepository workoutsRepository;
    private final WorkoutsConverter workoutsConverter;

    @Override
    public WorkoutsDto saveWorkout(WorkoutsDto workoutsDto) throws ValidationException {
        if (isWorkoutDtoValid(workoutsDto)) {
            throw new ValidationException("Bad workout credentials(check userId or date)");
        }

        Workout savedWorkout = workoutsRepository
                .save(workoutsConverter.fromWorkoutDtoToWorkout(workoutsDto));

        return workoutsConverter.fromWorkoutToWorkoutDto(savedWorkout);
    }

    private boolean isWorkoutDtoValid(WorkoutsDto workoutsDto) {
        if (workoutsDto == null
                || workoutsDto.getUserId() == null
                || "".equals(workoutsDto.getUserId())
                || "".equals(workoutsDto.getDate().toString())
                || workoutsDto.getDate() == null) {
            return false;
        }

        return true;
    }

    @Override
    public void deleteWorkout(Long userId, Date date) {
        workoutsRepository.deleteWorkoutsByUserIdAndDate(userId, date);
    }

    @Override
    public WorkoutsDto findWorkoutByUserIdAndDate(Long userId, Date date) {
        Workout workout = workoutsRepository.findWorkoutByUserIdAndDate(userId, date);

        if (workout != null) {
            return workoutsConverter.fromWorkoutToWorkoutDto(workout);
        }

        return null;
    }
}
