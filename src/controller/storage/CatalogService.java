package controller.storage;

import java.io.File;

public class CatalogService {

    private static final String STORAGE = "storage";
    private static final String CURRENT = STORAGE + "/current";
    private static final String EASY = STORAGE + "/easy";
    private static final String MEDIUM = STORAGE + "/medium";
    private static final String HARD = STORAGE + "/hard";

    public boolean hasUnfinishedGame() {
        File game = new File(CURRENT + "/game.txt");
        return game.exists();
    }

    public boolean hasEasyGames() {
        return hasFiles(EASY);
    }

    public boolean hasMediumGames() {
        return hasFiles(MEDIUM);
    }

    public boolean hasHardGames() {
        return hasFiles(HARD);
    }

    public boolean hasAllDifficultyGames() {
        return hasEasyGames() && hasMediumGames() && hasHardGames();
    }

    public boolean[] getCatalog() {
        return new boolean[] {
                hasUnfinishedGame(),
                hasAllDifficultyGames()
        };
    }

    private boolean hasFiles(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        return files != null && files.length > 0;
    }
}