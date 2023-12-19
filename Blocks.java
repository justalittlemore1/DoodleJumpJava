public class Blocks {
    static int blockwidth = 207;
    static int blockheight = 75;
    int blockx;
    int blocky;
    boolean bouncedon = false;
    boolean unstable = false;

    boolean isMoving = false;
    boolean canMove = false;
    int x = 5;
    static int moveProb = 100;
    int moving = (int) (Math.random() * moveProb);

    public Blocks(int x, int y, boolean canMove) {
        this.blockx = x;
        this.blocky = y;
        this.canMove = canMove;
    }

    public void allThatIsAboveAndAllThatIsBelow(double vy) {
        this.blocky -= vy;
    }

    public void moveItMoveIt() {
        if (moving < 10 && canMove) {
            this.isMoving = true;
        }
        if (isMoving) {
            this.blockx += x;
        }
        if (this.blockx < 0 || this.blockx + Blocks.blockwidth > 1000) {
            x *= -1;
        }
    }
}
