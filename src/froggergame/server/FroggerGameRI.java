package froggergame.server;

import froggergame.client.PlayerRI;

import java.rmi.Remote;
import java.util.ArrayList;

public interface FroggerGameRI extends Remote {
    void addPlayer(PlayerRI player);
    void removePlayer(PlayerRI player);
    int getId();
    ArrayList<PlayerRI> getPlayers();
}
