package org.tevlrp.sportapp.exception;

public class WorkoutRepositoryException extends RuntimeException {
    public WorkoutRepositoryException() { }

    public WorkoutRepositoryException(String message) {
        super(message);
    }

    public WorkoutRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkoutRepositoryException(Throwable cause) {
        super(cause);
    }
}
