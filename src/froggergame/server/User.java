/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package froggergame.server;

/**
 * @author rmoreira
 */
public class User {
    private String email;
    private String pword;

    public User(String email, String pword) {
        this.email = email;
        this.pword = pword;
    }

    @Override
    public String toString() {
        return "User{" + "uemail" + email + ", pword=" + pword + '}';
    }

    public String getEmail() {
        return email;
    }

    /**
     * @return the pword
     */
    public String getPword() {
        return pword;
    }

}