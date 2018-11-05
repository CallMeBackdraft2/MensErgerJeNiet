package ut;

import domain.Enums.GameMode;
import domain.Enums.PawnState;
import logic.interfaces.Game;
import logicFactories.LogicFactory;
import org.junit.*;

public abstract class GameTest {

    Game game;

    //public abstract Game createInstance();

    @Before
    public void initGame(){
        game = LogicFactory.getLocalFourPlayerGameTest();
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
        while(true){
            if(game.rollDice() == 6){
                break;
            }
        }
        game.movePawn("GRP01");
    }

    @Test
    public void hitPawnTest() {
        game.readyUp();

        for (int i = 0; i < 3; i++) {
            while (true) {
                if (game.rollDice() == 6) {
                    break;
                }
            }
            game.movePawn("REP01");
        }

        while (true) {
            if (game.rollDice() == 6) {
                break;
            }
        }
        game.movePawn("BLP01");

        while (true) {
            if (game.rollDice() == 2) {
                break;
            }
        }
        game.movePawn("BLP01");


        Assert.assertTrue(game.getPawn("REP01").getPawnState() == PawnState.STARTPOSITION);
    }
}
