
interface AppInterface {
    public void showBoard(int[][] board);

    //for getting name of player, and chosen figure instead of pawn

    //to say, whose turn and notify about check, mate, stalemate
    public void notification(String note);

    public int[][] getMove() throws InterruptedException;
}
