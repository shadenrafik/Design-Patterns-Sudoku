package controller.verification;

import controller.interfaces.Viewable;
import controller.verification.*;
import controller.model.Game;

public class VerificationController implements Viewable {

    public String verifyGameText(int[][] board) {
        VerificationResult result = SudokuVerifier.validate(board);
        return result.getState().toString().toLowerCase();
    }

    public boolean[][] getErrorGrid(int[][] board) {
        boolean[][] errorCell = new boolean[9][9];
        VerificationResult result = SudokuVerifier.validate(board);

        if (result.getState() == GameState.INVALID) {
            for (Cell cell : result.getDuplicateCells()) {
                errorCell[cell.getRow()][cell.getCol()] = true;
            }
        }
        return errorCell;
    }

    @Override public String verifyGame(Game game) { return verifyGameText(game.getBoardData()); }
    @Override public controller.model.Catalog getCatalog() { return null; }
    @Override public Game getGame(common.DifficultyEnum l) { return null; }
    @Override public void driveGames(Game g) {}
    @Override public int[] solveGame(Game g) { return new int[0]; }
    @Override public void logUserAction(String a) {}
}