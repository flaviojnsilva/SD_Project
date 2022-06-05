package froggergame.server;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import froggergame.client.PlayerRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {
    GameFactoryImpl gameFactoryImpl;
    private String email;
    String token;

    protected GameSessionImpl(GameFactoryImpl gameFactory) throws RemoteException {
        super();
        this.gameFactoryImpl = gameFactory;

    }

    /**
     * Criar um jogo
     *
     * @param id
     * @param difficulty
     * @param maxPlayers
     * @param PlayerRI
     * @param token
     * @return
     * @throws RemoteException
     */
    @Override
    public Game createGame(int id, int difficulty, int maxPlayers, PlayerRI PlayerRI, String token) throws RemoteException {

        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(email)
                    .build(); //Reusable verifier instance
            jwt = verifier.verify(token);
            System.out.println(jwt.getToken());
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
        }

        assert jwt != null;
        if (jwt.getToken().equals(token)) {

            FroggerGameRI froggerGameRI = new FroggerGameImpl();
            Game game = gameFactoryImpl.dbMockup.insert(difficulty, maxPlayers, froggerGameRI);

            game.getFroggerRI().add(PlayerRI);

            System.out.println("Jogo criado com sucesso!");
            return game;
        } else {
            System.out.println("Token errado!");
            return null;
        }
    }

    /**
     * Escolher jogo a juntar-se
     *
     * @param idG
     * @param PlayerRI
     * @param token
     * @return
     * @throws RemoteException
     */
    @Override
    public Game chooseGame(int idG, PlayerRI PlayerRI, String token) throws RemoteException {

        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(email)
                    .build(); //Reusable verifier instance
            jwt = verifier.verify(token);
            System.out.println(jwt.getToken());
        } catch (JWTVerificationException exception) {
        }

        assert jwt != null;
        if (jwt.getToken().equals(token)) {
            Game game = gameFactoryImpl.dbMockup.select(idG);
            game.getFroggerRI().add(PlayerRI);
            return game;
        } else {
            System.out.println("Token errado!");
            return null;
        }
    }

    /**
     *listar os jogos
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Game> listFroggerGames() throws RemoteException {
        return this.gameFactoryImpl.dbMockup.printGames();
    }

    @Override
    public void setToken(String token) throws RemoteException {
        this.token = token;
    }

    public void setEmail(String email) throws RemoteException {
        this.email = email;
    }
}
