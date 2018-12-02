package server.logic;


import client.domain.classes.Player;

import javax.websocket.Session;

public class ServerPlayer {

    private int id;

    public int getId() {
        return id;
    }

    public ServerPlayer setId(int id) {
        this.id = id;
        return this;
    }

    private Session session;
    private String username;

    public ServerPlayer(int id, Session session, String username) {
        this.id = id;
        this.session = session;
        this.username = username;
    }

    public Session getSession() {
        return session;
    }

    public String getUsername() {
        return username;
    }

    public ServerPlayer setUsername(String username) {
        this.username = username;
        return this;
    }

    public Player toPlayer() {
        return new Player(id, username);
    }
}
