package froggergame.server;

import froggergame.client.PlayerRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * <p>Title: Projecto SD</p>
 * <p>Description: Projecto apoio aulas SD</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: UFP </p>
 *
 * @author Rui Moreira
 * @version 1.0
 */

public interface GameSessionRI extends Remote {
     Game createGame(int id, int diff, int maxPlayers, PlayerRI playerRI, String token) throws RemoteException;

     ArrayList<Game> listGame() throws RemoteException;

    void setEmail(String email) throws RemoteException;

    void setStringToken(String stringToken) throws RemoteException;

    Game chooseGame(int jogo, PlayerRI playerRI) throws RemoteException;
}