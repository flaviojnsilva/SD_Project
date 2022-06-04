package froggergame.server;

import java.io.Serializable;

public class Game implements Serializable {
    int id = 0;
    private int maxPlayers;
    private int difficulty;
    private FroggerGameRI froggerGameRI;

    public Game(int id, int maxPlayers, int difficulty, FroggerGameRI froggerGameRI) {
        this.id = id;
        this.maxPlayers = maxPlayers;
        this.difficulty = difficulty;
        this.froggerGameRI = froggerGameRI;
    }

    public int getId() {
        return id;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public FroggerGameRI getFroggerGameRI() {
        return this.froggerGameRI;
    }
}
