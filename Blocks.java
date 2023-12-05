public class Blocks {
    static int blockwidth = 207;
    static int blockheight = 75;
    int blockx = (int) (Math.random() * (1000 - blockwidth));
    int blocky = (int) (Math.random() * 1000);
    boolean bouncedon = false;
    boolean unstable = false;

    public void allThatIsAboveAndAllThatIsBelow(double vy) {
        this.blocky -= vy;
    }
}
