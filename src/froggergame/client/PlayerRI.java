package froggergame.client;


import froggergame.client.frogger.Main;
import froggergame.server.FroggerGameRI;
import froggergame.server.State;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerRI extends Remote {
    void updateGameState(State state) throws RemoteException;
    int getId() throws RemoteException;
    void setId(int id) throws RemoteException;
    FroggerGameRI getFroggerGameRI() throws RemoteException;
    void setFroggerGameRI(FroggerGameRI froggerGameRI) throws RemoteException;
    void setMain(Main main) throws RemoteException;

}
