package client.domain.classes;

import java.util.List;
import java.util.Random;


public class FairDice implements Dice {
    // Fields
    private Random r;
    private int lastRolled;
    private int amountRolled = 0;

    // Constructor
    public FairDice() {
        r = new Random();
        lastRolled = 0;
    }

    // Properties
    public int getLastRolled() {
        return lastRolled;
    }

    // Methods
    public int rollDice() {
        amountRolled++;
        if(amountRolled==1){lastRolled = 6; return lastRolled;}
        int number = r.nextInt(6) + 1;
        this.lastRolled = number;
        return number;

    }

    public int getAmountRolled() {
        return amountRolled;
    }

}
