package client.logic.scenarios;

import client.domain.classes.FakeDice;
import client.logic.localimplementation.LocalFourPlayerGame;
import shared.interfaces.Game;

public class HitOwnPawnScenario implements Scenario {

    @Override
    public Game init() throws Exception {
        LocalFourPlayerGame game = new LocalFourPlayerGame(true);
        FakeDice fakeDice = new FakeDice(new int[]{6,6,2,2,2,6,6});
        game.setDice(fakeDice);
        for(int i = 0; i < 2; i++) {
            game.rollDice();
            game.movePawn("REP01");
        }
        game.rollDice();
        game.rollDice();
        game.rollDice();

        game.rollDice();
        game.movePawn("REP02");



        game.setDebugMode(false);
        //game.rollDice();
        //game.movePawn("REP04");
        return game;
    }
}
