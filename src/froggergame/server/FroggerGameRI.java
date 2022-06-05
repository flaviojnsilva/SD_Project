package froggergame.server;

import froggergame.client.PlayerRI;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface FroggerGameRI extends Remote {
    void add(PlayerRI frogger) throws RemoteException;
    void notifyObserver(State state) throws RemoteException;
    int getFrogger() throws RemoteException;
    int getId() throws RemoteException;
    void setFroggerGameState(State froggerGameState) throws RemoteException;
    ArrayList<PlayerRI> getFroggers() throws RemoteException;
}
