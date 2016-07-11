

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class App extends BaseApp {
    private String[][] field = new String[8][8];
    private Container container = this.getContentPane();
    private Color black = new Color(140, 60, 25);
    private Color white = new Color(240, 213, 18);
    private boolean isListening = false;
    private int[] start, end, last;

    private String getName(int name) {
        String color;
        int n = name;
        if (name > 6) {
            color = "black_";
            n -= 6;
        } else color = "white_";
        switch (n) {
            case 1:
                return color + "pawn";
            case 2:
                return color + "rook";
            case 3:
                return color + "queen";
            case 4:
                return color + "king";
            case 5:
                return color + "knight";
            case 6:
                return color + "bishop";
            default:
                return null;
        }
    }


    private void addImage(String name, int col, int row) {
        JLabel pic = new JLabel(new ImageIcon(name + ".gif"));
        JFrame frame = this;
        Cursor standard = this.getCursor();
        pic.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (isListening) {
                    start = new int[]{row, col};
                    frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                            new ImageIcon(name + ".gif").getImage(),
                            new Point(0, 0), "custom cursor"));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isListening) {
                    end = last;
                    isListening = false;
                    frame.setCursor(standard);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                last = new int[]{row, col};
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        pic.setOpaque(true);
        if ((col + row) % 2 == 0) {
            pic.setBackground(black);
        } else pic.setBackground(white);
        pic.setVerticalAlignment(JLabel.CENTER);
        pic.setHorizontalAlignment(JLabel.CENTER);
        container.add(pic);
    }

    public void showBoard(int[][] board) {
        this.container.removeAll();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != 10) {
                    String name = getName(board[row][col]);
                    this.addImage(name, row, col);
                } else this.addImage(null, row, col);
            }
        }
        this.setVisible(true);
    }

    public String getString(String note) {
        return JOptionPane.showInputDialog(note);
    }

    protected void beginGetting() {
        isListening = true;
        start = null;
        end = null;
    }

    protected void endGetting() {
        isListening = false;
    }

    protected int[][] gettingMove() {
        if (end == null) {
            return null;
        }
        return new int[][]{start, end};
    }

    public void notification(String note) {
        JOptionPane.showMessageDialog(null, note);
    }

    App() {
        super("Checkmates");
        this.setBounds(450, 10, 600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout grid = new GridLayout(8, 8, 0, 0);
        container.setLayout(grid);
        this.setVisible(true);
    }

}
