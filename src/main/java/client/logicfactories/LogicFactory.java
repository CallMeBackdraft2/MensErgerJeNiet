package client.logicfactories;

import client.domain.classes.Lobby;
import client.logic.interfaces.Game;
import client.logic.localimplementation.LocalFourPlayerGame;
import client.logic.localimplementation.MultiplayerFourPlayerGame;

public class LogicFactory {

    private LogicFactory(){}

    public static Game getLocalFourPlayerGame(){

        return new LocalFourPlayerGame(false);

    }

    public static Game getLocalFourPlayerGame(Lobby lobby){
        return new LocalFourPlayerGame(lobby);
    }

    public static Game getLocalFourPlayerGameTest(){

        return new LocalFourPlayerGame(true);

    }
    public static Game getOnlineFourPlayerGame(){

        return new MultiplayerFourPlayerGame();

    }

}
