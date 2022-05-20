package froggergame.server;

import froggergame.client.PlayerRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameSessionImpl implements GameSessionRI {

    private GameFactoryImpl gameFactoryImpl;
    private ArrayList<FroggerGameRI> gamegroups;

    private String email;

    public GameSessionImpl(GameFactoryImpl gameFactory, String email) throws RemoteException {
        super();
        this.gameFactoryImpl = gameFactory;
        this.email = email;
        UnicastRemoteObject.exportObject(this, 0);
    }

    //create game
    @Override
    public Game createGame(int id, int diff, int maxPlayers, PlayerRI playerRI) throws RemoteException {

        FroggerGameRI froggerGameRI = new FroggerGameImpl();

        return gameFactoryImpl.getDbMockup().insert(diff, maxPlayers, froggerGameRI);
    }

    @Override
    public String listGame() throws RemoteException {
        if (this.gamegroups.toString() != null) {

            return this.gamegroups.get(0).toString();
        } else {
            return null;
        }
    }

    @Override
    public void logout() throws RemoteException {
        this.gameFactoryImpl.destroySession(this.email);
    }
}
