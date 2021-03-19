import javax.swing.*;
import java.awt.*;

/**
 * This is a class
 * Created 2021-03-16
 *
 * @author Magnus Silverdal
 */
public class GrafikSimpel extends Canvas {
    private int width = 800;
    private int height = 600;

    private int houseX, houseY;
    private int houseVX, houseVY;

    public GrafikSimpel() {
        JFrame frame = new JFrame("A simple painting");
        this.setSize(width,height);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        houseX = 300;
        houseY = 150;
        houseVX = 1;
        houseVY = 0;
    }

    public void update() {
        houseX += houseVX;
        if (houseX > width){
            houseVX = -1;
        }
        if (houseX < 0 ) {
            houseVX = 1;
        }
    }

    public void paint(Graphics g) {
        update();
        drawHouse(g, houseX,houseY);
        drawTree(g, 100,200);
        drawTree(g, 110,200);
        drawTree(g, 120,200);
        drawTree(g, 130,200);
        drawTree(g, 140,200);
        drawTree(g, 150,200);
        repaint();
    }

    private void drawTree(Graphics g, int x, int y) {
        g.setColor(new Color(0,128,0));
        int[] xs = {0+x, 10+x, 20+x};
        int[] ys = {30+y,0+y,30+y};
        g.fillPolygon(xs,ys,3);
        g.setColor(new Color(200,128,30));
        g.fillRect(7+x,30+y,6,10);
    }

    private void drawHouse(Graphics g, int x, int y) {
        g.setColor(new Color(0xAA1111));
        g.fillRect(x, y-40, 50, 40);
        g.setColor(new Color(0x444444));
        int[] xcoords = {x-5, x + 25, x + 55};
        int[] ycoords = {y-40, y - 65, y-40};
        g.fillPolygon(xcoords, ycoords, 3);
        g.fillRect(x+4,y-35,15,35);
        g.drawRect(x+25,y-30,20,20);
        g.setColor(new Color(0xFFA3DCFA));
        g.fillRect(x+26,y-29,18,18);
    }

    public static void main(String[] args) {
        GrafikSimpel painting = new GrafikSimpel();
    }

}
