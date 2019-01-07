package client.logic.scenarios;

import client.logic.localimplementation.LocalFourPlayerGame;
import shared.interfaces.Game;

public class GameDebugStartScenario implements Scenario {

    @Override
    public Game init()  {
        Game game = new LocalFourPlayerGame(true);
        return game;
    }
}

