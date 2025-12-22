package GUI.verify;

import GUI.interfaces.Controllable;
import GUI.start.SelectDifficultyView;
import GUI.start.StartGameView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VerifyView extends JPanel {

    private JTextField[][] cells = new JTextField[9][9];
    private JButton verifyBtn = new JButton("Verify Current Board");

    private final Controllable controller;
    private int[][] initialBoard;

    public VerifyView(Controllable controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel gridPanel = new JPanel(new GridLayout(9, 9, 2, 2));
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                cells[r][c] = new JTextField();
                cells[r][c].setHorizontalAlignment(JTextField.CENTER);
                cells[r][c].setFont(new Font("Arial", Font.BOLD, 20));
                final int row = r;
                final int col = c;
                cells[r][c].addFocusListener(new java.awt.event.FocusAdapter() {
                    String oldVal = "";
                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) { oldVal = cells[row][col].getText(); }
                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {
                        String newVal = cells[row][col].getText();
                        if (!oldVal.equals(newVal)) {
                            try {
                                int v1 = oldVal.isEmpty() ? 0 : Integer.parseInt(oldVal);
                                int v2 = newVal.isEmpty() ? 0 : Integer.parseInt(newVal);
                                controller.logMove(row, col, v1, v2);
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                });

                styleCell(r, c, false);
                gridPanel.add(cells[r][c]);
            }
        }
        verifyBtn.addActionListener(e -> handleVerification());
        add(gridPanel, BorderLayout.CENTER);
        add(verifyBtn, BorderLayout.SOUTH);
    }

    private void handleVerification() {
        int[][] boardData = getBoardData();
        boolean[][] errors = controller.verifyGame(boardData);
        boolean hasErrors = false;
        boolean isFull = true;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (errors[r][c]) hasErrors = true;
                if (boardData[r][c] == 0) isFull = false;
                if (errors[r][c]) cells[r][c].setBackground(Color.PINK);
                else styleCell(r, c, false);
            }
        }
        if(hasErrors) JOptionPane.showMessageDialog(this, "Duplicate numbers found!", "Error", JOptionPane.ERROR_MESSAGE);
        else if(!isFull) JOptionPane.showMessageDialog(this, "Board is incomplete.", "Warning", JOptionPane.WARNING_MESSAGE);
        else {
            JOptionPane.showMessageDialog(this, "Correct! Game Finished.", "Success", JOptionPane.INFORMATION_MESSAGE);
            try {
                controller.handleGameFinished();
                closeAndRedirect();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    private void closeAndRedirect() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        if (parent != null) {
            parent.dispose();
        }
        boolean[] catalog = controller.getCatalog();
        if (catalog[1]) {
            new SelectDifficultyView(controller).setVisible(true);
        } else {
            new StartGameView(controller).setVisible(true);
        }
    }

    private void styleCell(int r, int c, boolean isError) {
        if (isError) {
            cells[r][c].setBackground(Color.PINK);
        } else {
            if ((r / 3 + c / 3) % 2 == 0) cells[r][c].setBackground(new Color(235, 235, 235));
            else cells[r][c].setBackground(Color.WHITE);
        }
    }

    public void loadBoard(int[][] currentBoard, int[][] initialBoard) {
        this.initialBoard = initialBoard; // Store for later use
        renderGrid(currentBoard);
    }
    private void renderGrid(int[][] currentBoard) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int val = currentBoard[r][c];
                boolean isOriginal = (initialBoard != null && initialBoard[r][c] != 0);

                if (val != 0) {
                    cells[r][c].setText(String.valueOf(val));
                    if (isOriginal) {
                        cells[r][c].setEditable(false);
                        cells[r][c].setForeground(Color.BLUE);
                    } else {
                        cells[r][c].setEditable(true);
                        cells[r][c].setForeground(Color.BLACK);
                    }
                } else {
                    cells[r][c].setText("");
                    cells[r][c].setEditable(true);
                    cells[r][c].setForeground(Color.BLACK);
                }
            }
        }
    }
    public void loadBoard(int[][] board) {
        loadBoard(board, board);
    }
    public int[][] getBoardData() {
        int[][] board = new int[9][9];
        for(int r=0; r<9; r++){
            for(int c=0; c<9; c++){
                String t = cells[r][c].getText().trim();
                if(!t.isEmpty()) board[r][c] = Integer.parseInt(t);
            }
        }
        return board;
    }

    public void refreshBoard(int[][] board) {
        renderGrid(board);
    }
}