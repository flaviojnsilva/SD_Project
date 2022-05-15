package froggergame.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameSessionImpl implements GameSessionRI {

    private GameFactoryImpl game;
    private ArrayList<GameGroupRI> gamegroups;

    private String email;

    public GameSessionImpl(GameFactoryImpl gameFactory, String email) throws RemoteException {
        super();
        this.game = gameFactory;
        this.email = email;
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public boolean createGameGroup(int id, String filename) throws RemoteException {
        return false;

    }

    @Override
    public String listGameGroup() throws RemoteException {
        return this.gamegroups.toString();
    }

    @Override
    public void logout() throws RemoteException {
        this.game.destroySession(this.email);
    }
}
