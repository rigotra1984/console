package com.rigoberto.console6.entities;

public enum Priority {
    MAXIMUN,
    MEDIUM,
    MINIMUN;
    public static Priority fromString(String x) {
        return switch (x) {
            case "MINIMUN" -> MINIMUN;
            case "MEDIUM" -> MEDIUM;
            default -> MAXIMUN;
        };
    }
}
