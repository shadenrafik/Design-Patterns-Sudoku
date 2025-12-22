package controller.game;

import common.DifficultyEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DifficultyGenerator {
    private final RandomPairs randomPairs;
    public DifficultyGenerator() {this.randomPairs = new RandomPairs();}
    public Map<DifficultyEnum, int[][]> generateAllDifficulties(int[][] solvedBoard) {
        if (solvedBoard == null || solvedBoard.length != 9 || solvedBoard[0].length != 9) {
            throw new IllegalArgumentException("Solved board must be a 9x9 array");
        }
        Map<DifficultyEnum, int[][]> difficulties = new HashMap<>();
        for (DifficultyEnum level : DifficultyEnum.values()) {
            int[][] difficultyBoard = generateDifficulty(solvedBoard, level.getCellsToRemove());
            difficulties.put(level, difficultyBoard);
        }
        return difficulties;
    }
    private int[][] generateDifficulty(int[][] sourceBoard, int cellsToRemove) {
        int[][] board = copyBoard(sourceBoard);
        List<int[]> positionsToRemove = randomPairs.generateDistinctPairs(cellsToRemove);
        for (int[] position : positionsToRemove) {
            int row = position[0];
            int col = position[1];
            board[row][col] = 0;
        }
        return board;
    }
    private int[][] copyBoard(int[][] source) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(source[i], 0, copy[i], 0, 9);
        }
        return copy;
    }
    public int[][] generateSingleDifficulty(int[][] solvedBoard, DifficultyEnum level) {
        if (solvedBoard == null || solvedBoard.length != 9 || solvedBoard[0].length != 9) {
            throw new IllegalArgumentException("Solved board must be a 9x9 array");
        }
        return generateDifficulty(solvedBoard, level.getCellsToRemove());
    }
    
}
