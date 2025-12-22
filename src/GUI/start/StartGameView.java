package GUI.start;

import GUI.interfaces.Controllable;
import GUI.resume.ResumeView;
import common.exceptions.InvalidSolutionException;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class StartGameView extends JFrame {

    private final Controllable controller;

    private JTextField pathField;
    private JButton generateButton;
    private JLabel statusLabel;

    public StartGameView(Controllable controller) {
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setTitle("Sudoku - Generate Games");
        setSize(550, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        root.setBackground(new Color(240, 240, 240));

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createFilePicker(), BorderLayout.CENTER);
        root.add(createActions(), BorderLayout.SOUTH);

        add(root);
    }

    private JComponent createHeader() {
        JLabel label = new JLabel(
                "<html><center>" +
                        "<h2>No games found</h2>" +
                        "<p>Select a solved Sudoku file to generate games</p>" +
                        "</center></html>",
                SwingConstants.CENTER
        );
        return label;
    }

    private JComponent createFilePicker() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Solved Sudoku File"));
        panel.setBackground(new Color(240, 240, 240));

        pathField = new JTextField();

        JButton browse = new JButton("Browse");
        browse.addActionListener(e -> chooseFile());

        panel.add(pathField, BorderLayout.CENTER);
        panel.add(browse, BorderLayout.EAST);

        return panel;
    }

    private JComponent createActions() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 240, 240));

        statusLabel = new JLabel(" ", SwingConstants.CENTER);

        generateButton = new JButton("Generate Games");
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.setBackground(new Color(76, 175, 80));
        generateButton.setForeground(Color.WHITE);
        generateButton.addActionListener(e -> generate());

        panel.add(statusLabel, BorderLayout.NORTH);
        panel.add(generateButton, BorderLayout.CENTER);

        return panel;
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            pathField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void generate() {
        String path = pathField.getText().trim();
        if (path.isEmpty()) {
            showError("Please select a file first");
            return;
        }

        File file = new File(path);
        if (!file.exists()) {
            showError("File not found");
            return;
        }

        generateButton.setEnabled(false);
        statusLabel.setText("Generating games...");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    controller.driveGames(path);
                } catch (InvalidSolutionException e) {
                    throw new RuntimeException("Invalid solved board");
                }
                return null;
            }

            @Override
            protected void done() {
                generateButton.setEnabled(true);
                statusLabel.setText("Games generated successfully!");

                Timer t = new Timer(800, e -> goToResume());
                t.setRepeats(false);
                t.start();
            }
        };

        worker.execute();
    }

    private void goToResume() {
        new SelectDifficultyView(controller).setVisible(true);
        dispose();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
