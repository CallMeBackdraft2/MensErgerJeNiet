package client.logicFactories;

import client.domain.Classes.Lobby;
import client.logic.interfaces.Game;
import client.logic.localImplementation.LocalFourPlayerGame;

public class LogicFactory {

    public static Game getLocalFourPlayerGame(){

        return new LocalFourPlayerGame(false);

    }

    public static Game getLocalFourPlayerGame(Lobby lobby){
        return new LocalFourPlayerGame(lobby);
    }

    public static Game getLocalFourPlayerGameTest(){

        return new LocalFourPlayerGame(true);

    }


}
