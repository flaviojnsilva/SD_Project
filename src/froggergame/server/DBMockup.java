package froggergame.server;

import java.util.ArrayList;

/**
 * This class simulates a DBMockup for managing users and books.
 *
 * @author rmoreira
 */
public class DBMockup {

    private final ArrayList<User> users;// = new ArrayList();

    public DBMockup() {
        this.users = new ArrayList<>();
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

    public User getUser(String username) {
        for (User u : this.users) {
            if (u.getEmail().compareTo(username) == 0) {
                return u;
            }
        }
        return null;
    }
}
