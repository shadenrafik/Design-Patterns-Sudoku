package controller.storage;

import java.io.*;
        import java.util.Scanner;

public class GameLoader {

    private static final String STORAGE = "storage";

    public int[][] loadCurrentGame() throws IOException {
        return readBoard(new File(STORAGE + "/current/game.txt"));
    }

    public int[][] loadGameByDifficulty(char level) throws IOException {
        String folder = switch (level) {
            case 'E' -> "easy";
            case 'M' -> "medium";
            case 'H' -> "hard";
            default -> throw new IllegalArgumentException("Invalid difficulty");
        };

        File dir = new File(STORAGE + "/" + folder);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0)
            throw new FileNotFoundException("No games found");

        return readBoard(files[0]);
    }

    public int[][] loadSolvedGameFromPath(String path) throws IOException {
        return readBoard(new File(path));
    }

    private int[][] readBoard(File file) throws IOException {
        int[][] board = new int[9][9];
        try (Scanner sc = new Scanner(file)) {
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    board[i][j] = sc.nextInt();
        }
        return board;
    }
}