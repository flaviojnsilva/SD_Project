package froggergame.server;

import froggergame.client.PlayerRI;

import java.io.Serializable;
import java.util.ArrayList;

public interface FroggerGameRI extends Serializable {
    void addPlayer(PlayerRI player);

    void removePlayer(PlayerRI player);

    int getId();

    ArrayList<PlayerRI> getPlayers();
}
