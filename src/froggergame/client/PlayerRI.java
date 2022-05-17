package froggergame.client;

import froggergame.server.FroggerGameRI;

import java.rmi.RemoteException;

public interface PlayerRI {
    int getId();

    FroggerGameRI getFroggerGameRI() throws RemoteException;

    void setFroggerGameRI(FroggerGameRI gameGroup) throws RemoteException;
}
