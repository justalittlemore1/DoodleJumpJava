public class Blocks {
    int blockwidth = 200;
    int blockheight = 30;
    int blockx = (int) (Math.random() * (1000-blockwidth));
    int blocky = (int) (Math.random() * 1000);
    boolean bouncedon = false;
    boolean unstable = false;

    public void allThatIsAboveAndAllThatIsBelow(double vy) {
        this.blocky -= vy;
    }
}
