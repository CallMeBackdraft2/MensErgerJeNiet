package logicFactories;

import logic.interfaces.Game;
import logic.localImplementation.LocalFourPlayerGame;

public class LogicFactory {

    public static Game getLocalFourPlayerGame(){

        return new LocalFourPlayerGame(false);

    }
    public static Game getLocalFourPlayerGameTest(){

        return new LocalFourPlayerGame(true);

    }


}
