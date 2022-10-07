package com.hanaberia.enums;

public enum Categories {

    BRACELET("Bransoletki"),
    EARRINGS("Kolczyki"),
    NECKLACE("Naszyjniki"),
    RING("Pierścionki"),
    OTHERS("Inne");

    private final String displayValue;

    Categories(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
