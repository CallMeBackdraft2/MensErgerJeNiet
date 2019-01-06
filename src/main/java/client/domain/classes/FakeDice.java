package client.domain.classes;

public class FakeDice implements Dice {

    private int[] values;
    private int curIndex;
    private int amountRolled= 0;
    private int lastRolled = 0;
    public FakeDice(int[] values) {
        this.values = values;
        curIndex =-1;
    }


    private void setValues(int[] values){
        this.values = values;
    }


    @Override
    public int getLastRolled() {
       return lastRolled;
    }

    @Override
    public int rollDice() {
        curIndex++;
        amountRolled++;
        if(curIndex>=values.length){
            curIndex =0;

        }
        lastRolled = values[curIndex];

        return lastRolled;
    }

    @Override
    public int getAmountRolled() {
        return amountRolled;
    }
}
