package ut;

import domain.Classes.Dice;
import domain.Classes.Lobby;
import domain.Classes.Player;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThat;


public class    UnitTest {
    @Test
    public void dicePercentageTest() {
        Dice dice = new Dice();
        int iterations = 10000000;
        int oneCount = 0;
        int twoCount = 0;
        int threeCount = 0;
        int fourCount = 0;
        int fiveCount = 0;
        int sixCount = 0;
        for (int i = 0; i <= iterations; i++) {
            switch (dice.rollDice()) {
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
        assertThat((oneCount * 100) / iterations, CoreMatchers.allOf(Matchers.greaterThan(14), Matchers.lessThan(17)));
        assertThat((twoCount * 100) / iterations, CoreMatchers.allOf(Matchers.greaterThan(14), Matchers.lessThan(17)));
        assertThat((threeCount * 100) / iterations, CoreMatchers.allOf(Matchers.greaterThan(14), Matchers.lessThan(17)));
        assertThat((fourCount * 100) / iterations, CoreMatchers.allOf(Matchers.greaterThan(14), Matchers.lessThan(17)));
        assertThat((fiveCount * 100) / iterations, CoreMatchers.allOf(Matchers.greaterThan(14), Matchers.lessThan(17)));
        assertThat((sixCount * 100) / iterations, CoreMatchers.allOf(Matchers.greaterThan(18), Matchers.lessThan(21)));
    }

    @Test
    public void lobbyTest(){
        Player player = new Player("Dennis");
        Lobby lobby = new Lobby(player);

        Assert.assertEquals(player, lobby.getPlayers().get(0));
    }
    
}
