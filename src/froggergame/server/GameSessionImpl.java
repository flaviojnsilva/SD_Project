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
//create game
    @Override
    public boolean createGameGroup(int id, String name) throws RemoteException {

        GameGroupImpl gameGroup = new GameGroupImpl(id);
        if (this.email == null) {
            this.gamegroups.add(gameGroup);
            //GameServer.addGameGroup(gameGroup);
            return true; //JobGroup criado com sucesso
        }
        return false;
    }

    @Override
    public String listGameGroup() throws RemoteException {
        if (this.gamegroups.toString() != null) {

            return this.gamegroups.get(0).toString();
        } else {
            return this.email;
        }
    }

    @Override
    public void logout() throws RemoteException {
        this.game.destroySession(this.email);
    }
}
