package GUI.interfaces;

import GUI.model.UserAction;
import common.exceptions.InvalidGame;
import common.exceptions.NotFoundException;
import common.exceptions.SolutionInvalidException;

import java.io.IOException;

public interface Controllable {

    boolean[] getCatalog();

    int[][] getGame(char level) throws NotFoundException;

    void driveGames(String sourcePath) throws SolutionInvalidException;

    boolean[][] verifyGame(int[][] game);

    int[][] solveGame(int[][] game) throws InvalidGame;

    void logUserAction(UserAction action) throws IOException;
}

