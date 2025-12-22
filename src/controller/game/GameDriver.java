package controller.game;

import common.DifficultyEnum;
import common.exceptions.InvalidSolutionException;
import controller.storage.GameStorage;
import controller.verification.SudokuVerifier;
import controller.verification.VerificationResult;
import controller.verification.GameState;

import java.io.IOException;
import java.util.Map;

public class GameDriver {

    private final DifficultyGenerator difficultyGenerator;
    private final GameStorage storage;

    public GameDriver(GameStorage storage) {
        if (storage == null) {
            throw new IllegalArgumentException("Storage cannot be null");
        }
        this.storage = storage;
        this.difficultyGenerator = new DifficultyGenerator();
    }

    public void driveGames(int[][] sourceSolution) throws InvalidSolutionException {

        VerificationResult result = SudokuVerifier.validate(sourceSolution);
        if (result.getState() != GameState.VALID) {
            throw new InvalidSolutionException(
                    "Source solution is " + result.getState() + " and it cannot generate games"
            );
        }
        Map<DifficultyEnum, int[][]> generatedGames =
                difficultyGenerator.generateAllDifficulties(sourceSolution);

        try {
            for (Map.Entry<DifficultyEnum, int[][]> entry : generatedGames.entrySet()) {
                DifficultyEnum level = entry.getKey();
                int[][] board = entry.getValue();
                saveBoardForLevel(level, board);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save generated games", e);
        }
    }

    public void driveSingleLevel(int[][] sourceSolution, char levelChar) throws InvalidSolutionException {

        VerificationResult result = SudokuVerifier.validate(sourceSolution);
        if (result.getState() != GameState.VALID) {
            throw new InvalidSolutionException("Source solution is " + result.getState());
        }

        DifficultyEnum difficulty = switch (levelChar) {
            case 'E' -> DifficultyEnum.EASY;
            case 'M' -> DifficultyEnum.MEDIUM;
            case 'H' -> DifficultyEnum.HARD;
            default -> throw new IllegalArgumentException("Invalid Level: " + levelChar);
        };

        int[][] puzzle = difficultyGenerator.generateSingleDifficulty(sourceSolution, difficulty);

        try {
            saveBoardForLevel(difficulty, puzzle);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save generated game", e);
        }
    }

    private void saveBoardForLevel(DifficultyEnum level, int[][] board) throws IOException {
        char storageLevel;
        String fileName;

        switch (level) {
            case EASY -> {
                storageLevel = 'E';
                fileName = "easy1.txt";
            }
            case MEDIUM -> {
                storageLevel = 'M';
                fileName = "medium1.txt";
            }
            case HARD -> {
                storageLevel = 'H';
                fileName = "hard1.txt";
            }
            default -> throw new IllegalStateException("Unexpected value: " + level);
        }
        storage.saveGameToDifficulty(storageLevel, board, fileName);
    }
}