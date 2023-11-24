import java.util.ArrayList;

public class Blocks {
    int blockwidth = 100;
    int blockheight = 30;
    int blockx = (int) (Math.random() * 1000);
    int blocky = (int) (Math.random() * 1000);

    public static void main(String[] args) {
        ArrayList<Blocks> list = new ArrayList<>();
        list.add((new Blocks()));
        Blocks test = list.get(0);
        System.out.println(test.blockx);
    }
}
