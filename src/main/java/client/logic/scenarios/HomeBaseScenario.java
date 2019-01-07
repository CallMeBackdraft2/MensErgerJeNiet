package client.logic.scenarios;

import client.domain.classes.FakeDice;
import client.logic.localimplementation.LocalFourPlayerGame;
import shared.interfaces.Game;

public class HomeBaseScenario implements Scenario {

    @Override
    public Game init() throws Exception {
        LocalFourPlayerGame game = new LocalFourPlayerGame(true);
        FakeDice fakeDice = new FakeDice(new int[]{6,5,2,2,2,6,5,2,2,2,6,4});
        game.setDice(fakeDice);

        game.rollDice();
        for (int i = 0; i < 8; i++) {
            game.movePawn("REP01");
        }
        game.rollDice();
        game.movePawn("REP01");

        game.rollDice();
        game.rollDice();
        game.rollDice();
        game.rollDice();
        for (int i = 0; i < 8; i++) {
            game.movePawn("REP02");
        }
        /**
        for (int i = 0; i < 7; i++) {
            game.movePawn("REP03");
        }
        game.rollDice();
        game.movePawn("REP03");

        game.rollDice();
        game.rollDice();
        game.rollDice();
        game.rollDice();

        for (int i = 0; i < 7; i++) {
            game.movePawn("REP04");
        }
        **/
        game.setDebugMode(false);
        //game.rollDice();
        //game.movePawn("REP04");
        return game;
    }
}
