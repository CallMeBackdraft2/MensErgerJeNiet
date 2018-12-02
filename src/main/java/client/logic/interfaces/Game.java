package client.logic.interfaces;

import client.domain.classes.Pawn;
import client.domain.classes.Player;
import client.domain.classes.Tile;

import java.util.List;

public interface Game {

    void skipTurn();
    boolean getNeedsUpdate();
    void setNeedsUpdate(boolean bool);
    int rollDice();
    int getDiceRolled();
    int getDiceAmountRolled();

    void movePawn(String pawnId);
    Tile[] getTiles();
    Tile getPossibleMove(String pawnId);
    boolean isYourTurn();
    Pawn getPawn(String homeTileID);
    Pawn[] getPawns();
    int getCurrentPlayerId();
    Player[] getPlayers();
    void sendMessage(String message);
    String[] getMessages();
    boolean getIsDone();
}
