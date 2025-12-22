package GUI.solver;

import java.awt.FlowLayout;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import GUI.interfaces.Controllable;
import GUI.start.SelectDifficultyView;
import GUI.verify.VerifyView;
import GUI.resume.ResumeView;
import common.exceptions.InvalidGame;

public class SolverView extends JPanel {

    private final JButton solveButton = new JButton("Solve");
    private final JButton undoButton = new JButton("Undo");
    private final JButton backButton = new JButton("Back to Menu"); // Defined here

    private final Controllable controller;
    private final VerifyView verifyView;

    public SolverView(Controllable controller, VerifyView verifyView) {
        this.controller = controller;
        this.verifyView = verifyView;

        setLayout(new FlowLayout());

        solveButton.addActionListener(e -> solve());
        undoButton.addActionListener(e -> undo());

        backButton.addActionListener(e -> {
            Window parentWindow = SwingUtilities.getWindowAncestor(this);
            if (parentWindow != null) {
                parentWindow.dispose();
            }
            new ResumeView(controller).setVisible(true);
        });

        add(solveButton);
        add(undoButton);
        add(backButton);
    }

    private void solve() {
        try {
            int[][] before = verifyView.getBoardData();
            int[][] after = controller.solveGame(before);

            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (before[r][c] != after[r][c]) {
                        controller.logMove(r, c, before[r][c], after[r][c]);
                    }
                }
            }

            verifyView.refreshBoard(after);

        } catch (InvalidGame e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Solve is only allowed when exactly 5 cells are empty",
                    "Solve Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void undo() {
        int[][] board = verifyView.getBoardData();
        controller.undo(board);
        verifyView.refreshBoard(board);
    }

    public void setSolveEnabled(boolean enabled) {
        solveButton.setEnabled(enabled);
    }
}