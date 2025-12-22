package controller.solver;

public class FlyweightBoardView {
    private final int[][] board;
    private final int[][] emptyCells;
    private int [] values;

    public FlyweightBoardView(int[][] board, int[][] emptyCells) {
        this.board = board;
        this.emptyCells = emptyCells;
        this.values = new int[emptyCells.length];
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public int getCellValue(int row, int col) {
        for (int i = 0; i < emptyCells.length; i++) {
            if (emptyCells[i][0] == row && emptyCells[i][1] == col) {
                return values[i];
            }
        }
        return board[row][col];
    }
}
