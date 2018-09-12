package classes;

import enums.diceNumber;

import java.util.Date;
import java.util.Random;

public class Dice {
    private diceNumber rolledValue;
    private long randomSeed;
    private Random rand;

    public Dice(){
        //use the current date and time converted to a long to create the seed for the random number generator
        this.randomSeed = new Date().getTime();
        //instantiate the random number generator with the randomSeed value as seed
        rand = new Random(randomSeed);
    }

    public diceNumber rollDice(){
        //use the random number generator to get a random value between 0 and 5 then adding one to get the correct bounds for the dice
        //this gets cast into the diceNumber enum to store the correct value
        this.rolledValue = convertToDiceNumber(rand.nextDouble());
        return rolledValue;
    }

    private diceNumber convertToDiceNumber(double rolledValue){
        diceNumber result = null;
        //The below IF containers are used to be able to control the chances of getting a certain diceroll
        if(rolledValue < 0.16){
            result = diceNumber.ONE;
        } else if (rolledValue < 0.32) {
            result = diceNumber.TWO;
        } else if (rolledValue < 0.48) {
            result = diceNumber.THREE;
        } else if (rolledValue < 0.64) {
            result = diceNumber.FOUR;
        } else if (rolledValue < 0.80) {
            result = diceNumber.FIVE;
        } else if (rolledValue <= 1) {
            result = diceNumber.SIX;
        }
        return result;
    }

    public diceNumber getRolledValue(){
        return this.rolledValue;
    }
}
