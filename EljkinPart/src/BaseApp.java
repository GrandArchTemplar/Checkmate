
import javax.swing.*;

abstract class BaseApp extends JFrame implements AppInterface {

    BaseApp(String checkmates) {
        super(checkmates);
    }

    public abstract void showBoard(int[][] board);

    public abstract String getString(String note);

    public abstract void notification(String note);

    public int[][] getMove() {
        int[][] move = null;
        try {
            this.beginGetting();
            while (move == null) {
                move = this.gettingMove();
                Thread.sleep(40);
            }
            this.endGetting();
        } catch (InterruptedException e) {
            notification("KAKOGO CHYORTA PRERIVANIYE, TVARI");
            return null;
        }
        return move;
    }

    protected abstract void beginGetting();
    protected abstract void endGetting();
    protected abstract int[][] gettingMove();

}
