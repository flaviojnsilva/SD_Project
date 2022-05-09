package server;

import client.PlayerRI;

import java.rmi.Remote;
import java.util.ArrayList;

public interface GameGroupRI extends Remote {
    void addPlayer(PlayerRI player);
    void removePlayer(PlayerRI player);
    int getId();
    ArrayList<PlayerRI> getPlayers();
}
