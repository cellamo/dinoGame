import javafx.animation.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


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
    GameObject background2;

    GameObject bird;
    GameObject cactus;
    GameObject bigCactus;

    static int scoreInt = 0;
    static int levelInt = 1;
    Text scoreText;
    Text levelText;

    private boolean gameStarted = false;
    private boolean gameOver = false;
    private boolean isPeeking = true;

    // Your game parameters
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = 400;

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

        primaryStage.setResizable(false);

        primaryStage.show();
    }

    private void showStartScreen() {
        AnimationTimer startScreenTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            private double titleScale = 1.0;
            private boolean growing = true;
            private double dinoX = -70;
            private boolean peekingIn = true;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 16_666_666) {  // 60 FPS
                    graphicsContext.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                    background.render(graphicsContext);
                    platform.render(graphicsContext);

                    // Draw card background
                    double cardWidth = 400;
                    double cardHeight = 200;
                    double cardX = (GAME_WIDTH - cardWidth) / 2;
                    double cardY = (GAME_HEIGHT - cardHeight) / 2;

                    // Pulsing title animation
                    if (growing) {
                        titleScale += 0.002;
                        if (titleScale >= 1.05) growing = false;
                    } else {
                        titleScale -= 0.002;
                        if (titleScale <= 0.95) growing = true;
                    }

                    graphicsContext.setFill(Color.WHITE);
                    graphicsContext.fillRoundRect(cardX, cardY, cardWidth, cardHeight, 20, 20);

                    graphicsContext.setStroke(Color.BLACK);
                    graphicsContext.setLineWidth(3);
                    graphicsContext.strokeRoundRect(cardX, cardY, cardWidth, cardHeight, 20, 20);

                    // Pulsing title animation
                    if (growing) {
                        titleScale += 0.002;
                        if (titleScale >= 1.05) growing = false;
                    } else {
                        titleScale -= 0.002;
                        if (titleScale <= 0.95) growing = true;
                    }

                    graphicsContext.save();
                    graphicsContext.setTransform(titleScale, 0, 0, titleScale, GAME_WIDTH / 2, GAME_HEIGHT / 2 - 30);
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.setFont(Font.font("Arial", FontWeight.BOLD, 40));
                    graphicsContext.fillText("HUBBM-Dino", -120, 0);

                    graphicsContext.restore();



                    if (isPeeking) {
                        if (peekingIn) {
                            dinoX += 2;
                            if (dinoX >= 0) peekingIn = false;
                        } else {
                            dinoX -= 2;
                            if (dinoX <= -70) peekingIn = true;
                        }
                        player.setX(dinoX);
                        player.setY(Dino.LAND_Y);
                        player.render(graphicsContext);
                    }
                    graphicsContext.setFill(Color.BLACK);

                    graphicsContext.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
                    graphicsContext.fillText("Press SPACE to start", GAME_WIDTH / 2 - 100, GAME_HEIGHT / 2 + 50);

                    lastUpdate = now;
                }
            }
        };
        startScreenTimer.start();
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

        player = new Dino("res/player.png", -70, Dino.LAND_Y, 70, 70);

        // TODO: Init game objects and parameters like key event listeners, timers etc.
        obstacles = new ArrayList<>();

        background = new GameObject("res/background.png", 0, 0, 1000, 400);
        background2 = new GameObject("res/background.png", background.getWidth(), 0, 1000, 400);

        background.render(graphicsContext);

        levelText = new Text();
        levelText.setText("Level: " + levelInt);
        levelText.setX(10);
        levelText.setY(60);
        levelText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        levelText.setFill(Color.MEDIUMVIOLETRED);
        rootViewContainer.getChildren().add(levelText);

        scoreText = new Text();
        scoreText.setText("Score: " + scoreInt);
        scoreText.setX(10);
        scoreText.setY(30);
        scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        scoreText.setFill(Color.MEDIUMVIOLETRED);
        rootViewContainer.getChildren().add(scoreText);

        hideScoreAndLevel();

        platform = new GameObject("res/platform.png", 0, 0, 1000, 20);
        platform.setX(0);
        platform.setY(background.getHeight() - platform.getHeight());
        platform.render(graphicsContext);


        player = new Dino("res/player.png", 0, 0, 70, 70);
        player.setX(60);
        player.setY(Dino.LAND_Y);

        player.render(graphicsContext);

        showStartScreen();

        initKeyEventListeners();
        initTimer();

        // timer.start();
    }


    /**
     * keyPressed and keyReleased are the two main keyboard event listener objects. You can check which keyboard
     * keys are pressed or released by means of this two objects and make appropriate changes in your game.
     */
    void initKeyEventListeners() {

        keyPressed = new EventHandler<KeyEvent>() {
            private final AtomicLong lastUpPressTimestamp = new AtomicLong();

            @Override
            public void handle(KeyEvent event) {
                if (gameOver) {
                    // Only allow SPACE key when game is over
                    if (event.getCode() == KeyCode.SPACE) {
                        resetGame();
                    }
                    return; // Ignore all other key presses
                }

                switch (event.getCode()) {
                    case SPACE:
                        if (!gameStarted) {
                            gameStarted = true;
                            showScoreAndLevel();
                            isPeeking = false;
                            timer.start();
                        }
                        break;
                    case UP: // Jump
                        graphicsContext.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

                        long currentTimestamp = System.currentTimeMillis();

                        // Check if it has been more than 1 second since the last time the UP key was pressed
                        if (currentTimestamp - lastUpPressTimestamp.get() > 1000) {
                            // It has been more than 1 second, so update the timestamp and execute the code
                            lastUpPressTimestamp.set(currentTimestamp);

                            // Put the code you want to execute once per second here
                            ((Dino) player).jump();
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
                        if (player.getX() < GAME_WIDTH) {
                            player.setX(player.getX() + 10);
                        }
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
                        ((Dino) player).jump();
                        break;
                    case LEFT:
                        refresh();
                        break;
                    case RIGHT:
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
        platform.render(graphicsContext);
        player.render(graphicsContext);
        for (GameObject obstacle : obstacles) {
            obstacle.render(graphicsContext);
        }


    }

    /**
     * This timer object will call the handle() method at every frame. So, in this method's body, you can
     * redraw your objects to make a movement effect, check whether any of your objects collided or not,
     * and update your game score etc. This is the main lifecycle of your game.
     */
    void initTimer() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (obstacles.size() <= 3) {
                int random = (int) (Math.random() * 3);
                System.out.println(random);
                if (levelInt == 1) random = 1;
                switch (random) {
                    case 0:
                        obstacles.add(new Cactus("res/cactus.png", 1000, 290, 50, 100));
                        break;
                    case 1:
                        obstacles.add(new BigCactus("res/cactus_big.png", 1000, 290, 100, 100));
                        break;
                    case 2:
                        if (levelInt > 1) {
                            obstacles.add(new Bird("res/bird1.png", "res/bird2.png", 1000, 250, 50, 50));
                        }
                        break;
                }
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (gameOver) {
                    showGameOverScreen();
                    stop(); // Stop the AnimationTimer
                    return;
                }

                if (!gameStarted) {
                    gameStarted = true;
                    player.setX(-70); // Reset player position for entering animation
                }

                rootViewContainer.getChildren().remove(scoreText);
                scoreText.setText("Score: " + scoreInt);
                rootViewContainer.getChildren().add(scoreText);

                rootViewContainer.getChildren().remove(levelText);
                levelText.setText("Level: " + levelInt);
                rootViewContainer.getChildren().add(levelText);

                // Update player's position and apply gravity
                if (((Dino) player).isJumping()) {
                    // Apply gravity based on whether the dino is ascending or descending
                    if (((Dino) player).getVelocity() < 0) {
                        // Ascending: use normal gravity
                        ((Dino) player).setVelocity(((Dino) player).getVelocity() + ((Dino) player).getGravity());
                    } else {
                        // Descending: use higher gravity to increase fall speed
                        ((Dino) player).setVelocity((float) (((Dino) player).getVelocity() + ((Dino) player).getGravity() * 2.0));
                    }

                    // Update the player's Y position
                    player.setY(player.getY() + ((Dino) player).getVelocity());

                    // Check if player has landed
                    if (player.getY() >= Dino.LAND_Y) {
                        player.setY(Dino.LAND_Y);
                        ((Dino) player).setJumping(false);
                        ((Dino) player).setVelocity(0);
                    }
                }

                // Move both backgrounds to the left
                double backgroundSpeed = 0.8;
                background.setX(background.getX() - backgroundSpeed);
                background2.setX(background2.getX() - backgroundSpeed);

                // Reset background positions to create a seamless scroll
                if (background.getX() <= -background.getWidth()) {
                    background.setX(background2.getX() + background2.getWidth());
                }
                if (background2.getX() <= -background2.getWidth()) {
                    background2.setX(background.getX() + background.getWidth());
                }

                background.render(graphicsContext);
                background2.render(graphicsContext);
                platform.render(graphicsContext);

                if (((Dino) player).isEntering()) {
                    ((Dino) player).enterAnimation();
                } else {
                    Iterator<GameObject> iterator = obstacles.iterator();
                    while (iterator.hasNext()) {
                        GameObject obstacle = iterator.next();

                        // Remove obstacle if it is off the screen
                        if (obstacle.getX() + obstacle.getWidth() < 0) {
                            iterator.remove();
                            continue;
                        }

                        // Increment score when obstacle passes player for the first time
                        if (!obstacle.isPassed() && obstacle.getX() < player.getX()) {
                            obstacle.setPassed(true); // Mark obstacle as passed
                            scoreInt++;
                            if (scoreInt % 10 == 0) {
                                levelInt++;
                            }
                        }

                        // Move obstacle and render
                        obstacle.setX(obstacle.getX() - (5 + levelInt));
                        obstacle.render(graphicsContext);

                        // **Collision detection**
                        if (player.getBounds().intersects(obstacle.getBounds())) {
                            gameOver = true;
                            break; // Exit the loop if collision occurs
                        }
                    }
                }

                player.render(graphicsContext);
            }
        };
    }

    private void resetGame() {
        // Reset game variables
        gameStarted = false;
        gameOver = false;
        scoreInt = 0;
        levelInt = 1;

        // Clear obstacles
        obstacles.clear();

        // Reset player position
        player.setX(-70);
        player.setY(Dino.LAND_Y);
        ((Dino) player).setEntering(true);

        // Clear and render initial graphics
        graphicsContext.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        showStartScreen();
        showScoreAndLevel();

        // Restart the timer
        timer.start();
    }

    private void showGameOverScreen() {
        final int[] displayedScore = {0};
        final double[] dinoY = {player.getY()};
        final double fallSpeed = 5;
        final double[] backgroundSpeed = {5.0}; // Initial speed

        AnimationTimer gameOverTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 16_666_666) {  // 60 FPS
                    graphicsContext.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                    // Animate background
                    background.setX(background.getX() - backgroundSpeed[0]);
                    background2.setX(background2.getX() - backgroundSpeed[0]);

                    // Reset background positions
                    if (background.getX() <= -background.getWidth()) {
                        background.setX(background2.getX() + background2.getWidth());
                    }
                    if (background2.getX() <= -background2.getWidth()) {
                        background2.setX(background.getX() + background.getWidth());
                    }

                    background.render(graphicsContext);
                    background2.render(graphicsContext);
                    platform.render(graphicsContext);

                    // Slow down background
                    if (backgroundSpeed[0] > 0) {
                        backgroundSpeed[0] -= 0.1;
                    } else {
                        backgroundSpeed[0] = 0;
                    }

                    // Falling Dino animation
                    if (dinoY[0] < GAME_HEIGHT) {
                        dinoY[0] += fallSpeed;
                        player.setY(dinoY[0]);
                        player.render(graphicsContext);
                    }

// Draw card background
                    double cardWidth = 400;
                    double cardHeight = 250;
                    double cardX = (GAME_WIDTH - cardWidth) / 2;
                    double cardY = (GAME_HEIGHT - cardHeight) / 2;

                    graphicsContext.setFill(Color.WHITE);
                    graphicsContext.fillRoundRect(cardX, cardY, cardWidth, cardHeight, 20, 20);

                    graphicsContext.setStroke(Color.BLACK);
                    graphicsContext.setLineWidth(3);
                    graphicsContext.strokeRoundRect(cardX, cardY, cardWidth, cardHeight, 20, 20);

                    // Render text on the card
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.setFont(Font.font("Arial", FontWeight.BOLD, 50));
                    graphicsContext.fillText("Game Over", cardX + 70, cardY + 70);

                    // Animated score counter
                    if (displayedScore[0] < scoreInt) {
                        displayedScore[0] += Math.max(1, (scoreInt - displayedScore[0]) / 10);
                    }
                    graphicsContext.setFont(Font.font("Arial", FontWeight.NORMAL, 30));
                    graphicsContext.fillText("Your Score: " + displayedScore[0], cardX + 100, cardY + 130);

                    graphicsContext.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
                    graphicsContext.fillText("Press SPACE to restart", cardX + 90, cardY + 180);
                    lastUpdate = now;
                }
            }
        };
        gameOverTimer.start();
    }


    private void hideScoreAndLevel() {
        scoreText.setVisible(false);
        levelText.setVisible(false);
    }

    private void showScoreAndLevel() {
        scoreText.setVisible(true);
        levelText.setVisible(true);
    }

    private void resetDinoPosition() {
        player.setX(-70);
        ((Dino) player).setEntering(true);
    }

}