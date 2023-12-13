import java.util.ArrayList;

public class Generation {
    ArrayList<Blocks> toremove = new ArrayList<Blocks>();
    ArrayList<Blocks> list = new ArrayList<Blocks>();

    ArrayList<int[]> pos = new ArrayList<int[]>();
    int[] coords = new int[2];

    int maxYDist = 100;
    int minYDist = 50;
    int numBlocks = 10;

    public void initGen() {
        this.list.add(new Blocks(500 - Blocks.blockwidth / 2, 900));
        while (this.list.size() < 10) {
            this.pos.clear();
            for (int i = 0; i < this.list.size(); i++) {
                coords = new int[2];
                for (int j = 0; j < 2; j++) {
                    if (j == 0) {
                        this.coords[j] = this.list.get(i).blockx;
                    } else {
                        this.coords[j] = this.list.get(i).blocky;
                    }
                }
                this.pos.add(this.coords);
            }

            int x = (int) (Math.random() * (1000 - Blocks.blockwidth));
            int y = (int) (Math.random() * 1000);

            for (int i = 0; i < this.list.size(); i++) {
                if (y + Blocks.blockheight < this.pos.get(i)[1]
                        && y + Blocks.blockheight > this.pos.get(i)[1] - this.minYDist) {
                    y = this.pos.get(i)[1] - this.minYDist - Blocks.blockheight;
                }

                if (y + Blocks.blockheight >= this.pos.get(i)[1] && y <= this.pos.get(i)[1] + Blocks.blockheight) {
                    if (x >= this.pos.get(i)[0] - Blocks.blockwidth - 50
                            && x <= this.pos.get(i)[0] + Blocks.blockwidth / 2) {
                        x -= x - (this.pos.get(i)[0] - (Blocks.blockwidth + 50));
                    } else if (x >= this.pos.get(i)[0] + Blocks.blockwidth / 2
                            && x <= this.pos.get(i)[0] + (Blocks.blockwidth + 50)) {
                        x += (this.pos.get(i)[0] + (Blocks.blockwidth + 50)) - x;
                    }
                }
            }

            if (x > 0 && x + Blocks.blockwidth < 1000) {
                this.list.add(new Blocks(x, y));
            }
        }
    }

    public void generate() {
        while (this.list.size() < numBlocks) {
            this.pos.clear();
            for (int i = 0; i < this.list.size(); i++) {
                coords = new int[2];
                for (int j = 0; j < 2; j++) {
                    if (j == 0) {
                        this.coords[j] = this.list.get(i).blockx;
                    } else {
                        this.coords[j] = this.list.get(i).blocky;
                    }
                }
                this.pos.add(this.coords);
            }

            int x = (int) (Math.random() * (1000 - Blocks.blockwidth));
            int y = (int) (Math.random() * -2000);
            int minY = getMinY();

            if (y < minY - this.maxYDist) {
                y = minY - this.maxYDist;
            }

            for (int i = 0; i < this.list.size(); i++) {
                if (y + Blocks.blockheight <= this.pos.get(i)[1] + Blocks.blockheight
                        && y + Blocks.blockheight >= this.pos.get(i)[1] - this.minYDist) {
                    y = this.pos.get(i)[1] - this.minYDist - Blocks.blockheight;
                }

                if (y + Blocks.blockheight >= this.pos.get(i)[1] && y <= this.pos.get(i)[1] + Blocks.blockheight) {
                    if (x >= this.pos.get(i)[0] - Blocks.blockwidth - 50
                            && x <= this.pos.get(i)[0] + Blocks.blockwidth / 2) {
                        x -= x - (this.pos.get(i)[0] - (Blocks.blockwidth + 50));
                    } else if (x >= this.pos.get(i)[0] + Blocks.blockwidth / 2
                            && x <= this.pos.get(i)[0] + (Blocks.blockwidth + 50)) {
                        x += (this.pos.get(i)[0] + (Blocks.blockwidth + 50)) - x;
                    }
                }
            }

            if (x > 0 && x + Blocks.blockwidth < 1000) {
                this.list.add(new Blocks(x, y));
            }
        }
    }

    public int getMinY() {
        int minVal = this.pos.get(0)[1];
        for (int i = 0; i < this.pos.size(); i++) {
            if (this.pos.get(i)[1] < minVal) {
                minVal = this.pos.get(i)[1];
            }
        }

        return minVal;
    }
}
