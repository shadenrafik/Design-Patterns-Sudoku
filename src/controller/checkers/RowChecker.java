package controller.checkers;

import controller.verification.VerificationResult;
import controller.verification.Cell;
import controller.verification.VerificationResult;
import java.util.ArrayList;
import java.util.List;

public class RowChecker {
    public static VerificationResult checkRow(int[][] board, int rowIndex) {
        List<Cell> duplicates = new ArrayList<>();

        int[] trackFirst = new int[10];

        for (int i = 0; i < 10; i++) {
            trackFirst[i] = -1;
        }

        for (int col = 0; col < 9; col++) {
            int val = board[rowIndex][col];
            if (val == 0) continue;

            if (trackFirst[val] != -1) {

                Cell firstCell = new Cell(rowIndex, trackFirst[val]);
                if (!duplicates.contains(firstCell)) {
                    duplicates.add(firstCell);
                }

                duplicates.add(new Cell(rowIndex, col));
            } else {

                trackFirst[val] = col;
            }
        }

        if (duplicates.isEmpty()) {
            return VerificationResult.valid();
        } else {
            return VerificationResult.invalid(duplicates);
        }
    }

    public static List<Cell> check(int[][] board) {
        List<Cell> duplicates = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            duplicates.addAll(checkRow(board, row).getDuplicateCells());
        }
        return duplicates;
    }
}