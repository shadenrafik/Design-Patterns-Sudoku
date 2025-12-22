package GUI.util;

import GUI.interfaces.Controllable;
import GUI.solver.SolverView;
import GUI.verify.VerifyView;

import javax.swing.*;
import java.awt.*;

public class GameFrameHelper {

    public static void openGameFrame(Controllable controller, int[][] board, JFrame currentView) {
        JFrame gameFrame = new JFrame("Sudoku Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(600, 600);
        gameFrame.setLayout(new BorderLayout());

        VerifyView verifyView = new VerifyView(controller);
        int[][] initialBoard = controller.getInitialGame();
        verifyView.loadBoard(board, initialBoard);

        SolverView solverView = new SolverView(controller, verifyView);

        gameFrame.add(verifyView, BorderLayout.CENTER);
        gameFrame.add(solverView, BorderLayout.SOUTH);

        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        if (currentView != null) {
            currentView.dispose();
        }
    }
}
