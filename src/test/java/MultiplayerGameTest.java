import client.logic.interfaces.Game;
import client.logicfactories.LogicFactory;
import org.junit.*;
import server.websockets.ServerWebsocketsApplication;

public class MultiplayerGameTest {

    Game game;

    //public abstract Game createInstance();

    @Before
    public void initGame() {
        //game = LogicFactory.getLocalFourPlayerGameTest();

        try {
            ServerWebsocketsApplication.main(new String[]{"true"});
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game = LogicFactory.getOnlineFourPlayerGame();

    }

    @After
    public void after() throws Exception {
        game =null;
        ServerWebsocketsApplication.stop();
        int c=5;
    }

    @Test
    public void testRollDice() throws Exception {
        int roll = game.rollDice();

        Assert.assertTrue(roll > -1 && roll < 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalMove() throws Exception {

        while (true) {
            if (game.rollDice() != 6) {
                break;
            }
        }
        game.movePawn("YEP01");
    }

    @Test
    public void possibleMovesTest() throws Exception {
        while (true) {
            if (game.rollDice() != 6 && game.getCurrentPlayerId()==1) {
                break;
            }
        }
        Assert.assertNull(game.getPossibleMove(game.getPawn("REP01").getFullId()));

        while (true) {
            if (game.rollDice() == 6 && game.getCurrentPlayerId()==1) {
                break;
            }
        }
        Assert.assertNotNull(game.getPossibleMove(game.getPawn("REP01").getFullId()));

    }

    @Test
    public void testGetPawns() throws Exception {
        Assert.assertNotNull(game.getPawns());
    }

    @Test
    public void testGetTiles() throws Exception {
        Assert.assertNotNull(game.getTiles());
    }

    @Test
    public void movePawn() throws Exception {
        while (true) {
            if (game.rollDice() == 6 && game.getCurrentPlayerId() == 3) {
                break;
            }
        }
        game.movePawn("GRP01");
        Assert.assertEquals("WLK01",game.getPawn("GRP01").getPawnTileId());
    }

    @Test
    public void hitPawnTest() throws Exception {

        for (int i = 0; i < 2; i++) {
            while (true) {
                if (game.rollDice() == 6 && game.getCurrentPlayerId()==0) {
                    break;
                }
            }
            game.movePawn("REP01" );
        }

        while (true) {
            if (game.rollDice() == 5 && game.getCurrentPlayerId()==0) {
                break;
            }
        }
        game.movePawn("REP01");


        while (game.rollDice() != 6 || game.getCurrentPlayerId() != 1) {
            if (game.getCurrentPlayerId() != 1) {
                game.skipTurn();
            }
        }
        game.movePawn("BLP01");

        while (game.rollDice() != 1 || game.getCurrentPlayerId() != 1) {
            if (game.getCurrentPlayerId() != 1) {
                game.skipTurn();
            }
        }
        game.movePawn("BLP01");


        Assert.assertEquals('P', game.getPawn("REP01").getPawnTileId().charAt(2));
    }

    @Test
    public void hitOwnPawnTest() throws Exception {


        while (true) {
            if (game.rollDice() == 6 && game.getCurrentPlayerId() == 0) {
                break;
            }
        }
        for (int i = 0; i < 3; i++) {
            game.movePawn("REP01");
        }

        Assert.assertEquals('K', game.getPawn("REP01").getPawnTileId().charAt(2));

        for (int i = 0; i < 3; i++) {

            game.movePawn("REP02");
        }


        Assert.assertEquals('P', game.getPawn("REP01").getPawnTileId().charAt(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void winTest() throws Exception {

        //Move red pawn 1 to home base
        for (int i = 0; i < 7; i++) {
            while (true) {
                if (game.rollDice() == 6 && game.getCurrentPlayerId()==0) {
                    break;
                }
            }
            game.movePawn("REP01" );
        }

        while (true) {
            if (game.rollDice() == 4 && game.getCurrentPlayerId()==0) {
                break;
            }
        }
        game.movePawn("REP01" );

        //Move red pawn 2 to home base
        for (int i = 0; i < 7; i++) {
            while (true) {
                if (game.rollDice() == 6 && game.getCurrentPlayerId()==0) {
                    break;
                }
            }
            game.movePawn("REP02" );
        }

        while (true) {
            if (game.rollDice() == 5 && game.getCurrentPlayerId()==0) {
                break;
            }
        }
        game.movePawn("REP02" );

        //Move red pawn 3 to home base
        for (int i = 0; i < 8; i++) {
            while (true) {
                if (game.rollDice() == 6 && game.getCurrentPlayerId()==0) {
                    break;
                }
            }
            game.movePawn("REP03" );
        }
        //Move red pawn 4 to home base
        for (int i = 0; i < 6; i++) {
            while (true) {
                if (game.rollDice() == 6 && game.getCurrentPlayerId()==0) {
                    break;
                }
            }
            game.movePawn("REP04" );
        }

        while (true) {
            if (game.rollDice() == 5 && game.getCurrentPlayerId()==0) {
                break;
            }
        }
        game.movePawn("REP04" );

        while (true) {
            if (game.rollDice() == 4 && game.getCurrentPlayerId()==0) {
                break;
            }
        }
        game.movePawn("REP04" );

        while (true) {
            if (game.rollDice() == 4 && game.getCurrentPlayerId()==0) {
                break;
            }
        }
        game.movePawn("REP04" );

        Assert.assertEquals("REH01",game.getPawn("REP01").getPawnTileId());
        Assert.assertEquals("REH02",game.getPawn("REP02").getPawnTileId());
        Assert.assertEquals("REH03",game.getPawn("REP03").getPawnTileId());
        Assert.assertEquals("REH04",game.getPawn("REP04").getPawnTileId());

        Assert.assertTrue(game.getIsDone());
    }
}
