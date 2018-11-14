package logicFactories;

import domain.Classes.Lobby;
import logic.interfaces.Game;
import logic.localImplementation.LocalFourPlayerGame;

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
