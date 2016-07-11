
import javax.swing.*;

abstract class BaseApp extends JFrame implements AppInterface {

    BaseApp(String checkmates) {
        super(checkmates);
    }

    public abstract void showBoard(int[][] board);

    public abstract String getString(String note);

    public abstract void notification(String note);

    public int[][] getMove() {
        int[] move1 = null, move2 = null;
        try {
            while (move1 == null) {
                move1 = this.getClick();
                this.setVisible(true);
                Thread.sleep(80);
            }
            this.stopGettingClick();
            while (move2 == null) {
                move2 = this.getClick();
                this.setVisible(true);
                Thread.sleep(80);
            }
            this.stopGettingClick();
        } catch (InterruptedException e) {
            notification("KAKOGO CHYORTA PRERIVANIYE, TVARI");
            return null;
        }
        return new int[][]{move1, move2};
    }


    protected abstract int[] getClick();

    protected abstract void stopGettingClick();

}
