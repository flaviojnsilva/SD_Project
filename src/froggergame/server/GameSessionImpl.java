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

public class GameSessionImpl implements GameSessionRI {

    private GameFactoryImpl gameFactoryImpl;
    private String stringToken;
    private String email;

    @Override
    public void setStringToken(String stringToken) throws RemoteException {
        this.stringToken = stringToken;
    }

    @Override
    public void setEmail(String email) throws RemoteException {
        this.email = email;
    }


    public GameSessionImpl(GameFactoryImpl gameFactory, String email) throws RemoteException {
        super();
        this.gameFactoryImpl = gameFactory;
        this.email = email;
        UnicastRemoteObject.exportObject(this, 0);
    }

    //create game
    @Override
    public Game createGame(int id, int diff, int maxPlayers, PlayerRI playerRI, String token) throws RemoteException {

        DecodedJWT decodedJWT = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(email)
                    .build(); //Reusable verifier instance
            decodedJWT = verifier.verify(token);
            System.out.println(decodedJWT.getToken());
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
        }

        assert decodedJWT != null;
        if (decodedJWT.getToken().equals(token)) {

            FroggerGameRI froggerGameRI = new FroggerGameImpl();

            Game game = gameFactoryImpl.getDbMockup().insert(id, diff, maxPlayers, froggerGameRI);
            game.getFroggerGameRI().addPlayer(playerRI);

            return game;
        }
        return null;
    }

    public Game chooseGame(int jogo, PlayerRI playerRI) throws RemoteException {

        Game game = gameFactoryImpl.getDbMockup().select(jogo);

        this.join(jogo, playerRI);
        return game;
    }


    public Game join(int id, PlayerRI playerRI) throws RemoteException {

        Game game = gameFactoryImpl.getDbMockup().select(id);
        game.getFroggerGameRI().addPlayer(playerRI);

        return game;
    }

    @Override
    public ArrayList<Game> listGame() throws RemoteException {

        return this.gameFactoryImpl.getDbMockup().printGames();
    }

}