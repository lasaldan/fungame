import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ecmraven on 3/19/14.
 */
public class ScrollingBackground2d {

    List<Layer> layers;
    double offsetX;
    double offsetY;

    public ScrollingBackground2d() {
        layers = new ArrayList<Layer>();
    }

    public void addLayer(String path, double initialX, double initialY, double ratio)  {

        Image tempImage;
        try {
            tempImage = ImageIO.read(new File(path));
            layers.add(new Layer(tempImage, initialX, initialY, ratio));
        } catch (IOException e) {
            System.out.println("Error Loading Image (" + path + ")");
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g, Canvas canvas) {
        for(Layer l : layers)
            g.drawImage(l.image, (int) l.x, (int) l.y, canvas);
    }

    public void update(double dx, double dy) {
        //offsetX += dx;
        //offsetY += dy;

        for(Layer l : layers ) {
            l.shift(dx, dy);
        }
    }

    protected class Layer {

        private Image image;
        private double slideRatio;
        private double x;
        private double y;

        public Layer(Image _image, double _x, double _y, double _ratio) {
            image = _image;
            slideRatio = _ratio;
            x = _x;
            y = _y;
        }

        public void shift(double dx, double dy){
            x += slideRatio*dx;
            y += slideRatio*dy;
        }
    }
}
