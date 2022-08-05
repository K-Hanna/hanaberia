package com.hanaberia.enums;

public enum Categories {

    BRACELET("Bransoletki"),
    EARRINGS("Kolczyki"),
    NECKLACE("Naszyjniki"),
    RING("Pierścionki");

    private final String displayValue;

    private Categories(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
