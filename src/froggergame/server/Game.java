package froggergame.server;

import froggergame.client.PlayerRI;

import java.io.Serializable;

public class Game implements Serializable {

    int id = 0;
    private int maxPlayers;
    private int difficulty;
    private FroggerGameRI froggerGameRI;
    private PlayerRI PlayerRI;

    public Game(int difficulty,int maxPlayers, FroggerGameRI froggerGameRI) {
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.froggerGameRI = froggerGameRI;
    }


    public int getId() {
        return id;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public FroggerGameRI getFroggerRI() {
        return froggerGameRI;
    }
}
