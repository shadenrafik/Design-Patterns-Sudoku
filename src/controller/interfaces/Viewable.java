package controller.interfaces;

import common.DifficultyEnum;
import common.exceptions.InvalidGame;
import common.exceptions.NotFoundException;
import common.exceptions.SolutionInvalidException;
import controller.model.Catalog;
import controller.model.Game;

import java.io.IOException;

public interface Viewable {

    Catalog getCatalog();

    Game getGame(DifficultyEnum level) throws NotFoundException;

    void driveGames(Game sourceGame) throws SolutionInvalidException;

    String verifyGame(Game game);

    int[] solveGame(Game game) throws InvalidGame;

    void logUserAction(String userAction) throws IOException;
}
