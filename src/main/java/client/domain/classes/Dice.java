package client.domain.classes;

import java.util.Random;

public class Dice {
    // Fields
    private Random r;
    private int lastRolled;
    private int amountRolled =0;

    // Properties
    public int getLastRolled() { return lastRolled; }

    // Constructor
    public Dice() {
        r =  new Random();
        lastRolled = -1;
    }

    // Methods
    public int rollDice() {
    amountRolled++;
        int number=-1;

             number = r.nextInt(6) + 1;

        this.lastRolled = number;
        return  number;

    }

    public int getAmountRolled() {
        return amountRolled;
    }

    public Dice setAmountRolled(int amountRolled) {
        this.amountRolled = amountRolled;
        return this;
    }
}
