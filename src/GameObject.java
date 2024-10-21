import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * With this object, you can store information of position like x, y, width, height of your object.
 * Also you can store image of your object and render it.
 */
public class GameObject {
    private Image image;
    // Define your own properties like x, y, width, height etc.
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean passed = false;

    public GameObject(String image, double x, double y, double width, double height) {
        this.image = new Image(image);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(GraphicsContext graphicsContext) {
        // Hint: You can take a GraphicsContext object as a parameter and call drawImage() method to draw an image on your game canvas.
        graphicsContext.drawImage(image, x, y, width, height);
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
