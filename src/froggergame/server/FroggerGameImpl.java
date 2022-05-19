package froggergame.server;

import froggergame.client.PlayerRI;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class FroggerGameImpl implements FroggerGameRI {
    private int id;
    private ArrayList<PlayerRI> players = new ArrayList<>();

    /**
     * Adiciona um worker ao jobgroup
     *
     * @param player worker a adicionar
     */
    //attach
    @Override
    public void addPlayer(PlayerRI player) {
        if (!this.players.contains(player)) {
            this.players.add(player);
        }
    }

    public void removePlayer(PlayerRI playerRI) {
        this.players.remove(playerRI);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id= " + id +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<PlayerRI> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerRI> players) {
        this.players = players;
    }

    /**
     * Pesquisa o player pelo id
     *
     * @param playerID id worker
     * @return retorna o worker ou null se nao encontrar
     */
    public PlayerRI getPlayer(int playerID) throws RemoteException {
        for (PlayerRI p : this.players) {
            if (p.getId() == playerID) {
                return p;
            }
        }
        return null;
    }
}

