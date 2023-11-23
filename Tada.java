import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.JPanel;

public class Tada extends JPanel implements ActionListener{

    static final int WIDTH = 1000;
    static final int HEIGHT = 1000;
    

    int x = 500;
    int y = 500;

    char direction = 'D';
    int vx = 0;
    int vy = -10;

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

        timer = new Timer(80, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void move() {
        x+=vx;
        y+=vy;
    }

    public void draw(Graphics graphics) {
        if (running) {
            graphics.setColor(new Color(0, 255, 0));
            graphics.fillRect(x, y, 200, 200);

            graphics.setColor(Color.white);
            graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("SCORE: ", (WIDTH - metrics.stringWidth("SCORE: "))/2, graphics.getFont().getSize());
        } else {
            gameOver(graphics);
        }
    }

    public void gameOver(Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("GAME OVER", (WIDTH - metrics.stringWidth("GAME OVER"))/2, HEIGHT/2);

        graphics.setColor(Color.white);
        graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("SCORE: ", (WIDTH - metrics.stringWidth("SCORE: "))/2, graphics.getFont().getSize());
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
            // switch (e.getKeyCode()) {
            //     case KeyEvent.VK_LEFT:
            //         if (direction != 'R') {
            //             direction = 'L';
            //         }
            //         break;

            //     case KeyEvent.VK_RIGHT:
            //         if (direction != 'L') {
            //             direction = 'R';
            //         }
            //         break;

            //     case KeyEvent.VK_UP:
            //         if (direction != 'D') {
            //             direction = 'U';
            //         }
            //         break;

            //     case KeyEvent.VK_DOWN:
            //         if (direction != 'U') {
            //             direction = 'D';
            //         }
            //         break;
            // }
        }
    }
}