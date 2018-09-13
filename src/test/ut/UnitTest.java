package ut;

import classes.Dice;
import classes.GameBoard;
import enums.gameType;
import enums.playerColor;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertThat;


public class UnitTest {
    @Test
    public void DicePercentageTest(){
        Dice dice = new Dice();
        int iterations = 10000000;
        int oneCount = 0;
        int twoCount = 0;
        int threeCount = 0;
        int fourCount = 0;
        int fiveCount = 0;
        int sixCount = 0;
        for(int i = 0; i <= iterations; i++){
            switch (dice.rollDice()){
                case ONE:
                    oneCount++;
                    break;
                case TWO:
                    twoCount++;
                    break;
                case THREE:
                    threeCount++;
                    break;
                case FOUR:
                    fourCount++;
                    break;
                case FIVE:
                    fiveCount++;
                    break;
                case SIX:
                    sixCount++;
                    break;
            }
        }
        System.out.println((oneCount*100)/iterations);
        System.out.println((twoCount*100)/iterations);
        System.out.println((threeCount*100)/iterations);
        System.out.println((fourCount*100)/iterations);
        System.out.println((fiveCount*100)/iterations);
        System.out.println((sixCount*100)/iterations);

        assertThat((oneCount*100)/iterations, CoreMatchers.allOf(Matchers.greaterThan(14), Matchers.lessThan(17)));
        assertThat((twoCount*100)/iterations, CoreMatchers.allOf(Matchers.greaterThan(14), Matchers.lessThan(17)));
        assertThat((threeCount*100)/iterations, CoreMatchers.allOf(Matchers.greaterThan(14), Matchers.lessThan(17)));
        assertThat((fourCount*100)/iterations, CoreMatchers.allOf(Matchers.greaterThan(14), Matchers.lessThan(17)));
        assertThat((fiveCount*100)/iterations, CoreMatchers.allOf(Matchers.greaterThan(14), Matchers.lessThan(17)));
        assertThat((sixCount*100)/iterations, CoreMatchers.allOf(Matchers.greaterThan(18), Matchers.lessThan(21)));
    }

    @Test
    public void testPlayerTurnSwitch(){
        GameBoard gb = new GameBoard(gameType.FOURPLAYER);
        gb.setPlayerTurn(playerColor.YELLOW);
        gb.switchPlayerTurn();
        Assert.assertEquals(playerColor.RED, gb.getPlayerWithTurn());
    }
}
