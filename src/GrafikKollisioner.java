import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This is a class
 * Created 2021-03-23
 *
 * @author Magnus Silverdal
 */
public class GrafikKollisioner extends Canvas implements Runnable{
    private int width = 400;
    private int height = 300;

    private Thread thread;
    int fps = 30;
    private boolean isRunning;

    private BufferStrategy bs;

    private Rectangle house;
    private int houseVX, houseVY;

    private Rectangle tree;
    private int treeVX, treeVY;

    private Rectangle mario;
    private int marioVX, marioVY;

    private BufferedImage marioimg;


    public GrafikKollisioner() {
        JFrame frame = new JFrame("A simple painting");
        this.setSize(width,height);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new KL());
        frame.setVisible(true);

        isRunning = false;

        try {
            marioimg = ImageIO.read(new File("supermario2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        house = new Rectangle(300,150,50,50);
        houseVX = 1;
        houseVY = 0;

        tree  =  new Rectangle(400,200,20,40);
        treeVX = 0;
        treeVY = 0;

        mario = new Rectangle(200,0,96,96);
        marioVX = 2;
        marioVY = 2;
    }

    public void update() {
        house.x += houseVX;
        if (house.x > width-house.width){
            houseVX = -1;
        }
        if (house.x < 0 ) {
            houseVX = 1;
        }
        mario.x += marioVX;
        mario.y += marioVY;
        if (mario.x < 0 || mario.x > width-80)
            marioVX = -marioVX;
        if (mario.y < 0 || mario.y > height-80)
            marioVY = -marioVY;

        // Det här är inte rätt studsning. Dels blir det åt fel håll, dels kan mario fastna i huset.
        if (mario.intersects(house)) {
            System.out.println("Hit!");
                marioVX = -marioVX;
                marioVY = -marioVY;

        }
    }

    public void draw() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        update();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,width,height);
        drawHouse(g, house.x,house.y);
        drawTree(g, tree.x,tree.y);
        drawTree(g, 100,200);
        drawTree(g, 110,200);
        drawTree(g, 120,200);
        drawTree(g, 130,200);
        drawTree(g, 140,200);
        drawTree(g, 150,200);
        g.drawImage(marioimg,mario.x,mario.y,mario.width,mario.height,null);
        g.dispose();
        bs.show();
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
        g.fillRect(x, y, 50, 40);
        g.setColor(new Color(0x444444));
        int[] xcoords = {x-5, x + 25, x + 55};
        int[] ycoords = {y, y - 25, y};
        g.fillPolygon(xcoords, ycoords, 3);
        g.fillRect(x+4,y+5,15,35);
        g.drawRect(x+25,y+10,20,20);
        g.setColor(new Color(0xFFA3DCFA));
        g.fillRect(x+26,y+11,18,18);
    }

    public static void main(String[] args) {
        GrafikKollisioner painting = new GrafikKollisioner();
        painting.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double deltaT = 1000.0/fps;
        long lastTime = System.currentTimeMillis();

        while (isRunning) {
            long now = System.currentTimeMillis();
            if (now-lastTime > deltaT) {
                update();
                draw();
                lastTime = now;
            }

        }
        stop();
    }

    private class KL implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == 'a') {
                treeVX = -5;
            }
            if (keyEvent.getKeyChar() == 'd') {
                treeVX = 5;
            }
            if (keyEvent.getKeyChar() == 'w') {
                treeVY = -5;
            }
            if (keyEvent.getKeyChar() == 's') {
                treeVY = 5;
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == 'a') {
                treeVX = 0;
            }
            if (keyEvent.getKeyChar() == 'd') {
                treeVX = 0;
            }
            if (keyEvent.getKeyChar() == 'w') {
                treeVY = 0;
            }
            if (keyEvent.getKeyChar() == 's') {
                treeVY = 0;
            }
        }
    }

    private class ML implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

    private class MML implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {

        }
    }
}
