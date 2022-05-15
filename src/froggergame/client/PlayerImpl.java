package froggergame.client;

import froggergame.server.GameGroupRI;

import java.rmi.RemoteException;

public class PlayerImpl implements PlayerRI {

    private int id;

    private String email;
    private GameGroupRI gameGroupRI;

    public PlayerImpl(int id, String email) {
        this.id = id;
        this.email = email;
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
                "ID: " + id + " EMAIl: " + email +
                '}' + "\n";
    }
}