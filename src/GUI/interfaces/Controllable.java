package GUI.interfaces;

import GUI.model.UserAction;
import common.exceptions.InvalidGame;
import common.exceptions.InvalidSolutionException;
import common.exceptions.NotFoundException;

import java.io.IOException;

public interface Controllable {

    boolean[] getCatalog();

    int[][] getGame(char level) throws NotFoundException;

    void driveGames(String sourcePath) throws InvalidSolutionException;

    boolean[][] verifyGame(int[][] game);

    int[][] solveGame(int[][] game) throws InvalidGame;

    void undo(int[][] game);

    void logMove(int row, int col, int oldValue, int newValue);

    void logUserAction(UserAction action) throws IOException;


    void handleGameFinished() throws IOException;

    int[][] getInitialGame();

    void driveSingleLevel(String sourcePath, char level) throws InvalidSolutionException;
}
