package froggergame.server;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * This class simulates a DBMockup for managing users and books.
 *
 * @author rmoreira
 *
 */
public class DBMockup {
    private final ArrayList<User> users;
    private ArrayList<Game> games;

    /**
     * This constructor creates and inits the database with some books and users.
     */
    public DBMockup() {
        this.users = new ArrayList<>();
        this.games = new ArrayList<>();
    }


    /**
     * Registers a new user.
     * 
     * @param u email
     * @param p passwd
     */
    public boolean register(String u, String p) {
        if (getUser(u) == null) {
            users.add(new User(u, p));
            System.out.println("User Registado com sucesso!");
            return true;
        }
        return false;
    }

    /**
     * Checks the credentials of an user.
     * 
     * @param u email
     * @param p passwd
     * @return
     */
    public boolean exists(String u, String p) {
        for (User usr : this.users) {
            if (usr.getUname().compareTo(u) == 0 && usr.getPword().compareTo(p) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Seleciona o jogo
     * @param idG
     * @return
     * @throws RemoteException
     */
    public Game select(int idG) throws RemoteException{
        for (Game g : games) {
            if (g.getId() == idG) {
                return g;
            }
        }
        return null;
    }

    /**
     * Inserir jogo no array de jogos
     * @param dif
     * @param max
     * @param froggerGameRI
     * @return
     * @throws RemoteException
     */
    public Game insert(int dif, int max, FroggerGameRI froggerGameRI) throws RemoteException{
        Game game = new Game(dif, max, froggerGameRI);
        if (games.size() < 1){
            game.id = 1;
        }
        else{
           game.id = games.size() + 1;
        }
        games.add(game);
        return game;
    }

    /**
     * Imprime jogos disponiveis.
     * @return
     * @throws RemoteException
     */
    public ArrayList<Game> printGames() throws RemoteException{
        for (Game game : games)
            System.out.println(game.id);
        return games;
    }

    public User getUser(String email) {
        for(User u : this.users) {
            if(u.getUname().compareTo(email) == 0) {
                return u;
            }
        }
        return null;
    }
}
