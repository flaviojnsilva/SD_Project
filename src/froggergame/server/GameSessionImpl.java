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

        Game game = gameFactoryImpl.getDbMockup().insert(id, diff, maxPlayers, froggerGameRI);
        game.getFroggerGameRI().addPlayer(playerRI);

        return game;
    }

    public Game chooseGame(int jogo, PlayerRI playerRI) throws RemoteException {

        Game game = gameFactoryImpl.getDbMockup().select(jogo);

        this.join(jogo, playerRI);
        return game;
    }


    public Game join(int id, PlayerRI playerRI) throws RemoteException {

        Game game = gameFactoryImpl.getDbMockup().select(id);
        game.getFroggerGameRI().addPlayer(playerRI);

        return game;
    }

    @Override
    public String listGame() throws RemoteException {

        //fazer logs e confirmar se gamegroups realmente null
        //Imprimir arraylist de jogos
        for (FroggerGameRI item : this.gamegroups) {
            System.out.print(item);
        }
        return null;
    }

    @Override
    public void logout() throws RemoteException {
        this.gameFactoryImpl.destroySession(this.email);
    }
}

