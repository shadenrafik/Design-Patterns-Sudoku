package GUI.resume;

import GUI.interfaces.Controllable;
import GUI.solver.SolverView;
import GUI.start.SelectDifficultyView;
import GUI.start.StartGameView;
import GUI.util.GameFrameHelper;
import GUI.verify.VerifyView;

import javax.swing.*;
import java.awt.*;

public class ResumeView extends JFrame {

    private final Controllable controller;
    private final boolean hasUnfinishedGame;

    public ResumeView(Controllable controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;
        boolean[] catalog = controller.getCatalog();
        this.hasUnfinishedGame = catalog[0];
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Sudoku - Resume Game");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.add(createTitle(), BorderLayout.NORTH);
        mainPanel.add(createButtonPanel(), BorderLayout.CENTER);

        add(mainPanel);
    }
    private JPanel createTitle() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Welcome Back!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String msg = hasUnfinishedGame
                ? "You have an unfinished game to complete."
                : "Ready to start a new challenge?";

        JLabel messageLabel = new JLabel(msg);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (hasUnfinishedGame) {
            messageLabel.setForeground(new Color(200, 0, 0));
        }
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(messageLabel);

        return textPanel;
    }
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        JButton resumeButton = new JButton("Resume Game");
        JButton newGameButton = new JButton("New Game");

        resumeButton.setPreferredSize(new Dimension(150, 40));
        newGameButton.setPreferredSize(new Dimension(150, 40));

        resumeButton.setBackground(new Color(76, 175, 80));
        resumeButton.setForeground(Color.WHITE);
        resumeButton.setFocusPainted(false);

        newGameButton.setBackground(new Color(33, 150, 243));
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFocusPainted(false);
        if (hasUnfinishedGame) {
            newGameButton.setEnabled(false);
            newGameButton.setBackground(Color.GRAY);
            newGameButton.setToolTipText("Please finish your current game first!");
        } else {
            resumeButton.setEnabled(false);
            resumeButton.setBackground(Color.GRAY);
        }
        resumeButton.addActionListener(e -> resumeGame());
        newGameButton.addActionListener(e -> startNewGame());

        panel.add(resumeButton);
        panel.add(newGameButton);

        return panel;
    }

    private void resumeGame() {
        try {
            int[][] board = controller.getGame('I');
            GameFrameHelper.openGameFrame(controller, board, this);

            dispose();

        } catch (Exception e) {
            showError("Failed to load unfinished game.");
        }
    }


    private void startNewGame() {
        boolean[] catalog = controller.getCatalog();
        boolean allModesExist = catalog[1]; // Check if E, M, and H all have games

        if (allModesExist) {
            new SelectDifficultyView(controller).setVisible(true);
        } else {
            new StartGameView(controller).setVisible(true);
        }
        dispose();
    }
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
