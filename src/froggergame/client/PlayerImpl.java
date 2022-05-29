package froggergame.client;

import froggergame.server.FroggerGameRI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PlayerImpl extends UnicastRemoteObject implements PlayerRI {

    private int id;
    private FroggerGameRI gameGroupRI;

    public PlayerImpl(int id) throws RemoteException {
        super();
        this.id = id;
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