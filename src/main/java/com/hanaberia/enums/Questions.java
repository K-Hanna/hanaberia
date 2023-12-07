package com.hanaberia.enums;

public enum Questions {
    BLACK("Kim jesteś/chciał(a)byś być?"),
    BLUE("Co byś powiedział(a) sobie sprzed 5 lat?"),
    RED("Co byś zrobił(a), gdyby nie było jutra?"),
    YELLOW("Przy czym tracisz poczucie czasu?"),
    GREEN("Jakie dobre nawyki chciał(a)byś pielęgnować?"),
    PURPLE("Kto najbardziej Cię inspiruje?"),
    WHITE("Gdzie na Ziemi chciał(a)byś mieszkać?"),
    ORANGE("Jak nie teraz, to kiedy?");

    private final String displayValue;

    private Questions(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
