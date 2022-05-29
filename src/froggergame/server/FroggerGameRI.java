package froggergame.server;

import froggergame.client.PlayerRI;
import java.io.Serializable;

public interface FroggerGameRI extends Serializable {
    void addPlayer(PlayerRI player);
}
