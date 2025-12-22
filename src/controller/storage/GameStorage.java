package controller.storage;

import java.io.*;

public class GameStorage {

    private static final String STORAGE = "storage";
    private static final String CURRENT = STORAGE + "/current";

    public void ensureStorageStructure() {
        new File(STORAGE + "/easy").mkdirs();
        new File(STORAGE + "/medium").mkdirs();
        new File(STORAGE + "/hard").mkdirs();
        new File(CURRENT).mkdirs();
    }

    public void initializeCurrentGame(int[][] board) throws IOException {
        clearCurrentGame();
        saveCurrentGame(board);
        saveInitialGame(board);
        new File(CURRENT + "/log.txt").createNewFile();
    }

    public void saveCurrentGame(int[][] board) throws IOException {
        writeBoard(new File(CURRENT + "/game.txt"), board);
    }
    public void saveInitialGame(int[][] board) throws IOException {
        writeBoard(new File(CURRENT + "/initial.txt"), board);
    }
    public void clearCurrentGame() {
        deleteFile(CURRENT + "/game.txt");
        deleteFile(CURRENT + "/log.txt");
        deleteFile(CURRENT + "/initial.txt");
    }
    public int[][] loadInitialGame() throws IOException {
        File file = new File(CURRENT + "/initial.txt");
        if (!file.exists()) {
            // Fallback: If no initial file exists, return null (View will handle it)
            return null;
        }
        return readBoard(file);
    }

    public void saveGameToDifficulty(char level, int[][] board, String fileName) throws IOException {
        String folder = switch (level) {
            case 'E' -> "easy";
            case 'M' -> "medium";
            case 'H' -> "hard";
            default -> throw new IllegalArgumentException("Invalid difficulty");
        };
        writeBoard(new File(STORAGE + "/" + folder + "/" + fileName), board);
    }

    public void deleteGameFromDifficulty(char level, String fileName) {
        String folder = switch (level) {
            case 'E' -> "easy";
            case 'M' -> "medium";
            case 'H' -> "hard";
            default -> throw new IllegalArgumentException("Invalid difficulty");
        };
        deleteFile(STORAGE + "/" + folder + "/" + fileName);
    }

    private void writeBoard(File file, int[][] board) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    pw.print(board[i][j] + (j < 8 ? " " : ""));
                }
                pw.println();
            }
        }
    }

    private void deleteFile(String path) {
        File f = new File(path);
        if (f.exists()) f.delete();
    }
    private int[][] readBoard(File file) throws IOException {
        int[][] board = new int[9][9];
        try (java.util.Scanner sc = new java.util.Scanner(file)) {
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    board[i][j] = sc.nextInt();
        }
        return board;
    }
}