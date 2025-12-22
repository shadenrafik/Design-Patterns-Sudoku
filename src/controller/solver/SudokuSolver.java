package controller.solver;

import java.util.List;
import java.util.ArrayList;

import controller.verification.*;

public class SudokuSolver {
    private final SudokuVerifier verificator;

    public SudokuSolver(SudokuVerifier verificator) {
        this.verificator = verificator;
    }

    public boolean canSolve (int [][] board){
        return findEmptyCells(board).size() == 5;
        }

    private List<int[]> findEmptyCells(int [][] board){
        List<int[]> emptyCells = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }
        return emptyCells;
    }

    public int[] solve(int [][] board){
        List<int[]> emptyCellsList = findEmptyCells(board);
        if(emptyCellsList.size() != 5){
            return null;
            // throw new IllegalArgumentException("The solver only supports puzzles with exactly 5 empty cells.");
        }
        int[][] emptyCells = emptyCellsList.toArray(new int[5][2]);

        FlyweightBoardView boardView = new FlyweightBoardView(board, emptyCells);
        PermutationIterator iterator = new PermutationIterator(5);

        while (iterator.hasNext()) {
            int[] currentPermutation = iterator.next();
            boardView.setValues(currentPermutation);
            VerificationResult result = SudokuVerifier.validate(boardView);
            if (result.getState() == GameState.VALID) {
                return currentPermutation;
            }
        }
        return null; 
    }
}
