package controller.interfaces;

import common.DifficultyEnum;
import common.exceptions.InvalidGame;
import common.exceptions.InvalidSolutionException;
import common.exceptions.NotFoundException;
import controller.model.Catalog;
import controller.model.Game;

import java.io.IOException;

public interface Viewable {

    Catalog getCatalog();

    Game getGame(DifficultyEnum level) throws NotFoundException;

    void driveGames(Game sourceGame) throws InvalidSolutionException;

    String verifyGame(Game game);

    int[] solveGame(Game game) throws InvalidGame;

    void logUserAction(String userAction) throws IOException;
}
