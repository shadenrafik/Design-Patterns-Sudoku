package GUI.start;

import GUI.interfaces.Controllable;
import GUI.util.GameFrameHelper;
import common.exceptions.InvalidSolutionException;
import common.exceptions.NotFoundException;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SelectDifficultyView extends JFrame {

    private final Controllable controller;

    public SelectDifficultyView(Controllable controller) {
        this.controller = controller;

        setTitle("Select Difficulty");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        panel.setBackground(new Color(240, 240, 240));

        panel.add(createButton("Easy", 'E'));
        panel.add(createButton("Medium", 'M'));
        panel.add(createButton("Hard", 'H'));

        add(panel);
    }

    private JButton createButton(String text, char level) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setFocusPainted(false);

        btn.addActionListener(e -> openGame(level));
        return btn;
    }

    private void openGame(char level) {
        try {
            int[][] board = controller.getGame(level);

            GameFrameHelper.openGameFrame(controller, board, this);

        } catch (NotFoundException e) {
            handleEmptyQueue(level);
        } catch (Exception e) {
            showError("Unexpected error: " + e.getMessage());
        }
    }

    private void handleEmptyQueue(char level) {
        String levelName = switch (level) { case 'E'->"Easy"; case 'M'->"Medium"; case 'H'->"Hard"; default->""; };

        int result = JOptionPane.showConfirmDialog(this,
                "No " + levelName + " games available!\nWould you like to load a source file to generate a new one?",
                "Empty Queue",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                try {
                    controller.driveSingleLevel(file.getAbsolutePath(), level);

                    // 5. Automatically try to open the game again (Recursive call)
                    // This time it will succeed because we just created the file!
                    openGame(level);

                } catch (InvalidSolutionException ex) {
                    showError("Invalid source file: " + ex.getMessage());
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}