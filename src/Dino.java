import javafx.animation.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.sql.Time;
import java.util.List;

public class Dino extends GameObject{
    private final float GRAVITY = 0.2f;
    private float velocity = -20;
    public static final int LAND_Y = 320;
    private boolean isJumping = false;
    private Rectangle rect;

    private boolean isEntering = true;
    private final double ENTER_SPEED = 5;

    public Dino(String image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }

    public void enterAnimation() {
        if (isEntering) {
            setX(getX() + ENTER_SPEED);
            if (getX() >= 60) {  // Final position
                setX(60);
                isEntering = false;
            }
        }
    }

    public boolean isEntering() {
        return isEntering;
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
        super.render(graphicsContext);
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setEntering(boolean entering) {
        this.isEntering = entering;
        if (entering) {
            setX(-70); // Reset position for entering animation
        }
    }


    public void jump() {
        if (!isJumping) {
            isJumping = true;
            velocity = -10;  // Adjust this value to modify the jump height
        }
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public double getENTER_SPEED() {
        return ENTER_SPEED;
    }

    public float getGravity() {
        return GRAVITY;
    }

}