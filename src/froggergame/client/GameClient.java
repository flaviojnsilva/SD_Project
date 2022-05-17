package froggergame.client;

import froggergame.frogger.Main;
import froggergame.server.Game;
import froggergame.server.GameFactoryRI;
import froggergame.server.GameSessionRI;
import froggergame.util.rmisetup.SetupContextRMI;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2017</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui S. Moreira
 * @version 3.0
 */
public class GameClient {

    /**
     * Context for connecting a RMI froggergame.client MAIL_TO_ADDR a RMI Servant
     */
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold the Servant proxy
     */
    private GameFactoryRI gameFactoryRI;

    public static void main(String[] args) {
        if (args != null && args.length < 2) {
            System.err.println("usage: java [options] edu.ufp.sd.inf.rmi._01_helloworld.froggergame.server.HelloWorldClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            //1. ============ Setup froggergame.client RMI context ============
            GameClient hwc = new GameClient(args);
            //2. ============ Lookup service ============
            hwc.lookupService();
            //3. ============ Play with service ============
            hwc.playService();
        }
    }

    public GameClient(String args[]) {
        try {
            //List ans set args
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private Remote lookupService() {
        try {
            //Get proxy MAIL_TO_ADDR rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR lookup service @ {0}", serviceUrl);

                //============ Get proxy MAIL_TO_ADDR HelloWorld service ============
                gameFactoryRI = (GameFactoryRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return gameFactoryRI;
    }

    private void playService() {
        try {

            //LoginFrame UI = new LoginFrame();
            //UI.setTitle("Frogger Game");
            //UI.setVisible(true);
            //UI.setBounds(10, 10, 370, 600);
            //UI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //UI.setResizable(false);

            //UI.addComponentsToContainer();

            //boolean b = this.gameFactoryRI.register("ufp@ufp.pt", "123");
            //checkRegister(b);
            //boolean c = this.gameFactoryRI.register("VSVufp@ufp.pt", "1234");
            //checkRegister(c);

            //GameSessionRI gameSessionRI = this.gameFactoryRI.login("ufp@ufp.pt", "123");

//atraves do session criar jogo, visualizar jogos e permitir juntar....
//depois disto lançar jogo.
//verificar nº jogadores,  é minimo, permite mais...

            Scanner username = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Introduza o nome:");
            String userName = username.nextLine();  // Read user input
            Scanner password = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Introduza a password:");
            String passWord = password.nextLine();  // Read user input


            boolean r = this.gameFactoryRI.register(userName, passWord);
            //checkRegister(r);

            GameSessionRI gameSessionRI = this.gameFactoryRI.login(userName, passWord);

            Game a = criarjogo(gameSessionRI);

            PlayerRI observerRI = new PlayerImpl(1);

            Main f = new Main(a, observerRI);
            f.run();

        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkRegister(boolean b) {
        if (b) {
            //Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User Registered");
            System.out.println("UTILIZADOR REGISTADO.\n");
        } else {
            //Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User not Registered");
            System.out.println("UTILIZADOR NÃO REGISTADO.\n");
        }
    }

    private static Game criarjogo(GameSessionRI gameSessionRI) throws RemoteException {
        Scanner difficulty = new Scanner(System.in);
        System.out.println("Introduza a dificuldade:");
        int dif = difficulty.nextInt();
        System.out.println("Introduza o numero maximo de jogadores:");
        int max = difficulty.nextInt();

        //chama o Create Game
        PlayerRI observerRI = new PlayerImpl(1);

        Game game = gameSessionRI.createGame(1, dif, max, observerRI);

        observerRI.setFroggerGameRI(game.getFroggerGameRI());

        //while (observerRI.getFroggerGameRI().getFroggers().size() != game.getMaxPlayers()) {
        //}

        System.out.println("Jogo criado com sucesso!");

        return game;
    }

    private void print(String s) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, s);
    }

}