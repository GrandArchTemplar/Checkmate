
interface AppInterface {
    //input as you see
    public void showBoard(int[][] board);

    //for getting name of player, and chosen figure instead of pawn
    public String getString(String note);

    //to say, whose turn and notify about check, mate, stalemate
    public void notification(String note);

    //as you see, but swapped
    public int[][] getMove();
}
