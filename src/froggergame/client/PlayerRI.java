package froggergame.client;

import froggergame.server.FroggerGameRI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerRI extends Remote {
    int getId() throws RemoteException;

    FroggerGameRI getFroggerGameRI() throws RemoteException;

    void setFroggerGameRI(FroggerGameRI gameGroup) throws RemoteException;
}
