package client.rest;

import client.domain.classes.LobbyView;

public class LobbyClientMain {

    public static void main(String[] args) {
        LobbyClient client = new LobbyClient();
        LobbyView l = new LobbyView(1);
        client.addLobby(l);
        LobbyView b = new LobbyView(2);
        client.addLobby(b);

    }
}
