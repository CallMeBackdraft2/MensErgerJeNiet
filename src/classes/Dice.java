package classes;

import enums.diceNumber;

import java.util.Date;
import java.util.Random;

public class Dice {
    private diceNumber rolledValue;
    private long randomSeed;

    public Dice(){
        //use the current date and time converted to a long to create the seed for the random number generator
        this.randomSeed = new Date().getTime();
    }

    public diceNumber rollDice(){
        //instantiate the random number generator with the randomSeed value as seed
        Random rand = new Random(randomSeed);
        //use the random number generator to get a random value between 0 and 5 then adding one to get the correct bounds for the dice
        //this gets cast into the diceNumber enum to store the correct vallue
        this.rolledValue = diceNumber.values()[rand.nextInt(6) + 1];
        return rolledValue;
    }

    public diceNumber getRolledValue(){
        return this.rolledValue;
    }
}
