package client.domain.classes;

import client.domain.enums.PlayerColor;

public class Player {
    private int id;
    private String name;
    private PlayerColor playerColor;
    private int playerScore;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.playerColor = PlayerColor.values()[id];
    }

    public Player(String name){
        this.name = name;
    }

    //public setters
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    //public getters
    public PlayerColor getPlayerColor() {
        return this.playerColor;
    }


    public int getPlayerScore() {
        return this.playerScore;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name + " as " + playerColor;
    }
}
