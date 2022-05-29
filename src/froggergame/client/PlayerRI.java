package froggergame.client;

import froggergame.server.FroggerGameRI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerRI extends Remote {
    void setFroggerGameRI(FroggerGameRI gameGroup) throws RemoteException;
}
