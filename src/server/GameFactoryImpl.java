package server;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private HashMap<String, GameImpl> match_userSession;

    public GameFactoryImpl() throws RemoteException {
        super();
        this.dbMockup = new DBMockup();
        this.match_userSession = new HashMap<>();
    }

    @Override
    public void print(String msg) throws RemoteException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "someone called me with msg = {0}", new Object[]{msg});
    }

    @Override
    public GameRI login(String u, String p) throws RemoteException {
        if (this.dbMockup.exists(u, p)) {
            if (match_userSession.containsKey(u)) {
                return match_userSession.get(u);
            } else {
                GameImpl diglibSession = new GameImpl(this, u);
                match_userSession.put(u, diglibSession);
                return diglibSession;
            }
        } else {
            return null;
        }
    }

    @Override
    public void register(String u, String p) throws RemoteException {
        this.dbMockup.register(u, p);
    }

    public void destroySession(String u) throws RemoteException {
        this.match_userSession.remove(u);
    }

    public DBMockup getDbMockup() {
        return dbMockup;
    }
}