import sample.Main;

/**
 * Created by мвидео on 09.07.2016.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Main app = new Main();
        int[][] table = new int[][]{
                {2, 5, 6, 3, 4, 6, 5, 2},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        };
        app.showBoard(table);
        app.setVisible(true);
        app.notification("123");
    }
}
