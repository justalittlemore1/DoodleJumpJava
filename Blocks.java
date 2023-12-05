public class Blocks {
    static int blockwidth = 207;
    static int blockheight = 75;
    int blockx;
    int blocky;
    boolean bouncedon = false;
    boolean unstable = false;

    public Blocks(int x, int y) {
        this.blockx = x;
        this.blocky = y;
    }

    public void allThatIsAboveAndAllThatIsBelow(double vy) {
        this.blocky -= vy;
    }
}
