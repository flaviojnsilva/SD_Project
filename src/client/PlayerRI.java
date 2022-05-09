package client;

import server.GameGroupRI;

import java.rmi.RemoteException;

public interface PlayerRI {
    int getId();

    GameGroupRI getGameGroup() throws RemoteException;

    void setGameGroup(GameGroupRI gameGroup) throws RemoteException;
}
