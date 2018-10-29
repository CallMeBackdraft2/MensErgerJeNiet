package domain.Classes;

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
        if(LastRolled == -1){
           LastRolled = 6;
            return 6;
        }
        int number = r.nextInt(6) + 1;
        this.LastRolled = number;
        return  number;
    }
}
