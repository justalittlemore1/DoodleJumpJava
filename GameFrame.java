import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GameFrame() throws IOException, FontFormatException {
        Tada panel = new Tada();
        this.add(panel);
        this.setTitle("Doodle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
