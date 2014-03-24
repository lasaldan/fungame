import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lasaldan on 3/24/14.
 */
public class GameFont {

    private Graphics2D g;
    private Font font;

    public GameFont(String path, Canvas _canvas) {

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g = (Graphics2D) _canvas.getBufferStrategy().getDrawGraphics();
        g.setRenderingHints(rh);

        try {
            InputStream is = new FileInputStream(path);
            font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot Find Font File");
        }
        catch (FontFormatException e) {
            System.out.println("Not a Valid Font File path");
        }
        catch (IOException e) {
            System.out.println("Cannot access/read font file");
        }

        font = font.deriveFont(java.awt.Font.PLAIN);
        g.setFont(font);
    }

    public void drawString(String s, int x, int y, Color col, float size) {
        font = font.deriveFont(size);
        g.setColor(col);
        g.drawString(s, x, y);
    }

    public void drawString(String s, int x, int y, Color col) {
        g.setColor(col);
        g.drawString(s, x, y);
    }

    public void drawString(String s, int x, int y) {
        g.drawString(s, x, y);
    }

    public void setColor(int _r, int _g, int _b) {
        g.setColor(new Color(_r, _g, _b));
        g.setFont(font);
    }

    public void setSize(float _size) {
        font = font.deriveFont(_size);
        g.setFont(font);
    }

    public void setWeight(int _weight) {
        font = font.deriveFont(_weight);
        g.setFont(font);
    }
}
