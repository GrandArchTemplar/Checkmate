

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends BaseApp {
    private String[][] field = new String[8][8];
    private Container container = this.getContentPane();
    private Color black = new Color(140, 60, 25);
    private Color white = new Color(240, 213, 18);
    private Object monitor = new Object();
    private boolean isListening = false;
    private int[] click;

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
        JButton pic = new JButton(new ImageIcon(name + ".gif"));
        pic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isListening) {
                    click = (new int[]{col, row});
                    isListening = false;
                }
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
                    String name = getName(board[7 - row][col]);
                    this.addImage(name, 7 - row, col);
                } else this.addImage(null, row, col);
            }
        }
    }

    public String getString(String note) {
        return (String) JOptionPane.showInputDialog(note);
    }

    public int[] getClick() {
        isListening = true;
        return click;
    }

    public void stopGettingClick() {
        isListening = false;
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
    }

}
