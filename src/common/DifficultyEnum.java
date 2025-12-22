package common;

public enum DifficultyEnum {
    EASY(10, "easy"),
    MEDIUM(20, "medium"),
    HARD(25, "hard");

    private final int cellsToRemove;
    private final String folderName;

    DifficultyEnum(int cellsToRemove, String folderName) {
        this.cellsToRemove = cellsToRemove;
        this.folderName = folderName;
    }

    public int getCellsToRemove() {
        return cellsToRemove;
    }

    public String getFolderName() {
        return folderName;
    }

    public static DifficultyEnum fromFolderName(String folderName) {
        for (DifficultyEnum level : values()) {
            if (level.folderName.equalsIgnoreCase(folderName)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid difficulty level: " + folderName);
    }
}