package froggergame.client;

import froggergame.frogger.Main;
import froggergame.server.Game;
import froggergame.server.GameFactoryRI;
import froggergame.server.GameSessionRI;
import froggergame.util.rmisetup.SetupContextRMI;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
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
public class GameClient implements Serializable {

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

            //visualizar jogos e permitir juntar....
//verificar nº jogadores,  é minimo, permite mais...

            menu();

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
        PlayerRI playerRI = new PlayerImpl(1);

        Game game = gameSessionRI.createGame(1, dif, max, playerRI);

        playerRI.setFroggerGameRI(game.getFroggerGameRI());

        Main f = new Main(game, playerRI);
        f.run();
        System.out.println("Jogo criado com sucesso!");

        return game;
    }

    private static void escolherjogo(GameSessionRI gameSessionRI) throws RemoteException {
        System.out.println("Lista dos jogos disponiveis:");
        assert gameSessionRI != null;
        gameSessionRI.listGame();
        Scanner escolherJogo = new Scanner(System.in);
        System.out.println("Escolha um jogo para se juntar:");
        int jogo = escolherJogo.nextInt();
        PlayerRI playerRI = new PlayerImpl(1);
        Game game = gameSessionRI.chooseGame(jogo, playerRI);
        playerRI.setFroggerGameRI(game.getFroggerGameRI());

        Main f = new Main(game, playerRI);
        f.run();
    }

    private void print(String s) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, s);
    }

    private void menu() throws RemoteException {
        Scanner menu = new Scanner(System.in);

        System.out.print("##--Frogger Game--##\n\n");
        System.out.print("|---------------------------|\n");
        System.out.print("| Opção 1 - Registrar-me    |\n");
        System.out.print("| Opção 2 - Novo Jogo       |\n");
        System.out.print("| Opção 3 - Juntar-me a jogo|\n");
        System.out.print("| Opção 4 - Sair            |\n");
        System.out.print("|---------------------------|\n");
        System.out.print("Digite uma opção: ");

        int option = menu.nextInt();

        switch (option) {

            case 1:
                System.out.print("\nRegisto\n");
                Scanner email = new Scanner(System.in);  // Create a Scanner object
                System.out.println("Introduza o nome:");
                String eMail = email.nextLine();  // Read user input
                Scanner password = new Scanner(System.in);  // Create a Scanner object
                System.out.println("Introduza a password:");
                String passWord = password.nextLine();  // Read user input

                boolean r = this.gameFactoryRI.register(eMail, passWord);

                menu();

                break;

            case 2:

                System.out.print("\nNovo Jogo");
                System.out.print("\nFaça Login\n");
                Scanner username2 = new Scanner(System.in);  // Create a Scanner object
                System.out.println("Introduza o nome:");
                String userName2 = username2.nextLine();  // Read user input
                Scanner password2 = new Scanner(System.in);  // Create a Scanner object
                System.out.println("Introduza a password:");
                String passWord2 = password2.nextLine();  // Read user input
                GameSessionRI gameSessionRI = this.gameFactoryRI.login(userName2, passWord2);

                if (gameSessionRI == null) {
                    System.out.println("Nao está registado\n Efetue primeiro o registo");
                    menu();
                } else {
                    PlayerRI observerRI = new PlayerImpl(1);
                    Game game = criarjogo(gameSessionRI);
                    menu();
                }
                break;
            case 3:
                System.out.print("\nJuntar-me a Jogo\n");
                System.out.print("\nFaça Login\n");
                Scanner username3 = new Scanner(System.in);
                System.out.println("Introduza o nome:");
                String userName3 = username3.nextLine();
                Scanner password3 = new Scanner(System.in);
                System.out.println("Introduza a password:");
                String passWord3 = password3.nextLine();
                GameSessionRI gameSessionRI2 = this.gameFactoryRI.login(userName3, passWord3);

                if (gameSessionRI2 == null) {
                    System.out.println("Nao está registado\nEfetue primeiro o registo");
                    menu();
                } else {

                    if (gameSessionRI2.listGame() == null) {

                        System.out.println("Nao exitem jogos disponiveis.\nCrie um novo jogo\n");
                        menu();
                    } else {
                        gameSessionRI2.listGame();
                        menu();
                    }
                }

                break;

            default:
                System.out.print("\nOpção Inválida!");
                break;

            case 4:
                System.out.print("\nAté Breve.");
                menu.close();
        }
    }
}