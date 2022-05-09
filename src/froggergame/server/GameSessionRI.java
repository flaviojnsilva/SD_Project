package froggergame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <p>Title: Projecto SD</p>
 * <p>Description: Projecto apoio aulas SD</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: UFP </p>
 *
 * @author Rui Moreira
 * @version 1.0
 */

public interface GameSessionRI extends Remote {
    public void logout() throws RemoteException;

    //TODO() acrescentar jogo e juntar-se ao jogo + método jogos ativos no momento a juntar ao jogo

    //TODO() com classe State para ter o estado atual do jogo: notifiyAll, attach e dettach + getState e setState (servidor) ->> checkar aul do observer
    //TODO() lado do cliente método updateState()

}