public class Blocks {
    int blockwidth = 200;
    int blockheight = 30;
    int blockx = (int) (Math.random() * 1000);
    int blocky = (int) (Math.random() * 1000);

    public void allThatIsAboveAndAllThatIsBelow(int vy) {
        this.blocky -= vy;
    }
}
