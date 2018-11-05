package ut;

import domain.Enums.GameMode;
import domain.Enums.PawnState;
import logic.interfaces.Game;
import org.junit.*;

public abstract class GameTest {

    Game game;

    public abstract Game createInstance();

    @Before
    public void initGame(){
        game = createInstance();
    }

    @Test
    public void testRollDice(){
        int roll = game.rollDice();

        Assert.assertTrue(roll > -1 && roll < 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMovePawn(){
        while(true) {
            if(game.rollDice() != 6) {
                break;
            }
        }
        game.movePawn("REP01");
    }

    @Test
    public void possibleMovesTest(){
        Assert.assertNotNull(game.getPossibleMove(game.getPawn("REP01")));
    }


    @Test
    public void movePawn(){
        game.getPawn("WLK01").movePawnIntoPlay();

        Assert.assertTrue(game.getPawn("WLK01").getPawnState() == PawnState.INPLAY);
    }

    @Test
    public void hitPawn(){

    }

}
