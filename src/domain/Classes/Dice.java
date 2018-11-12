package domain.Classes;

import java.time.LocalDateTime;
import java.util.Random;

public class Dice {
    // Fields
    private Random r;
    private int LastRolled;

    // Properties
    public int getLastRolled() { return LastRolled; }

    // Constructor
    public Dice() {
        r =  new Random();
        LastRolled = -1;
    }

    // Methods
    public int rollDice() {

        int number=-1;

             number = r.nextInt(6) + 1;

        this.LastRolled = number;
        return  number;
    }
}
