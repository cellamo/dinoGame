import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bird extends GameObject {
    private  Image[] frames;
    private int currentFrameIndex = 0;
    private int frameDelay = 30;
    private int frameDelayCounter = 0;

    public Bird(String image1, String image2, double x, double y, double width, double height) {
        super(image1, x, y, width, height);
        frames = new Image[2];
        frames[0] = new Image(image1);
        frames[1] = new Image(image2);
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
        // current frame of the bird
        graphicsContext.drawImage(frames[currentFrameIndex], getX(), getY(), getWidth(), getHeight());

        frameDelayCounter++;
        if (frameDelayCounter >= frameDelay) {
            frameDelayCounter = 0;
            currentFrameIndex = (currentFrameIndex + 1) % frames.length;
        }
    }
}
