package froggergame.server;

import froggergame.client.PlayerRI;
import java.util.ArrayList;

public class FroggerGameImpl implements FroggerGameRI {
    private int id;
    private ArrayList<PlayerRI> players = new ArrayList<>();

    /**
     * Adiciona um player ao jogo
     *
     * @param player a adicionar
     */
    //attach
    @Override
    public void addPlayer(PlayerRI player) {
        if (!this.players.contains(player)) {
            this.players.add(player);
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "id= " + id +
                '}';
    }

    public ArrayList<PlayerRI> getPlayers() {
        return players;
    }

}

