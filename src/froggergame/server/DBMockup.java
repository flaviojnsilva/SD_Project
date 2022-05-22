package froggergame.server;

import java.util.ArrayList;

/**
 * This class simulates a DBMockup for managing users and books.
 *
 * @author rmoreira
 */
public class DBMockup {

    private final ArrayList<User> users;
    private final ArrayList<Game> games;

    public DBMockup() {
        this.users = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    /**
     * This constructor creates and inits the database with some users.
     */

    /**
     * Registers a new user.
     *
     * @param e email
     * @param p passwd
     */
    public boolean register(String e, String p) {
        if (getUser(e) == null) {
            users.add(new User(e, p));
            return true;
        }
        return false;
    }

    public Game insert(int id, int dif, int maxPlayers, FroggerGameRI froggerGameRI) {

        Game a = new Game(id, maxPlayers, dif, froggerGameRI);

        games.add(a);

        return a;
    }

    /**
     * Checks the credentials of an user.
     *
     * @param e email
     * @param p passwd
     * @return
     */
    public boolean exists(String e, String p) {
        for (User usr : this.users) {
            if (usr.getEmail().compareTo(e) == 0 && usr.getPword().compareTo(p) == 0) {
                return true;
            }
        }
        return false;
        //return ((u.equalsIgnoreCase("guest") && p.equalsIgnoreCase("ufp")) ? true : false);
    }

    public Game select(int id) {

        for (Game game : games) {
            if (game.id == id) {
                return game;
            }
        }
        return null;
    }


    public User getUser(String username) {
        for (User u : this.users) {
            if (u.getEmail().compareTo(username) == 0) {
                return u;
            }
        }
        return null;
    }
}
