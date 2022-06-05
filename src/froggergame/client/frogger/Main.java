/**
 * Copyright (c) 2009 Vitaliy Pavlenko
 * <p>
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * <p>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package froggergame.client.frogger;

import froggergame.client.PlayerRI;
import froggergame.server.FroggerGameRI;
import froggergame.server.Game;
import froggergame.server.State;
import jig.engine.ImageResource;
import jig.engine.PaintableCanvas;
import jig.engine.PaintableCanvas.JIGSHAPE;
import jig.engine.RenderingContext;
import jig.engine.ResourceFactory;
import jig.engine.hli.ImageBackgroundLayer;
import jig.engine.hli.StaticScreenGame;
import jig.engine.physics.AbstractBodyLayer;
import jig.engine.util.Vector2D;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Main extends StaticScreenGame {
    static final int WORLD_WIDTH = (13 * 32);
    static final int WORLD_HEIGHT = (14 * 32);
    static final Vector2D FROGGER_START = new Vector2D(6 * 32, WORLD_HEIGHT - 32);

    static final String RSC_PATH = "froggergame/resources/";
    static String SPRITE_SHEET = RSC_PATH + "frogger_sprites1.png";

    static final int STARTING_LEVEL = 1;
    static final int DEFAULT_LEVEL_TIME = 60;

    private ArrayList<FroggerCollisionDetection> frogCol = new ArrayList<>();
    private ArrayList<Frogger> frogs;
    private PlayerRI PlayerRI;
    private AudioEfx audiofx;
    private FroggerUI ui;
    private WindGust wind;
    private HeatWave hwave;
    private GoalManager goalmanager;

    private AbstractBodyLayer<MovingEntity> movingObjectsLayer;
    private AbstractBodyLayer<MovingEntity> particleLayer;

    private MovingEntityFactory roadLine1;
    private MovingEntityFactory roadLine2;
    private MovingEntityFactory roadLine3;
    private MovingEntityFactory roadLine4;
    private MovingEntityFactory roadLine5;

    private MovingEntityFactory riverLine1;
    private MovingEntityFactory riverLine2;
    private MovingEntityFactory riverLine3;
    private MovingEntityFactory riverLine4;
    private MovingEntityFactory riverLine5;

    private ImageBackgroundLayer backgroundLayer;

    static final int GAME_INTRO = 0;
    static final int GAME_PLAY = 1;
    static final int GAME_FINISH_LEVEL = 2;
    static final int GAME_INSTRUCTIONS = 3;
    static final int GAME_OVER = 4;

    protected int GameState = GAME_PLAY;
    protected int GameLevel;

    public int GameLives;
    public int GameScore = 0;

    public int levelTimer = DEFAULT_LEVEL_TIME;

    private boolean space_has_been_released = false;
    private boolean keyPressed = false;
    private boolean listenInput = true;

    private FroggerGameRI froggerGameRI;
    private ArrayList<Vector2D> frogsPosition = new ArrayList<Vector2D>();
    private RenderingContext rc2;
    public int nfrogs = 0;


    /**
     * Initialize game objects
     */
    public Main(Game game, PlayerRI PlayerRI) throws RemoteException {

        super(WORLD_WIDTH, WORLD_HEIGHT, false);
        this.frogs = new ArrayList<Frogger>();
        PlayerRI.setMain(this);
        this.PlayerRI = PlayerRI;
        this.GameLevel = game.getDifficulty();
        this.froggerGameRI = game.getFroggerRI();

        gameframe.setTitle("Frogger " + "Jogador: " + PlayerRI.getId());
        ResourceFactory.getFactory().loadResources(RSC_PATH, "resources.xml");
        ImageResource bkg = ResourceFactory.getFactory().getFrames(
                SPRITE_SHEET + "#background").get(0);
        backgroundLayer = new ImageBackgroundLayer(bkg, WORLD_WIDTH,
                WORLD_HEIGHT, ImageBackgroundLayer.TILE_IMAGE);

        // Used in CollisionObject, basically 2 different collision spheres
        // 30x30 is a large sphere (sphere that fits inside a 30x30 pixel rectangle)
        //  4x4 is a tiny sphere
        PaintableCanvas.loadDefaultFrames("col", 30, 30, 2, JIGSHAPE.RECTANGLE, null);
        PaintableCanvas.loadDefaultFrames("colSmall", 4, 4, 2, JIGSHAPE.RECTANGLE, null);

        createFrogs(game);
        for (Frogger frog : this.frogs) {
            frogCol.add(new FroggerCollisionDetection(frog));
        }

        ui = new FroggerUI(this);
        wind = new WindGust();
        hwave = new HeatWave();
        goalmanager = new GoalManager();

        movingObjectsLayer = new AbstractBodyLayer.IterativeUpdate<MovingEntity>();
        particleLayer = new AbstractBodyLayer.IterativeUpdate<MovingEntity>();

        initializeLevel(game.getDifficulty());
    }

    public void createFrogs(Game game) throws RemoteException {
        nfrogs = game.getFroggerRI().getFroggers().size();
        System.out.println("Numero Froggers: " + nfrogs);

        for (int i = 0; i < nfrogs; i++) {
            frogsPosition.add(new Vector2D((6 * 32) + i * 50, WORLD_HEIGHT - 32));
        }
        for (int j = 1; j <= nfrogs; j++) {
            SPRITE_SHEET = RSC_PATH + "frogger_sprites" + j + ".png";

            game.getFroggerRI().getFroggers().get(j - 1).setId(j - 1);

            Frogger frog = new Frogger(this, PlayerRI, frogsPosition.get(j - 1));
            frogs.add(frog);

        }

        GameLives = frogs.get(PlayerRI.getId()).froggerLives;

        int k = 0;
        for (Frogger frogger : frogs) {
            frogger.setPosition(frogsPosition.get(k));
            k++;
        }
    }


    public void initializeLevel(int level) {

        /* dV is the velocity multiplier for all moving objects at the current game level */
        double dV = level * 0.05 + 1;

        movingObjectsLayer.clear();

        /* River Traffic */
        riverLine1 = new MovingEntityFactory(new Vector2D(-(32 * 3), 2 * 32),
                new Vector2D(0.06 * dV, 0));

        riverLine2 = new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH, 3 * 32),
                new Vector2D(-0.04 * dV, 0));

        riverLine3 = new MovingEntityFactory(new Vector2D(-(32 * 3), 4 * 32),
                new Vector2D(0.09 * dV, 0));

        riverLine4 = new MovingEntityFactory(new Vector2D(-(32 * 4), 5 * 32),
                new Vector2D(0.045 * dV, 0));

        riverLine5 = new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH, 6 * 32),
                new Vector2D(-0.045 * dV, 0));

        /* Road Traffic */
        roadLine1 = new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH, 8 * 32),
                new Vector2D(-0.1 * dV, 0));

        roadLine2 = new MovingEntityFactory(new Vector2D(-(32 * 4), 9 * 32),
                new Vector2D(0.08 * dV, 0));

        roadLine3 = new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH, 10 * 32),
                new Vector2D(-0.12 * dV, 0));

        roadLine4 = new MovingEntityFactory(new Vector2D(-(32 * 4), 11 * 32),
                new Vector2D(0.075 * dV, 0));

        roadLine5 = new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH, 12 * 32),
                new Vector2D(-0.05 * dV, 0));

        goalmanager.init(level);
        for (Goal g : goalmanager.get()) {
            movingObjectsLayer.add(g);
        }

        /* Build some traffic before game starts buy running MovingEntityFactories for fews cycles */
        for (int i = 0; i < 500; i++)
            cycleTraffic(10);
    }


    /**
     * Populate movingObjectLayer with a cycle of cars/trucks, moving tree logs, etc
     *
     * @param deltaMs
     */
    public void cycleTraffic(long deltaMs) {
        MovingEntity m;
        /* Road traffic updates */
        roadLine1.update(deltaMs);
        if ((m = roadLine1.buildVehicle(40)) != null) movingObjectsLayer.add(m);

        roadLine2.update(deltaMs);
        if ((m = roadLine2.buildVehicle(30)) != null) movingObjectsLayer.add(m);

        roadLine3.update(deltaMs);
        if ((m = roadLine3.buildVehicle(20)) != null) movingObjectsLayer.add(m);

        roadLine4.update(deltaMs);
        if ((m = roadLine4.buildVehicle(50)) != null) movingObjectsLayer.add(m);

        roadLine5.update(deltaMs);
        if ((m = roadLine5.buildVehicle(10)) != null) movingObjectsLayer.add(m);

        /* River traffic updates */
        riverLine1.update(deltaMs);
        if ((m = riverLine1.buildShortLogWithTurtles(40)) != null) movingObjectsLayer.add(m);

        riverLine2.update(deltaMs);
        if ((m = riverLine2.buildLongLogWithCrocodile(30)) != null) movingObjectsLayer.add(m);

        riverLine3.update(deltaMs);
        if ((m = riverLine3.buildShortLogWithTurtles(50)) != null) movingObjectsLayer.add(m);

        riverLine4.update(deltaMs);
        if ((m = riverLine4.buildLongLogWithCrocodile(20)) != null) movingObjectsLayer.add(m);

        riverLine5.update(deltaMs);
        if ((m = riverLine5.buildShortLogWithTurtles(10)) != null) movingObjectsLayer.add(m);

        // Do Wind
        if ((m = wind.genParticles(GameLevel)) != null) particleLayer.add(m);

        // HeatWave
        for (Frogger frog : frogs)
            if ((m = hwave.genParticles(frog.getCenterPosition())) != null) particleLayer.add(m);

        movingObjectsLayer.update(deltaMs);
        particleLayer.update(deltaMs);
    }

    /**
     * Handling Frogger movement from keyboard input
     */
    public void froggerKeyboardHandler() throws RemoteException {
        keyboard.poll();

        boolean keyReleased = false;
        boolean downPressed = keyboard.isPressed(KeyEvent.VK_DOWN);
        boolean upPressed = keyboard.isPressed(KeyEvent.VK_UP);
        boolean leftPressed = keyboard.isPressed(KeyEvent.VK_LEFT);
        boolean rightPressed = keyboard.isPressed(KeyEvent.VK_RIGHT);

        // Enable/Disable cheating
        for (Frogger frog : frogs) {
            if (keyboard.isPressed(KeyEvent.VK_C))
                frog.cheating = true;
            if (keyboard.isPressed(KeyEvent.VK_V))
                frog.cheating = false;
            if (keyboard.isPressed(KeyEvent.VK_0)) {
                initializeLevel(GameLevel);
            }
        }

        /*
         * This logic checks for key strokes.
         * It registers a key press, and ignores all other key strokes
         * until the first key has been released
         */
        if (downPressed || upPressed || leftPressed || rightPressed)
            keyPressed = true;
        else if (keyPressed)
            keyReleased = true;

        if (listenInput) {
            if (downPressed) {
                froggerGameRI.setFroggerGameState(new State(PlayerRI.getId(), "Down Pressed"));
            }
            if (upPressed) {
                froggerGameRI.setFroggerGameState(new State(PlayerRI.getId(), "Up Pressed"));
            }
            if (leftPressed) {
                froggerGameRI.setFroggerGameState(new State(PlayerRI.getId(), "Left Pressed"));
            }
            if (rightPressed) {
                froggerGameRI.setFroggerGameState(new State(PlayerRI.getId(), "Right Pressed"));
            }

            if (keyPressed)
                listenInput = false;
        }
        if (keyReleased) {
            listenInput = true;
            keyPressed = false;
        }

        if (keyboard.isPressed(KeyEvent.VK_ESCAPE))
            GameState = GAME_INTRO;
    }

    public void receiveState(State state) throws RemoteException {
        System.out.println("estado " + state.getMsg());
        switch (state.getMsg()) {
            case "Down Pressed": {
                frogs.get(state.getId()).moveDown();
                break;
            }
            case "Up Pressed": {
                frogs.get(state.getId()).moveUp();
                break;
            }
            case "Left Pressed": {
                frogs.get(state.getId()).moveLeft();
                break;
            }
            case "Right Pressed": {
                frogs.get(state.getId()).moveRight();
                break;
            }
        }
    }

    /**
     * Handle keyboard events while at the game intro menu
     */
    public void menuKeyboardHandler() {
        keyboard.poll();

        // Following 2 if statements allow capture space bar key strokes
        if (!keyboard.isPressed(KeyEvent.VK_SPACE)) {
            space_has_been_released = true;
        }

        if (!space_has_been_released)
            return;

        if (keyboard.isPressed(KeyEvent.VK_SPACE)) {
            switch (GameState) {
                case GAME_INSTRUCTIONS:
                case GAME_OVER:
                    GameState = GAME_INTRO;

                    space_has_been_released = false;
                    break;
                default:
                    GameScore = 0;
                    levelTimer = DEFAULT_LEVEL_TIME;
                    GameState = GAME_PLAY;
                    initializeLevel(GameLevel);
            }
        }
        if (keyboard.isPressed(KeyEvent.VK_H))
            GameState = GAME_INSTRUCTIONS;
    }

    /**
     * Handle keyboard when finished a level
     */
    public void finishLevelKeyboardHandler() {
        keyboard.poll();
        if (keyboard.isPressed(KeyEvent.VK_SPACE)) {
            GameState = GAME_PLAY;
            audiofx.playGameMusic();
            initializeLevel(++GameLevel);
        }
    }

    /**
     *
     */
    public void update(long deltaMs) {
        switch (GameState) {
            case GAME_PLAY:
                for (Frogger frog : frogs) {
                    try {
                        if (frog.getPlayerRI().getId() == PlayerRI.getId()) {
                            frog.update(deltaMs);
                        }

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    froggerKeyboardHandler();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                wind.update(deltaMs);
                hwave.update(deltaMs);
                try {
                    frogs.get(PlayerRI.getId()).update(deltaMs);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                ui.update(deltaMs);
                for (FroggerCollisionDetection frogC : frogCol) {
                    cycleTraffic(deltaMs);
                    try {
                        frogC.testCollision(movingObjectsLayer);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    if (frogC.isInRiver())
                        wind.start(GameLevel);
                    try {
                        wind.perform(frogs.get(PlayerRI.getId()), GameLevel, deltaMs);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    // Do the heat wave only when Frogger is on hot pavement
                    if (frogC.isOnRoad()) {
                        try {
                            hwave.start(frogs.get(PlayerRI.getId()), GameLevel);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        hwave.perform(frogs.get(PlayerRI.getId()), deltaMs, GameLevel);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    if (!frogs.get(PlayerRI.getId()).isAlive)
                        particleLayer.clear();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                goalmanager.update(deltaMs);

                if (goalmanager.getUnreached().size() == 0) {
                    GameState = GAME_FINISH_LEVEL;
                    audiofx.playCompleteLevel();
                    particleLayer.clear();
                }

                try {
                    if (frogs.get(PlayerRI.getId()).froggerLives < 1) {
                        GameState = GAME_OVER;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case GAME_OVER:
                GameState = GAME_OVER;

                space_has_been_released = false;
            case GAME_INSTRUCTIONS:
            case GAME_INTRO:
                goalmanager.update(deltaMs);
                menuKeyboardHandler();
                cycleTraffic(deltaMs);
                break;

            case GAME_FINISH_LEVEL:
                finishLevelKeyboardHandler();
                break;
        }
    }


    /**
     * Rendering game objects
     */
    public void render(RenderingContext rc) {
        switch (GameState) {
            case GAME_FINISH_LEVEL:
            case GAME_PLAY:
                backgroundLayer.render(rc);
                for (Frogger frog : frogs) {
                    if (frog.isAlive) {
                        movingObjectsLayer.render(rc);
                        frog.render(rc);
                    } else {
                        frog.render(rc);
                        movingObjectsLayer.render(rc);
                    }
                }
                particleLayer.render(rc);
                ui.render(rc);
                break;

            case GAME_OVER:
            case GAME_INSTRUCTIONS:
            case GAME_INTRO:
                backgroundLayer.render(rc);
                movingObjectsLayer.render(rc);
                ui.render(rc);
                break;
        }
    }

    public ArrayList<Frogger> getFrogs() {
        return frogs;
    }

    public PlayerRI getPlayerRI() {
        return PlayerRI;
    }
}