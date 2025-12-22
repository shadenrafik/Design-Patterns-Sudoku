package controller.model;

import common.DifficultyEnum;

public class Game {
    private int[][] board;
    private DifficultyEnum difficulty;
    private int gameID;

    public Game(int[][] board, DifficultyEnum difficulty, int gameID) {
        this.board = board;
        this.difficulty = difficulty;
        this.gameID = gameID;
    }


    public int[][] getBoardData() {
        return this.board;
    }

    public void setCell(int row, int col, int value) {
        if (row >= 0 && row < 9 && col >= 0 && col < 9) {
            this.board[row][col] = value;
        }
    }
}