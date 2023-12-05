public class Blocks {
    static int blockwidth = 200;
    static int blockheight = 30;
    // int blockx = (int) (Math.random() * (1000 - blockwidth));
    // int blocky = (int) (Math.random() * 1000);
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
