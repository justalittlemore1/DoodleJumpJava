import javax.swing.JFrame;

public class GameFrame extends JFrame{
    GameFrame() {
        Tada penel = new Tada();
        this.add(penel);
        this.setTitle("Doodle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
