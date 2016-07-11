
import javax.swing.*;

abstract class BaseApp extends JFrame implements AppInterface {

    BaseApp(String checkmates) {
        super(checkmates);
    }

    public abstract void showBoard(int[][] board);

    public abstract String getString(String note);

    public abstract void notification(String note);

    public int[][] getMove() throws InterruptedException {
        int[] move1 = null;
        while (move1 == null) {
            move1 = this.getClick();
            this.setVisible(true);
            Thread.sleep(80);
        }
        this.stopGettingClick();
        int[] move2 = null;
        while (move2 == null) {
            move2 = this.getClick();
            this.setVisible(true);
            Thread.sleep(80);
        }
        this.stopGettingClick();
        return new int[][]{move1, move2};
    }

    protected abstract int[] getClick();

    protected abstract void stopGettingClick();

}
