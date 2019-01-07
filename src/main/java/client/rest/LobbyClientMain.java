package client.rest;

import client.domain.classes.Lobby;

public class LobbyClientMain {

    public static void main(String[] args) {
        LobbyClient client = new LobbyClient();
        Lobby l = new Lobby(1);
        client.addLobby(l);
        Lobby b = new Lobby(2);
        client.addLobby(b);

    }
}
