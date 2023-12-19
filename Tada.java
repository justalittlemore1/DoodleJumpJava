import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    public static BufferedImage currentBufferedImage;
    public static BufferedImage newBufferedImage;

    ArrayList<Blocks> toremove = new ArrayList<Blocks>();

    boolean BOOM = false;
    int TICK = 0;
    int FIREX, FIREY;
    int fireball;
    int FIREWIDTH = 232;
    int FIREHEIGHT = 400;
    int flasher = 0;
    boolean gotHit = false;
    ImageIcon currentIcon;
    int deathcounter = 0;

    ImageIcon fireBaller = new ImageIcon("./Images/FIREBALL.png");
    Image image2 = fireBaller.getImage();
    Image newimg2 = image2.getScaledInstance(FIREWIDTH, FIREHEIGHT, java.awt.Image.SCALE_SMOOTH);
    ImageIcon fireBoomer = new ImageIcon(newimg2);

    ImageIcon arrower = new ImageIcon("./Images/Arrow.png");
    Image image3 = arrower.getImage();
    Image newimg3 = image3.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
    ImageIcon arrow = new ImageIcon(newimg3);

    int highScore = 0;

    public BufferedImage rotate(BufferedImage image, Double degrees) {
        double rotationRequired = Math.toRadians(degrees);
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired,
                locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage newImage = new BufferedImage(image.getWidth(),
                image.getHeight(), image.getType());
        image = op.filter(image, newImage);

        return image;
    }

    Tada() throws IOException, FontFormatException {
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(0, 13, 40));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        File file = new File("./Fonts/Fantasy.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, file);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        ge.getAllFonts();

        currentBufferedImage = ImageIO.read(getClass().getResource("./Images/PixelatedDood.png"));

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
    }

    public void move() {
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            if (vx >= -10) {
                vx -= ax;
            }
            if (ax <= 10) {
                ax++;
            }
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            if (vx <= 10) {
                vx += ax;
            }
            if (ax <= 10) {
                ax++;
            }
        }

        if (key != KeyEvent.VK_LEFT && key != KeyEvent.VK_RIGHT && key != KeyEvent.VK_D && key != KeyEvent.VK_A) {
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

            if (!gotHit) {
                graphics.setColor(Color.white);
                graphics.setFont(new Font("Ancient Modern Tales", Font.PLAIN, 80));
                FontMetrics metrics = getFontMetrics(graphics.getFont());
                graphics.drawString("SCORE: " + score, (WIDTH - metrics.stringWidth("SCORE: " + score)) / 2,
                        graphics.getFont().getSize());

                graphics.setColor(new Color(255, 0, 0));
                for (Blocks blocks : g.list) {
                    planks.paintIcon(this, graphics, blocks.blockx, blocks.blocky);
                }

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

                if (!BOOM && TICK == 0) {
                    fireball = (int) (Math.random() * 200);
                }
                if (fireball == 8) {
                    FIREX = (int) (Math.random() * (1000 - FIREWIDTH));
                    FIREY = -500;
                    BOOM = true;
                    fireball = -8;
                }
                if (BOOM) {
                    BOOM = !BOOM;
                    TICK++;
                }
                if (TICK > 0 && TICK < 100) {
                    // Warning!
                    int temp = (int) ((Math.random() * 16) - 8);

                    if (flasher < 30) {
                        arrow.paintIcon(this, graphics, FIREX + temp, 100);
                        flasher++;
                    }
                    if (flasher >= 30 && flasher < 50) {
                        flasher++;
                    }
                    if (flasher == 50) {
                        flasher = 0;
                    }
                    TICK++;
                }
                if (TICK >= 100) {
                    int temp = (int) ((Math.random() * 50) - 25);
                    fireBoomer.paintIcon(this, graphics, FIREX + temp, FIREY);
                    FIREY = (TICK - 100) * 20 - 500;
                    if (ATIAAATIB) {
                        FIREY -= (vy);
                    }
                    TICK++;

                    if (FIREY > 1000) {
                        TICK = 0;
                    }

                    if ((x + guywidth - 40) >= (FIREX + temp) && (x + 40) <= (FIREX + temp + FIREWIDTH)
                            && (y + guyheight) >= (FIREY + 7)
                            && (y + guyheight) <= (FIREY + FIREHEIGHT)) {
                        gotHit = true;
                        currentIcon = Dood0;
                    }
                }

                if (y > HEIGHT) {
                    running = false;
                }

            } else {
                x = 0;
                y = 0;
                Image endImage = currentIcon.getImage();
                Image newEnd = endImage.getScaledInstance((int) (currentIcon.getIconHeight() * 1.5),
                        (int) (currentIcon.getIconWidth() * 1.5), java.awt.Image.SCALE_SMOOTH);
                currentIcon = new ImageIcon(newEnd);
                currentIcon.paintIcon(this, graphics, -200, -200);
                deathcounter++;
                if (deathcounter > 5) {
                    running = false;
                }
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
        if (score > highScore) {
            highScore = score;
        }

        graphics.setColor(new Color(0, 13, 80));
        for (int i = 0; i < 1000; i += 20) {
            graphics.drawLine(i, 0, i, 1000);
            graphics.drawLine(0, i, 1000, i);
        }

        // Experiment to create an animation!
        BufferedImage newBufferedImage = rotate(currentBufferedImage, 2.0d);
        currentBufferedImage = newBufferedImage;
        ImageIcon currentIcon = new ImageIcon(currentBufferedImage);
        currentIcon.paintIcon(this, graphics, 240, 250);

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
                625);

        if (!endbool) {
            graphics.setColor(Color.white);
        } else {
            graphics.setColor(Color.blue);
        }
        graphics.setFont(new Font("Ancient Modern Tales", Font.PLAIN, 150));
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("SCORE: " + score, (WIDTH - metrics.stringWidth("SCORE: " + score)) / 2,
                400);

        if (!endbool) {
            graphics.setColor(Color.MAGENTA);
        } else {
            graphics.setColor(Color.white);
        }
        graphics.setFont(new Font("Ancient Modern Tales", Font.PLAIN, 150));
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("HIGHEST: " + highScore, (WIDTH - metrics.stringWidth("HIGHEST: " + highScore)) / 2,
                175);

        if (!endbool) {
            graphics.setColor(Color.white);
        } else {
            graphics.setColor(Color.green);
        }
        graphics.setFont(new Font("Ancient Modern Tales", Font.PLAIN, 150));
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("BROCCOLI SOUP!", (WIDTH - metrics.stringWidth("BROCCOLI SOUP!")) / 2,
                950);

        if (endbool) {
            graphics.setColor(Color.white);
        } else {
            graphics.setColor(Color.ORANGE);
        }
        graphics.setFont(new Font("Ancient Modern Tales", Font.PLAIN, 50));
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("(space to play again)", (WIDTH - metrics.stringWidth("(space to play again)")) / 2,
                725);

        endcounter++;

        if (key == KeyEvent.VK_SPACE) {
            timer.stop();

            x = 500;
            y = 500;
            guywidth = 200;
            guyheight = 200;

            vx = 0.00;
            ax = 0.00;
            vy = -32.0;
            ay = 0.7;

            bounceHeight = 0;
            score = 0;

            ATIAAATIB = false;

            running = false;

            g = new Generation();

            cur = 0;
            curcounter = 0;
            bouncing = false;

            endcounter = 0;
            endbool = false;

            toremove = new ArrayList<Blocks>();

            BOOM = false;
            TICK = 0;
            FIREWIDTH = 232;
            FIREHEIGHT = 400;
            flasher = 0;
            gotHit = false;
            deathcounter = 0;

            random = new Random();
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            this.setBackground(new Color(0, 13, 40));
            this.setFocusable(true);
            this.addKeyListener(new MyKeyAdapter());

            try {
                currentBufferedImage = ImageIO.read(getClass().getResource("./Images/PixelatedDood.png"));
            } catch (Exception e) {
                // TODO: handle exception
            }

            play();
            super.paintComponent(graphics);
        }
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

            if (score >= 2000 && score <= 5000) {
                g.numBlocks = 9;
                g.maxYDist = 150;
                g.minYDist = 100;
            } else if (score > 5000 && score <= 7500) {
                g.numBlocks = 7;
                g.maxYDist = 200;
                g.minYDist = 150;
            } else if (score > 7500 && score <= 10000) {
                g.numBlocks = 6;
                g.maxYDist = 250;
                g.minYDist = 200;
            } else if (score > 10000 && score <= 20000) {
                g.numBlocks = 5;
                g.maxYDist = 400;
                g.minYDist = 300;
            } else if (score > 20000) {
                g.numBlocks = 2;
                g.maxYDist = 400;
                g.minYDist = 300;
            }

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