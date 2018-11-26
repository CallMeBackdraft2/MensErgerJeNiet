package server.logic;

import server.MessageReceiver;
import shared.Message;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements MessageReceiver {



    List<MultiplayerFourPlayerGame> games = new ArrayList<>();

    public GameManager() {

    }

    public MultiplayerFourPlayerGame createNewGame(){

        MultiplayerFourPlayerGame game =new MultiplayerFourPlayerGame();
        games.add(game);
        return game;
    }

    @Override
    public void onMessageReceived(Message message) {


    }
}
