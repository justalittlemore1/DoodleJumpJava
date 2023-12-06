import java.util.ArrayList;

public class Generation {
    ArrayList<Blocks> toremove = new ArrayList<Blocks>();
    ArrayList<Blocks> list = new ArrayList<Blocks>();

    int[][] positions = new int[10][2];

    int maxYDist = 100;
    int minYDist = 50;

    public void initGen() {
        this.list.add(new Blocks(500 - Blocks.blockwidth / 2, 900));
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
                if (y + Blocks.blockheight < positions[i][1]
                        && y + Blocks.blockheight > positions[i][1] - this.minYDist) {
                    y = positions[i][1] - this.minYDist - Blocks.blockheight;
                }

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
            int y = (int) (Math.random() * 1000 - 1000);
            int minY = getMinY(positions);

            if (y < minY - this.maxYDist) {
                y = minY - this.maxYDist;
            }

            for (int i = 0; i < this.list.size(); i++) {
                if (y + Blocks.blockheight < positions[i][1]
                        && y + Blocks.blockheight > positions[i][1] - this.minYDist) {
                    y = positions[i][1] - this.minYDist - Blocks.blockheight;
                }

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

    public int getMinY(int[][] arr) {
        int minVal = arr[0][1];
        for (int i = 0; i < positions.length; i++) {
            if (positions[i][1] < minVal) {
                minVal = positions[i][1];
            }
        }
        return minVal;
    }
}
