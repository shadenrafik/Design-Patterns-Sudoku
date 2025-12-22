package controller.solver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UndoManager {

    private final File logFile;

    public UndoManager(File logFile) {
        this.logFile = logFile;

        try {
            if (!logFile.exists()) {
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize undo log file", e);
        }
    }

    public void logMove(int row, int col, int oldValue, int newValue) throws IOException {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(row + "," + col + "," + oldValue + "," + newValue + "\n");
        }
    }

    public void undo(int[][] board) throws IOException {
        List<String> lines = new ArrayList<>();

        if (!logFile.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        if (lines.isEmpty()) return;

        String last = lines.remove(lines.size() - 1);
        rewrite(lines);

        String[] parts = last.split(",");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
        int oldValue = Integer.parseInt(parts[2]);

        board[row][col] = oldValue;
    }

    private void rewrite(List<String> lines) throws IOException {
        try (FileWriter writer = new FileWriter(logFile, false)) {
            for (String l : lines) {
                writer.write(l + "\n");
            }
        }
    }
}
