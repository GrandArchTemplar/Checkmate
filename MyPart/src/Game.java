import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by GrandTemplar on 7/8/2016.
 */
public class Game implements Runnable {
    private int table[][] = new int[8][8];
    private Socket p1;
    private Socket p2;
    private int turnColor;
    private boolean gg;
    private int turnNumber;
    public Game(Socket player1, Socket player2) {
        this.p1 = player1;
        this.p2 = player2;
        turnColor = 1;
        gg = false;
        table = new int[][]{
                {14, 13, 12, 15, 16, 12, 13, 14},
                {11, 11, 11, 11, 11, 11, 11, 11},
                {30, 30, 30, 30, 30, 30, 30, 30},
                {30, 30, 30, 30, 30, 30, 30, 30},
                {30, 30, 30, 30, 30, 30, 30, 30},
                {30, 30, 30, 30, 30, 30, 30, 30},
                {21, 21, 21, 21, 21, 21, 21, 21},
                {24, 23, 22, 25, 26, 22, 23, 24},
        };
    }

    @Override
    public void run() {
        BufferedReader inP1, inP2;
        PrintWriter outP1, outP2;

        try {
            inP1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
        } catch (IOException e) {
            System.out.println("Failed to create input stream from player1");
            return;
        }

        try {
            inP2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
        } catch (IOException e) {
            System.out.println("Failed to create input stream from player2");
            return;
        }

        try {
            outP1 = new PrintWriter(p1.getOutputStream());
        } catch (IOException e) {
            System.out.println("Failed to create output stream from player1");
            return;
        }

        try {
            outP2 = new PrintWriter(p2.getOutputStream());
        } catch (IOException e) {
            System.out.println("Failed to create output stream from player2");
            return;
        }

        while (!gg) {
            String msgP1;
            try {
                msgP1 = inP1.readLine();
            } catch (IOException e) {
                System.out.println("Failed to receive message on " + turnNumber + " turn from white player");
                return;
            }

            String[] tokensP1 = msgP1.split(" ");
            if (tokensP1.length < 3) {
                System.out.println("Not enough tokens in game");
                return;
            }

            if (!tokensP1[0].equals("SHIT")) {
                System.out.println("Try again with SHIT");
                return;
            }

            if (!checkExpression(tokensP1[1])) {
                System.out.println("Error in begin position on " + turnNumber + " turn white player");
                return;
            }

            if (!checkExpression(tokensP1[2])) {
                System.out.println("Error in end position on " + turnNumber + " turn white player");
                return;
            }

            int condition = mainChecker(tokensP1[1], tokensP1[2], turnColor);
        }
    }

    private boolean checkExpression(String expression) {
        expression = expression.toLowerCase();
        return  (expression.charAt(0) >= 'a' && expression.charAt(0) <= 'h'
                && expression.charAt(1) >= '1' && expression.charAt(1) <= '8');
    }

    private int mainChecker(String pos1, String pos2, int color) {
        int x1 = pos1.charAt(0) - 'a';
        int y1 = pos1.charAt(1) - 1;
        int x2 = pos2.charAt(0) - 'a';
        int y2 = pos2.charAt(1) - 1;
        int result = 0;
        switch (table[x1][y1] % 10) {
            case 0: {
                result = checkEmpty();
                break;
            }
            case 1: {
                result = checkPawn(x1, y1, x2, y2, color);
                break;
            }
            case 2: {
                result = checkBishop(x1, y1, x2, y2, color);
                break;
            }
            case 3: {
                result = checkKnight(x1, y1, x2, y2, color);
                break;
            }
            case 4: {
                result = checkRook(x1, y1, x2, y2, color);
                break;
            }
            case 5: {
                result = checkQueen(x1, y1, x2, y2, color);
                break;
            }
            case 6: {
                result = checkKing(x1, y1, x2, y2, color);
                break;
            }
        }
        return 0;
    }

    private int checkEmpty() {
        return 0;
    }

