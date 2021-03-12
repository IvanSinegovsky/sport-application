package org.tevlrp.sportapp.model;

public enum Permission {
    WORKOUT_READ("workout:read"),
    WORKOUT_WRITE("workout:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
