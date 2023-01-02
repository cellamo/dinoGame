import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;


public class MyGame extends Application {
    // JavaFX related objects
    Group rootViewContainer;
    Canvas gameCanvas;
    GraphicsContext graphicsContext;
    EventHandler<KeyEvent> keyPressed;
    EventHandler<KeyEvent> keyReleased;
    AnimationTimer timer;


    // Your game objects
    GameObject player;
    ArrayList<GameObject> obstacles;
    GameObject platform;
    GameObject background;


    // Your game parameters
    final int GAME_WIDTH = 1000;
    final int GAME_HEIGHT = 400;

    /**
     * Launch a JavaFX application.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start the game scene with defined GAME_WIDTH and GAME_HEIGHT.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        rootViewContainer = new Group();
        rootViewContainer.getChildren().removeAll();
        initGame();

        Scene jScene = new Scene(rootViewContainer, GAME_WIDTH, GAME_HEIGHT);
        primaryStage.setTitle("HUBBM-Dino");
        primaryStage.setScene(jScene);

        jScene.setOnKeyPressed(keyPressed);
        jScene.setOnKeyReleased(keyReleased);




        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);
                // background image clears canvas
                player.render(graphicsContext);
            }
        }.start();

        primaryStage.show();
    }

    /**
     * Init game objects and parameters like key event listeners, timers etc.
     */
    public void initGame() {
        // Generate a game canvas where all your objects, texts etc. will be drawn.
        rootViewContainer.getChildren().clear();
        gameCanvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        graphicsContext = gameCanvas.getGraphicsContext2D();
        rootViewContainer.getChildren().add(gameCanvas);


        // TODO: Init game objects and parameters like key event listeners, timers etc.

        background = new GameObject("res/background.png", 0, 0, 1000, 400);
        background.render(graphicsContext);

        platform = new GameObject("res/platform.png", 0, 0, 1000, 50);
        platform.setX(0);
        platform.setY(350);
        platform.render(graphicsContext);

        Dino dino = new Dino("res/player.png", 0, 0, 100, 100);
        dino.setX(100);
        dino.setY(300);
        dino.render(graphicsContext);
        player = dino;





        initKeyEventListeners();
        initTimer();

        timer.start();
    }


    /**
     * keyPressed and keyReleased are the two main keyboard event listener objects. You can check which keyboard
     * keys are pressed or released by means of this two objects and make appropriate changes in your game.
     */
    void initKeyEventListeners() {
        keyPressed = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        graphicsContext.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                        //limit how much the player can jump
                        if (player.getY() > 0) {
                            player.setY(player.getY() - 10);
                        }
                        refresh();
                        break;
                    case DOWN:
                        graphicsContext.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                        if (player.getY() < GAME_HEIGHT - player.getHeight()) {
                            player.setY(player.getY() + 10);
                        }
                        refresh();
                        break;
                    case LEFT:
                        graphicsContext.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                        if (player.getX() > 0) {
                            player.setX(player.getX() - 10);
                        }
                        refresh();
                        break;
                    case RIGHT:
                        graphicsContext.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                        if (player.getX() < GAME_WIDTH - player.getWidth()) {
                            player.setX(player.getX() + 10);
                        }
                        refresh();
                        break;
                    case SPACE:
                        graphicsContext.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                        // TODO: make the player jump
                        refresh();
                        break;

                    // Detect the key via its code like LEFT, RIGHT, UP, DOWN, ENTER etc.
                }
            }
        };

        keyReleased = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        refresh();
                        break;
                    case DOWN:
                        refresh();
                        break;
                    case LEFT:
                        refresh();
                        break;
                    case RIGHT:
                        refresh();
                        break;
                    case SPACE:
                        refresh();
                        break;
                    // Detect the key via its code like LEFT, RIGHT, UP, DOWN, ENTER etc.
                }
            }
        };

        gameCanvas.setOnKeyPressed(keyPressed);
        gameCanvas.setOnKeyReleased(keyReleased);
    }

    private void refresh() {
        graphicsContext.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        background.render(graphicsContext);
        player.render(graphicsContext);
        platform.render(graphicsContext);
    }

    /**
     * This timer object will call the handle() method at every frame. So, in this method's body, you can
     * redraw your objects to make a movement effect, check whether any of your objects collided or not,
     * and update your game score etc. This is the main lifecycle of your game.
     */
    void initTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                /* In this method's body, you can  redraw your objects to make a movement effect,
                   check whether any of your objects collided or not,
                   and update your game score etc. This is the main lifecycle of your game.*/
            }
        };
    }

}
