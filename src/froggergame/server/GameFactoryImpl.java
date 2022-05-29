package froggergame.server;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * <p>Title: Projecto SD</p>
 * <p>Description: Projecto apoio aulas SD</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: UFP </p>
 *
 * @author Rui S. Moreira
 * @version 3.0
 */

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {
    private DBMockup dbMockup;
    private HashMap<String, GameSessionImpl> match_userSession;

    public GameFactoryImpl() throws RemoteException {
        super();
        this.dbMockup = new DBMockup();
        this.match_userSession = new HashMap<>();
    }

    @Override
    public GameSessionRI login(String email, String password) throws RemoteException {
        if (this.dbMockup.exists(email, password)) {
            if (match_userSession.containsKey(email)) {
                return match_userSession.get(email);
            } else {
                GameSessionImpl gameSession = new GameSessionImpl(this, email);
                match_userSession.put(email, gameSession);
                return gameSession;
            }
        } else {
            System.out.println("TESTE USER ERRADOS");
            return null;
        }
    }

    @Override
    public boolean register(String email, String password) throws RemoteException {
        return this.dbMockup.register(email, password);
    }

    public DBMockup getDbMockup() {
        return dbMockup;
    }
}