    private int checkPawn(int x1, int y1, int x2, int y2, int color) {
        int direction = table[x1][y1] == 11 ? 1 : -1;
        // Check step forward
        if (y1 == y2) {
            if (
                    (
                            (x1 + direction == x2) // One step forward
                            || ((x1 + direction * 2 == x2) // or double step from first line
                                    && (((x1 == 1) && (direction == 1)) || ((x1 == 6) && direction == -1)))
                    )
                && (
                            table[x2][y2] == 30)) { // and finish cell is empty
                return 1;
            }
        }
        // Check attack
        if (((y1 == y2 + 1) || (1 + y1 == y2))
                && (x1 + direction == x2)) {
            int enemyC = table[x1][y1] < 20 ? -1 : table[x1][y1] < 30 ? 1 : 0; // see 'direction' var
            if (enemyC == direction) return 1;
        }
        return 0;
    }

    private int checkBishop(int x1, int y1, int x2, int y2, int color) {
        int x = x1 - x2;
        int y = y1 - y2;
        int t = Math.abs(x);
        if (Math.abs(x) != Math.abs(y)) {
            return 0;

        }
        int xInc = x / t;
        int yInc = y / t;
        int j = y1 + yInc;
        for (int i = x1 + xInc;  i != x2; i+= xInc, j += yInc) {
            if (table[i][j] / 10 != 3) {
                return 0;
            }
        }
        if (table[x2][y2] / 10 == color) {
            return 0;
        }
        return 1;
    }

    private int checkKnight(int x1, int y1, int x2, int y2, int color) {
        if ((Math.abs(x1 - x2) * Math.abs(y1 - y2) == 2) && (table[x2][y2] / 10 != color)) {
            return 1;
        }
        return 0;
    }

    private int checkRook(int x1, int y1, int x2, int y2, int color) {
        if ((x1 == x2) && (y1 == y2)) {
            return 0;
        }
        if (x1 == x2) {
            for (int i = Math.min(y1, y2) + 1; i < Math.max(y1, y2); i++) {
                if (table[x1][i] / 10 != 3)
                    return 0;
            }
            if (table[x1][Math.max(y1, y2)] / 10 != color) {
                return 1;
            }
        }
        if (y1 == y2) {
            for (int i = Math.min(x1, x2) + 1; i < Math.max(x1, x2); i++) {
                if (table[i][y1] / 10 != 3)
                    return 0;
            }
            if (table[Math.max(x1, x2)][y1] / 10 != color) {
                return 1;
            }
        }
        return 0;
    }

    private int checkQueen(int x1, int y1, int x2, int y2, int color) {
        return checkBishop(x1, y1, x2, y2, color) + checkRook(x1, y1, x2, y2, color);
    }

    private int checkKing(int x1, int y1, int x2, int y2, int color) {
        // Check area
        if ((Math.abs(x1 - x2) > 1) || (Math.abs(y1 - y2) > 1)) {
            // Check 0-0-0 and 0-0
            // TODO: 11.07.16 implement 0-0-0 and 0-0 checks
            return 0;
        }
        // Check target cell
        if ((table[x1][y1] / 10) == (table[x2][y2] / 10)) return 0;
        // Check menace
        int eCol = color == 1 ? 2 : 1;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (table[i][j] / 10 != color) {
                    // ...crap
                    switch (table[i][j] % 10) {
                        case 1:
                            if (checkPawn(i, j, x2, y2, eCol)) return 0;
                        case 2:
                            if (checkBishop(i, j, x2, y2, eCol)) return 0;
                        case 3:
                            if (checkKnight(i, j, x2, y2, eCol)) return 0;
                        case 4:
                            if (checkRook(i, j, x2, y2, eCol)) return 0;
                        case 5:
                            if (checkQueen(i, j, x2, y2, eCol)) return 0;
                        case 6:
                            // Avoid infinite recursion
                            if ((Math.abs(i - x2) <= 1) || (Math.abs(j - y2) <= 1)) return 0;
                        default:
                            break;
                    }
                }
            }
        }
        return 1;
    }
}
