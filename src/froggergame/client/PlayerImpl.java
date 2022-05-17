package froggergame.client;

import froggergame.server.FroggerGameRI;

import java.rmi.RemoteException;

public class PlayerImpl implements PlayerRI {

    private int id;

    private FroggerGameRI gameGroupRI;

    public PlayerImpl(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public FroggerGameRI getFroggerGameRI() throws RemoteException {
        return null;
    }

    @Override
    public void setFroggerGameRI(FroggerGameRI gameGroup) throws RemoteException {

    }

    @Override
    public String toString() {
        return "Player{" +
                "ID: " + id + " EMAIl: " +
                '}' + "\n";
    }
}