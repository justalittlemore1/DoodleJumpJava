import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Tada extends JPanel implements ActionListener {

    static final int WIDTH = 1000;
    static final int HEIGHT = 1000;

    int x = 500;
    int y = 500;
    int guywidth = 200;
    int guyheight = 200;

    double vx = 0.00;
    double ax = 0.00;
    double vy = -32.0;
    double ay = 0.7;

    int bounceHeight = 0;
    int score = 0;

    boolean ATIAAATIB = false;

    int key;

    boolean running = false;
    Random random;
    Timer timer;

    Generation g = new Generation();

    ImageIcon Dood0 = getZeImage("./Images/Broccoli.png");
    ImageIcon Dood1 = getZeImage("./Images/Broccoli1.png");
    ImageIcon Dood2 = getZeImage("./Images/Broccoli2.png");
    ImageIcon Dood3 = getZeImage("./Images/Broccoli3.png");
    ImageIcon Dood4 = getZeImage("./Images/Broccoli4.png");

    int cur = 0;
    int curcounter = 0;
    boolean bouncing = false;

    ImageIcon yupIcon = new ImageIcon("./Images/Plank.png");
    Image image1 = yupIcon.getImage();
    Image newimg1 = image1.getScaledInstance(Blocks.blockwidth, Blocks.blockheight, java.awt.Image.SCALE_SMOOTH);
    ImageIcon planks = new ImageIcon(newimg1);

    int endcounter = 0;
    boolean endbool = false;

    public static BufferedImage original;
    public static BufferedImage rotated90;
    public static BufferedImage rotatedMinus90;

    ArrayList<Blocks> toremove = new ArrayList<Blocks>();

    public BufferedImage rotate(BufferedImage image, Double degrees) {
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);

        BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotate.createGraphics();
        int x11 = (newWidth - image.getWidth()) / 2;
        int y11 = (newHeight - image.getHeight()) / 2;
        AffineTransform at = new AffineTransform();
        at.setToRotation(radians, x + (image.getWidth() / 2), y + (image.getHeight() / 2));
        at.translate(x11, y11);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        System.out.println("Reached here!");
        System.out.println(rotate);
        return rotate;
    }

    Tada() {
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(0, 13, 40));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        try {
            File file = new File("./Fonts/Fantasy.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, file);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            ge.getAllFonts();

            original = ImageIO.read(getClass().getResource("./Images/PixelatedDood.png"));
            rotated90 = rotate(original, 90.0d);
            rotatedMinus90 = rotate(original, -90.0d);

        } catch (Exception e) {
            // Only here because of exception error.
        }

        play();
    }

    public ImageIcon getZeImage(String filename) {
        ImageIcon bruhIcon = new ImageIcon(filename);
        Image image = bruhIcon.getImage();
        Image newimg = image.getScaledInstance(guywidth, guywidth, java.awt.Image.SCALE_SMOOTH);
        ImageIcon alienDood = new ImageIcon(newimg);
        return alienDood;
    }

    public void play() {
        running = true;

        g.initGen();

        timer = new Timer(1, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);

        if (bouncing) {
            bouncing = false;
            cur++;
        }
        if (cur > 0) {
            if (curcounter < 7) {
                curcounter++;
            }
            if (curcounter == 7) {
                curcounter = 0;
                cur++;
            }
            switch (cur) {
                case 1:
                    Dood1.paintIcon(this, graphics, x, y);
                    break;
                case 2:
                    Dood2.paintIcon(this, graphics, x, y);
                    break;
                case 3:
                    Dood3.paintIcon(this, graphics, x, y);
                    break;
                case 4:
                    Dood4.paintIcon(this, graphics, x, y);
                    break;
                default:
                    cur = 0;
                    Dood0.paintIcon(this, graphics, x, y);
            }
        } else {
            Dood0.paintIcon(this, graphics, x, y);
        }
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
            graphics.setColor(new Color(0, 13, 80));
            for (int i = 0; i < 1000; i += 20) {
                graphics.drawLine(i, 0, i, 1000);
                graphics.drawLine(0, i, 1000, i);
            }

            graphics.setColor(Color.white);
            graphics.setFont(new Font("Ancient Modern Tales", Font.PLAIN, 80));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("SCORE: " + score, (WIDTH - metrics.stringWidth("SCORE: " + score)) / 2,
                    graphics.getFont().getSize());

            graphics.setColor(new Color(255, 0, 0));
            for (Blocks blocks : g.list) {
                planks.paintIcon(this, graphics, blocks.blockx, blocks.blocky);
            }

            int minY = g.getMinY(g.positions);

            if (score >= 2000 + minY && score <= 5000 + minY) {
                g.numBlocks = 9;
                g.maxYDist = 200;
                g.minYDist = 150;
            } else if (score > 5000 + minY && score <= 7500 + minY) {
                g.numBlocks = 7;
                g.maxYDist = 300;
                g.minYDist = 250;
            } else if (score > 7500 + minY && score <= 10000 + minY) {
                g.numBlocks = 6;
                g.maxYDist = 325;
                g.minYDist = 275;
            } else if (score > 10000 + minY) {
                g.numBlocks = 5;
                g.maxYDist = 325;
                g.minYDist = 275;
            }

            if (y > HEIGHT) {
                running = false;
            }
        } else {
            gameOver(graphics);
        }
    }

    public void checkBounce() {
        for (Blocks blocks : g.list) {
            if ((x + guywidth - 40) >= (blocks.blockx) && (x + 40) <= (blocks.blockx + Blocks.blockwidth)
                    && (y + guyheight) >= (blocks.blocky + 7)
                    && (y + guyheight) <= (blocks.blocky + Blocks.blockheight)
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
                bouncing = true;
            }
        }
    }

    public void gameOver(Graphics graphics) {
        if (endcounter > 40) {
            endcounter = 0;
            endbool = !endbool;
        }

        if (endbool) {
            graphics.setColor(Color.white);
        } else {
            graphics.setColor(Color.red);
        }
        graphics.setFont(new Font("Ancient Modern Tales", Font.PLAIN, 190));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("GAME OVER", (WIDTH - metrics.stringWidth("GAME OVER")) / 2,
                (HEIGHT / 2) + (graphics.getFont().getSize() / 2));

        if (!endbool) {
            graphics.setColor(Color.white);
        } else {
            graphics.setColor(Color.blue);
        }
        graphics.setFont(new Font("Ancient Modern Tales", Font.PLAIN, 150));
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("SCORE: " + score, (WIDTH - metrics.stringWidth("SCORE: " + score)) / 2,
                HEIGHT / 3);

        // Experiment to create an animation!
        ImageIcon temp1 = new ImageIcon(original);
        ImageIcon temp2 = new ImageIcon(rotated90);
        ImageIcon temp3 = new ImageIcon(rotatedMinus90);

        temp1.paintIcon(this, graphics, 0, 500);
        temp2.paintIcon(this, graphics, 200, 200);
        temp3.paintIcon(this, graphics, 200, 200);

        endcounter++;
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