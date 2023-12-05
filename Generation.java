import java.util.ArrayList;

public class Generation {
    ArrayList<Blocks> toremove = new ArrayList<Blocks>();
    ArrayList<Blocks> list = new ArrayList<Blocks>();

    int[][] positions = new int[10][2];

    public void generate() {
        while (this.list.size() < 10) {
            for (int i = 0; i < this.list.size(); i++) {
                for (int j = 0; j < 2; j++) {
                    if (j == 0) {
                        this.positions[i][j] = this.list.get(i).blockx;
                    } else {
                        this.positions[i][j] = this.list.get(i).blocky;
                    }
                }
            }

            int x = (int) (Math.random() * (1000 - Blocks.blockwidth));
            int y = (int) (Math.random() * 1000);

            for (int i = 0; i < this.list.size(); i++) {
                if (y + Blocks.blockheight >= positions[i][1] && y <= positions[i][1] + Blocks.blockheight) {
                    if (x >= positions[i][0] - Blocks.blockwidth - 50 && x <= positions[i][0] + Blocks.blockwidth / 2) {
                        x -= x - (positions[i][0] - (Blocks.blockwidth + 50));
                    } else if (x >= positions[i][0] + Blocks.blockwidth / 2
                            && x <= positions[i][0] + (Blocks.blockwidth + 50)) {
                        x += (positions[i][0] + (Blocks.blockwidth + 50)) - x;

                    }
                }
            }

            if (x > 0 && x + Blocks.blockwidth < 1000) {
                this.list.add(new Blocks(x, y));
            }
        }
    }
}
