package froggergame.server;

import froggergame.client.PlayerRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameSessionRI extends Remote {
    public Game createGame(int id, int difficulty, int maxPlayers, PlayerRI PlayerRI, String token) throws RemoteException;

    Game chooseGame(int idG, PlayerRI PlayerRI, String token) throws RemoteException;

    public ArrayList<Game> listFroggerGames() throws RemoteException;

    void setToken(String token) throws RemoteException;

    void setEmail(String email) throws RemoteException;
}
