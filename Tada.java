import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Tada extends JPanel implements ActionListener {

    static final int WIDTH = 1000;
    static final int HEIGHT = 1000;

    int x = 500;
    int y = 800;
    int guywidth = 100;
    int guyheight = 150;

    double vx = 0.00;
    double ax = 0.00;
    double vy = 12.0;
    double ay = 0.7;

    int bounceHeight = 0;
    int score = 0;

    boolean ATIAAATIB = false;

    int key;

    boolean running = false;
    Random random;
    Timer timer;

    Generation g = new Generation();

    ArrayList<Blocks> toremove = new ArrayList<Blocks>();

    // ArrayList<Blocks> list = new ArrayList<Blocks>();

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

        // for (int i = 0; i < 10; i++) {
        // list.add((new Blocks(0, 0)));
        // }

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
            if (vx >= -7) {
                vx -= ax;
            }
            if (ax <= 7) {
                ax++;
            }
        }
        if (key == KeyEvent.VK_RIGHT) {
            if (vx <= 7) {
                vx += ax;
            }
            if (ax <= 7) {
                ax++;
            }
        }
        if (key == KeyEvent.VK_UP) {
            vy = -10;
        }
        if (key != KeyEvent.VK_LEFT && key != KeyEvent.VK_RIGHT) {
            if (vx > 0) {
                vx -= 1;
            }
            if (vx < 0) {
                vx += 1;
            }
            if (vx == 1 || vx == -1) {
                vx = 0;
            }
        }
        x += vx;
        if (!ATIAAATIB) {
            y += vy;
        }

        if (vy <= 40) {
            vy += ay;
        }

        if (y <= HEIGHT / 2 - guyheight && vy < 0) {
            ATIAAATIB = true;
        } else {
            ATIAAATIB = false;
        }

        for (Blocks blocks : g.list) {
            if (blocks.bouncedon) {
                blocks.blocky += 15;
            }
            if (ATIAAATIB) {
                blocks.allThatIsAboveAndAllThatIsBelow(vy);
            }
            if (blocks.blocky > HEIGHT) {
                toremove.add(blocks);
            }
        }

        if (x + guywidth / 2 < 0) {
            x = WIDTH - guywidth / 2;
        } else if (x + guywidth / 2 > WIDTH) {
            x = 0 - guywidth / 2;
        }
    }

    public void draw(Graphics graphics) {
        if (running) {
            graphics.setColor(new Color(0, 255, 0));
            graphics.fillRect(x, y, guywidth, guyheight);

            graphics.setColor(new Color(255, 0, 0));
            for (Blocks blocks : g.list) {
                graphics.fillRect(blocks.blockx, blocks.blocky, Blocks.blockwidth,
                        Blocks.blockheight);
            }

            graphics.setColor(Color.white);
            graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("SCORE: " + score, (WIDTH - metrics.stringWidth("SCORE: " + score)) / 2,
                    graphics.getFont().getSize());
        } else {
            gameOver(graphics);
        }
    }

    public void checkBounce() {
        for (Blocks blocks : g.list) {
            if ((x + guywidth) >= (blocks.blockx) && x <= (blocks.blockx + Blocks.blockwidth)
                    && (y + guyheight) >= (blocks.blocky) && (y + guyheight) <= (blocks.blocky + Blocks.blockheight)
                    && vy >= 0) {
                if (!blocks.bouncedon) {
                    blocks.bouncedon = true;
                }
                vy = -25;
                if (!blocks.unstable) {
                    bounceHeight = y;
                    blocks.unstable = true;
                    score += score();
                }
            }
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

    public int score() {
        int theScore = 0;

        if (bounceHeight != 0) {
            int vi = (int) Math.sqrt(Math.pow(25, 2) + 2 * -ay * -((HEIGHT / 2 - guyheight) - bounceHeight));
            theScore = (int) ((-(Math.pow(vi, 2))) / (2 * -ay));
        }

        return theScore;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (running) {
            move();
            checkBounce();
            g.list.removeAll(toremove);
            toremove = new ArrayList<Blocks>();

            g.generate();
            System.out.println(g.list.size());
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