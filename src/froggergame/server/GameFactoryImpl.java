package froggergame.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {

    public DBMockup dbMockup;
    public HashMap<String, GameSessionImpl> user_session;

    public GameFactoryImpl() throws RemoteException {
        super();
        this.dbMockup = new DBMockup();
        this.user_session = new HashMap<>();
    }

    /**
     * Metodo Login do utilizador
     * @param username
     * @param pwd
     * @return
     * @throws RemoteException
     */
    public GameSessionRI login(String username, String pwd) throws RemoteException {
        if (this.dbMockup.exists(username,pwd)){
            if (user_session.containsKey(username)){
                System.out.println("Login com sucesso.");
                return user_session.get(username);
            }
            else{
                GameSessionImpl gameSessionImpl = new GameSessionImpl(this) ;
                user_session.put(username, gameSessionImpl);
                System.out.println("Sessao criada com sucesso.");
                return gameSessionImpl;
            }
        }
        else {
            System.out.println("Erro ao efetuar o Login! Dados errados.");
            return null;
        }
    }
    /**
     * Regista user na DBMockUp
     * @param username username a registar
     * @param pwd password a registar
     * @return true - Registado | false - Nao registado
     * @throws RemoteException
     */
    @Override
    public boolean register(String username, String pwd) throws RemoteException {
        return this.dbMockup.register(username, pwd);
    }
}