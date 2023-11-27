import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Tada extends JPanel implements ActionListener {

    static final int WIDTH = 1000;
    static final int HEIGHT = 1000;

    int x = 500;
    int y = 500;

    double vx = 0.00;
    double ax = 0.00;
    double vy = 10.0;
    double ay = 0.5;

    int key;

    boolean running = false;
    Random random;
    Timer timer;

    Tada() {
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.CYAN);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        play();
    }

    public void play() {
        running = true;

        timer = new Timer(1, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void move() {
        if (key == KeyEvent.VK_LEFT) {
            if (vx >= -10) { vx -= ax; }
            if (ax <= 10) { ax++; }
        }
        if (key == KeyEvent.VK_RIGHT) {
            if (vx <= 10) { vx += ax; }
            if (ax <= 10) { ax++; }
        }
        if (key == KeyEvent.VK_UP) {
            vy = -10;
        }
        if(key != KeyEvent.VK_LEFT && key != KeyEvent.VK_RIGHT) {
            if (vx > 0) { vx -= 1; }
            if (vx < 0) { vx += 1; }
            if (vx == 1 || vx == -1) {
                vx = 0;
            }
        }

        x += vx;

        
        y += vy;
        vy += ay;
    }

    public void draw(Graphics graphics) {
        if (running) {
            graphics.setColor(new Color(0, 255, 0));
            graphics.fillRect(x, y, 75, 75);

            graphics.setColor(Color.white);
            graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("SCORE: ", (WIDTH - metrics.stringWidth("SCORE: ")) / 2, graphics.getFont().getSize());
        } else {
            gameOver(graphics);
        }
    }

    public void gameOver(Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("GAME OVER", (WIDTH - metrics.stringWidth("GAME OVER")) / 2, HEIGHT / 2);

        graphics.setColor(Color.white);
        graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("SCORE: ", (WIDTH - metrics.stringWidth("SCORE: ")) / 2, graphics.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (running) {
            move();
        }

        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            key = e.getKeyCode();
        }

        public void keyReleased(KeyEvent e) {
            key = 0;
        }
    }
}