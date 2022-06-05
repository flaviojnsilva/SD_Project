package froggergame.server;

import froggergame.client.PlayerRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class FroggerGameImpl extends UnicastRemoteObject implements FroggerGameRI {
    private int id;
    private State froggerGameState;
    private ArrayList<PlayerRI> froggers = new ArrayList<>();
    protected FroggerGameImpl() throws RemoteException {
        this.froggerGameState = new State(null, null);
    }

    /**
     * Metodo para fazer add de um frogger
     *
     * @param frogger
     * @throws RemoteException
     */
    @Override
    public void add(PlayerRI frogger) throws RemoteException {
        this.froggers.add(frogger);
    }
    /**
     * Notifica os Observers
     *
     * @param state
     * @throws RemoteException
     */
    @Override
    public void notifyObserver(State state) throws RemoteException {
        for (PlayerRI PlayerRI : froggers) {
            PlayerRI.updateGameState(state);
        }
    }

    @Override
    public int getFrogger() throws RemoteException {
        return froggers.size() + 1;
    }

    @Override
    public int getId() throws RemoteException {
        return id;
    }
    @Override
    public void setFroggerGameState(State froggerGameState) throws RemoteException {
        this.froggerGameState = froggerGameState;
        notifyObserver(froggerGameState);
    }

    @Override
    public ArrayList<PlayerRI> getFroggers() throws RemoteException {
        return froggers;
    }
}
