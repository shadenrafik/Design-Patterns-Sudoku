package controller.checkers;

import controller.verification.VerificationResult;
import controller.verification.Cell;
import controller.verification.VerificationResult; // Add this import
import java.util.ArrayList;
import java.util.List;

public class ColumnChecker {


    public static VerificationResult checkColumn(int[][] board, int colIndex) {
        List<Cell> duplicates = new ArrayList<>();
        int[]  trackFirst = new int[10];
        for(int i=0; i<10; i++){
            trackFirst[i] = -1;
        }
        for (int row = 0; row < 9; row++) {
            int val = board[row][colIndex];
            if (val==0){
                continue;}

            if (trackFirst[val]!=-1) {
                Cell firstCell = new Cell(trackFirst[val], colIndex);
                if (!duplicates.contains(firstCell)) {
                    duplicates.add(firstCell);
                }
                duplicates.add(new Cell(row, colIndex));
            } else {
                trackFirst[val] = row;
            }
        }


        if (duplicates.isEmpty()) {
            return VerificationResult .valid();
        } else {
            return VerificationResult .invalid(duplicates);
        }
    }

    public static List<Cell> check(int[][] board) {
        List<Cell> duplicates = new ArrayList<>();
        for (int col = 0; col < 9; col++) {
            duplicates.addAll(checkColumn(board, col).getDuplicateCells());
        }
        return duplicates;
    }
}