package froggergame.server;

import froggergame.client.PlayerRI;

import java.io.Serializable;

public class Game implements Serializable {
    int id = 0;

    private int maxPlayers;

    private int difficulty;

    private FroggerGameRI froggerGameRI;

    private PlayerRI playerRI;

    GameFactoryImpl gameFactoryImpl;

    public Game(int maxPlayers, int difficulty, FroggerGameRI froggerGameRI) {
        this.maxPlayers = maxPlayers;
        this.difficulty = difficulty;
        this.froggerGameRI = froggerGameRI;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public FroggerGameRI getFroggerGameRI() {
        return froggerGameRI;
    }
}
