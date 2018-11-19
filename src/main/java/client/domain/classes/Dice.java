package client.domain.classes;

import java.util.Random;

public class Dice {
    // Fields
    private Random r;
    private int lastRolled;

    // Properties
    public int getLastRolled() { return lastRolled; }

    // Constructor
    public Dice() {
        r =  new Random();
        lastRolled = -1;
    }

    // Methods
    public int rollDice() {

        int number=-1;

             number = r.nextInt(6) + 1;

        this.lastRolled = number;
        return  number;
    }
}
