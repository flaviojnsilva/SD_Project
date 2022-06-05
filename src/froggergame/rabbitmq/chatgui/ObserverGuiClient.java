/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2011</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui Moreira
 * @version 2.0
 */
package froggergame.rabbitmq.chatgui;

import com.rabbitmq.client.BuiltinExchangeType;
import froggergame.rabbitmq.frogger.Main;
import froggergame.rabbitmq.util.RabbitUtils;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author rjm
 */
public class ObserverGuiClient extends JFrame {
    private Observer observer;

    public Main main;

    /**
     * Creates new form ChatClientFrame
     *
     * @param args
     */
    public ObserverGuiClient(String args[]) {
        try {

            RabbitUtils.printArgs(args);

            //Read args passed via shell command
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            String exchangeName = args[2];
            //String room=args[3];
            String user = args[3];
            //String general=args[5];

            //2. Create the _05_observer object that manages send/receive of messages to/from rabbitmq
            this.observer = new Observer(this, host, port, "guest", "guest", exchangeName, BuiltinExchangeType.FANOUT, "UTF-8");
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " After initObserver()...");

            this.main = new Main(this.observer);
            main.run();

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    //================================================ BEGIN TO CHANGE ================================================

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new ObserverGuiClient(args);
    }
}
