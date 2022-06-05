package froggergame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameFactoryRI extends Remote {

     GameSessionRI login(String u, String p) throws RemoteException;
     boolean register(String u , String p) throws RemoteException;
}
