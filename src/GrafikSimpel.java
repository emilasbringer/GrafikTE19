import javax.swing.*;
import java.awt.*;

/**
 * This is a class
 * Created 2021-03-16
 *
 * @author Magnus Silverdal
 */
public class GrafikSimpel extends Canvas {
    public GrafikSimpel() {
        JFrame frame = new JFrame("A simple painting");
        this.setSize(800,600);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(200,200,80,20);
        g.setColor(new Color(255,127,0));
        g.drawOval(200,200,80,20);
        drawTree(g, 100,200);
        drawTree(g, 110,200);
        drawTree(g, 120,200);
        drawTree(g, 130,200);
        drawTree(g, 140,200);
        drawTree(g, 150,200);
    }

    private void drawTree(Graphics g, int x, int y) {
        g.setColor(new Color(0,128,0));
        int[] xs = {0+x, 10+x, 20+x};
        int[] ys = {30+y,0+y,30+y};
        g.fillPolygon(xs,ys,3);
        g.setColor(new Color(200,128,30));
        g.fillRect(7+x,30+y,6,10);
    }

    public static void main(String[] args) {
        GrafikSimpel painting = new GrafikSimpel();
    }

}
