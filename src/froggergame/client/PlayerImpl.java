package froggergame.client;


import froggergame.client.frogger.Main;
import froggergame.server.FroggerGameRI;
import froggergame.server.State;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PlayerImpl extends UnicastRemoteObject implements PlayerRI {

    private int id;
    private FroggerGameRI froggerGameRI;
    private Main main;

    public PlayerImpl(int id) throws RemoteException {
        this.id = id;
    }

    /**
     * Efetua o update do estado do jogo
     *
     * @param state
     * @throws RemoteException
     */
    @Override
    public void updateGameState(State state) throws RemoteException {
        System.out.println(main);
        this.main.receiveState(state);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public FroggerGameRI getFroggerGameRI() {
        return froggerGameRI;
    }

    @Override
    public void setFroggerGameRI(FroggerGameRI froggerGameRI) {
        this.froggerGameRI = froggerGameRI;
    }

    @Override
    public void setMain(Main main) throws RemoteException {
        this.main = main;
    }
}


