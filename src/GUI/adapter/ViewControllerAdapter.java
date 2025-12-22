package GUI.adapter;

import GUI.interfaces.Controllable;
import GUI.model.UserAction;
import common.exceptions.InvalidGame;
import common.exceptions.InvalidSolutionException;
import common.exceptions.NotFoundException;
import controller.facade.ControllerFacade;

import java.io.IOException;

public class ViewControllerAdapter implements Controllable {

    private final ControllerFacade controller;

    public ViewControllerAdapter(ControllerFacade controller) {
        this.controller = controller;
    }

    @Override
    public boolean[] getCatalog() {
        return controller.getCatalog();
    }

    @Override
    public int[][] getGame(char level) throws NotFoundException {
        return controller.getGame(level);
    }

    @Override
    public void logMove(int row, int col, int oldValue, int newValue) {
        controller.logMove(row, col, oldValue, newValue);
    }

    @Override
    public void driveGames(String path) throws InvalidSolutionException {
        controller.driveGames(path);
    }

    @Override
    public boolean[][] verifyGame(int[][] game) {
        return controller.verifyGame(game);
    }

    @Override
    public int[][] solveGame(int[][] game) throws InvalidGame {
        return controller.solveGame(game);
    }

    @Override
    public void undo(int[][] game) {
        controller.undo(game);
    }

    @Override
    public void logUserAction(UserAction action) {
        controller.logUserAction(action.name());
    }
    @Override
    public void handleGameFinished() throws IOException {
        controller.handleGameFinished();
    }

    @Override
    public void driveSingleLevel(String sourcePath, char level) throws InvalidSolutionException {
        controller.driveSingleLevel(sourcePath, level);
    }

    @Override
    public int[][] getInitialGame() {
        return controller.getInitialGame();
    }
}

