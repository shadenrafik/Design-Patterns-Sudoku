package controller.facade;

import common.exceptions.InvalidGame;
import common.exceptions.InvalidSolutionException;
import common.exceptions.NotFoundException;
import controller.game.GameDriver;
import controller.solver.SudokuSolver;
import controller.solver.UndoManager;
import controller.storage.CatalogService;
import controller.storage.GameLoader;
import controller.storage.GameStorage;
import controller.verification.GameState;
import controller.verification.SudokuVerifier;
import controller.verification.VerificationResult;

import java.io.File;
import java.io.IOException;

public class ControllerFacade {

    private final CatalogService catalogService = new CatalogService();
    private final GameStorage storage = new GameStorage();
    private final GameLoader loader = new GameLoader();
    private final SudokuSolver solver = new SudokuSolver(new SudokuVerifier());
    private  UndoManager undoManager;
    private char currentActiveLevel = ' ';
    private int[][] activeSessionBoard;

    public ControllerFacade() {
        storage.ensureStorageStructure();
    }

    public boolean[] getCatalog() {
        return catalogService.getCatalog();
    }
    public int[][] getInitialGame() {
        try {
            return storage.loadInitialGame();
        } catch (IOException e) {
            return null;
        }
    }
    public int[][] getGame(char level) throws NotFoundException {
        try {
            int[][] board;
            boolean isResume = (level == 'I') ||
                    (level == currentActiveLevel && catalogService.hasUnfinishedGame());

            if (isResume) {
                board = loader.loadCurrentGame();
                File logFile = new File("storage/current/log.txt");
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }
            } else {
                board = loader.loadGameByDifficulty(level);
                storage.initializeCurrentGame(board);
                this.currentActiveLevel = level;
            }
            this.undoManager = new UndoManager(new File("storage/current/log.txt"));
            this.activeSessionBoard = board;
            return board;
        } catch (IOException e) {
            throw new NotFoundException();
        }
    }
    public void driveSingleLevel(String sourcePath, char level) throws InvalidSolutionException {
        try {
            int[][] solved = loader.loadSolvedGameFromPath(sourcePath);
            new GameDriver(storage).driveSingleLevel(solved, level);
        } catch (IOException e) {
            throw new InvalidSolutionException("Invalid solved board file", e);
        }
    }

    public void handleGameFinished() {
        try {
            if (currentActiveLevel == 'E' || currentActiveLevel == 'M' || currentActiveLevel == 'H') {
                deleteSourceFile(currentActiveLevel);
            }
            storage.clearCurrentGame();
            this.activeSessionBoard = null;
            this.undoManager = null;
            this.currentActiveLevel = ' ';
        } catch (Exception e) {
            System.err.println("Failed to cleanup game files: " + e.getMessage());
        }
    }
    private void deleteSourceFile(char level) {
        File dir = getDirectoryForLevel(level);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files != null && files.length > 0) files[0].delete();
    }
    private File getDirectoryForLevel(char level) {
        String folder = switch (level) {
            case 'E' -> "easy";
            case 'M' -> "medium";
            case 'H' -> "hard";
            default -> "";
        };
        return new File("storage/" + folder);
    }
    public void driveGames(String path) throws InvalidSolutionException {
        try {
            int[][] solved = loader.loadSolvedGameFromPath(path);
            new GameDriver(storage).driveGames(solved);
        } catch (IOException e) {
            throw new InvalidSolutionException("Invalid solved board", e);
        }
    }

    public boolean[][] verifyGame(int[][] board) {
        boolean[][] errors = new boolean[9][9];
        VerificationResult r = SudokuVerifier.validate(board);

        if (r.getState() == GameState.INVALID) {
            r.getDuplicateCells().forEach(
                    c -> errors[c.getRow()][c.getCol()] = true
            );
        }
        return errors;
    }

    public int[][] solveGame(int[][] board) throws InvalidGame {
        if (!solver.canSolve(board)) {
            throw new InvalidGame();
        }

        int[] sol = solver.solve(board);
        if (sol == null) {
            throw new InvalidGame();
        }

        int k = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    int oldValue = board[r][c];
                    int newValue = sol[k++];
                    logMove(r, c, oldValue, newValue);
                    board[r][c] = newValue;
                    board[r][c] = newValue;
                }
            }
        }
        return board;
    }

    public void undo(int[][] board) {
        try {
            undoManager.undo(board);
            this.activeSessionBoard = board;
            storage.saveCurrentGame(board);
        } catch (IOException ignored) {}
    }

    public void logUserAction(String action) {
        System.out.println("UserAction: " + action);
    }


    public void logMove(int row, int col, int oldValue, int newValue) {
        if (this.activeSessionBoard != null) {
            this.activeSessionBoard[row][col] = newValue;
            try {
                storage.saveCurrentGame(this.activeSessionBoard);
            } catch (IOException e) {
                System.err.println("CRITICAL ERROR: Failed to save game state to disk: " + e.getMessage());
            }
        }
        if (undoManager != null) {
            try {
                undoManager.logMove(row, col, oldValue, newValue);
            } catch (Exception e) {
                System.err.println("Warning: Failed to log undo entry: " + e.getMessage());
            }
        }
    }
}