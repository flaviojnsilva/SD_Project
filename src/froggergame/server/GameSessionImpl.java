package froggergame.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameSessionImpl implements GameSessionRI {

    private GameFactoryImpl diglibFactory;
    private String username;
    public GameSessionImpl(GameFactoryImpl diglibFactory, String u) throws RemoteException {
        super();
        this.diglibFactory = diglibFactory;
        this.username = u;
        UnicastRemoteObject.exportObject(this,0);
    }

    @Override
    public void logout() throws RemoteException {
        this.diglibFactory.destroySession(this.username);
    }
}
