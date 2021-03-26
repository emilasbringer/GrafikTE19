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
 * Created 2021-03-19
 *
 * @author Magnus Silverdal
 */
public class Grafik extends Canvas implements Runnable{
    private int width = 1920;
    private int height = 1080;

    private Thread thread;
    int fps = 30;
    private boolean isRunning;

    private BufferStrategy bs;

    private int YH = 100;

    private int paddle1X, paddle1Y, paddle1VX, paddle1VY;

    private int paddle2X, paddle2Y, paddle2VX, paddle2VY;

    private int pongballX, pongballY, pongballVX, pongballVY;

    private BufferedImage pongball;


    public Grafik() {
        JFrame frame = new JFrame("CHICKENDEATHPONG");
        this.setSize(width,height);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new KL());
        frame.setVisible(true);

        isRunning = false;

        try {
            pongball = ImageIO.read(new File("unnamed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        paddle1X = 20;
        paddle1Y = 200;
        paddle1VX = 0;
        paddle1VY = 0;

        paddle2X = 1100;
        paddle2Y = 200;
        paddle2VX = 0;
        paddle2VY = 0;

        pongballX = 0;
        pongballY = 0;
        pongballVX = 4;
        pongballVY = 4;
    }

    public void update() {
        pongballX += pongballVX;
        pongballY += pongballVY;
        if (pongballX < 0) {
            pongballVX = 0;
            pongballVY = 0;
            System.out.println("Player 1 lost");
        }

        if (pongballX > width-80) {
            pongballVX = 0;
            pongballVY = 0;
            System.out.println("Player 2 lost");
        }

        if (pongballY < 0 || pongballY > height-80) {
            pongballVY = -pongballVY;
            paddle1X += paddle1VX;
            paddle1Y += paddle1VY;
        }

        if (pongballX > width-140 & pongballY > paddle2Y & pongballY < (paddle2Y + YH)) {
            pongballVX = -pongballVX;
        }

        if (pongballX < width+20 & pongballY > paddle1Y & pongballY < (paddle1Y + YH)) {
            pongballVX = -pongballVX;
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
        drawpaddle1(g, paddle1X,paddle1Y);
        drawpaddle2(g, paddle2X,paddle2Y);
        g.drawImage(pongball,pongballX,pongballY,80,80,null);
        g.dispose();
        bs.show();
    }

    private void drawpaddle1(Graphics g, int x, int y) {
        g.setColor(new Color(0,0,0));
        int[] xs = {0+x, 10+x, 20+x};
        int[] ys = {30+y,0+y,30+y};
        g.fillRect(7+x,YH+y,6,100);
    }

    private void drawpaddle2(Graphics g, int x, int y) {
        g.setColor(new Color(0,0,0));
        int[] xs = {0+x, 10+x, 20+x};
        int[] ys = {30+y,0+y,30+y};
        g.fillRect(750+x,YH+y,6,100);
    }

    public static void main(String[] args) {
        Grafik painting = new Grafik();
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
            if (keyEvent.getKeyChar() == 'w') {
                paddle1VY = -5;
            }
            if (keyEvent.getKeyChar() == 's') {
                paddle1VY = 5;
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == 'w') {
                paddle1VY = 0;
            }
            if (keyEvent.getKeyChar() == 's') {
                paddle1VY = 0;
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


