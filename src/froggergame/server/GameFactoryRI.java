package froggergame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <p>Title: Projecto SD</p>
 * <p>Description: Projecto apoio aulas SD</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: UFP </p>
 *
 * @author Rui Moreira
 * @version 1.0
 */

public interface GameFactoryRI extends Remote {
    GameSessionRI login(String username, String password) throws RemoteException;

    boolean register(String email, String password) throws RemoteException;

    void destroySession(String u) throws RemoteException;
}
