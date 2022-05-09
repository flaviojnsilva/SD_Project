package client;

import server.GameGroupRI;

import java.rmi.RemoteException;

public class PlayerImpl implements PlayerRI {

    private int id;
    private GameGroupRI gameGroupRI;

    public PlayerImpl(int id) {
        this.id = id;
    }

    @Override
    public void setGameGroup(GameGroupRI gameGroup) {
        this.gameGroupRI = gameGroup;
    }

    @Override
    public GameGroupRI getGameGroup() throws RemoteException {
        return null;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ",gamegroup " + gameGroupRI.getId() +
                '}';
    }
}