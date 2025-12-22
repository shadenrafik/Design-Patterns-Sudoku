package controller.checkers;

import controller.verification.Cell;
import controller.verification.VerificationResult;
import java.util.ArrayList;
import java.util.List;

public class BoxChecker {
    public static VerificationResult checkBox(int[][] board, int boxIndex) {
        List<Cell> duplicates = new ArrayList<>();
        int startRow = (boxIndex / 3) * 3;
        int startColumn = (boxIndex % 3) * 3;
        Cell[] trackFirst = new Cell[10];

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                int currentRow = startRow + row;
                int currentColumn = startColumn + column;
                int val = board[currentRow][currentColumn];

                if (val == 0) continue;

                if (trackFirst[val] != null) {
                    Cell firstCell = trackFirst[val];
                    if (!duplicates.contains(firstCell)) {
                        duplicates.add(firstCell);
                    }

                    duplicates.add(new Cell(currentRow, currentColumn));
                } else {

                    trackFirst[val] = new Cell(currentRow, currentColumn);
                }
            }
        }

        return duplicates.isEmpty() ? VerificationResult .valid() : VerificationResult .invalid(duplicates);
    }

    public static List<Cell> check(int[][] board) {
        List<Cell> duplicates = new ArrayList<>();
        // FIX: Loop through all 9 boxes, not just 3
        for (int box = 0; box < 9; box++) {
            VerificationResult  result = checkBox(board, box);
            if (result.getDuplicateCells() != null) {
                duplicates.addAll(result.getDuplicateCells());
            }
        }
        return duplicates;
    }
}