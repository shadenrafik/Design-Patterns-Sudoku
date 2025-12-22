package controller.verification;

import java.util.ArrayList;
import java.util.List;
import controller.checkers.RowChecker;
import controller.solver.FlyweightBoardView;
import controller.checkers.ColumnChecker;
import controller.checkers.BoxChecker;

public class SudokuVerifier {
    public static VerificationResult validate(int[][] board) {
        List<Cell> duplicates = new ArrayList<>();

        duplicates.addAll(RowChecker.check(board));
        duplicates.addAll(ColumnChecker.check(board));
        duplicates.addAll(BoxChecker.check(board));

        if (!duplicates.isEmpty()) {
            return VerificationResult.invalid(duplicates);
        }

        for(int i=0 ; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] == 0){
                    return VerificationResult.incomplete();
                }
            }
        }

        return VerificationResult.valid();
    }

    public static VerificationResult validate(FlyweightBoardView view) {
    int[][] temp = new int[9][9];

    for (int r = 0; r < 9; r++) {
        for (int c = 0; c < 9; c++) {
            temp[r][c] = view.getCellValue(r, c);
        }
    }
    return validate(temp);
    }
}