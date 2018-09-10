package enums;

public enum  diceNumber {
    ONE     (1),
    TWO     (2),
    THREE   (3),
    FOUR    (4),
    FIVE    (5),
    SIX     (6);

    private int value;

    diceNumber(int i) {
        this.value = i;
    }
}
