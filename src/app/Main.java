package app;

import GUI.adapter.ViewControllerAdapter;
import GUI.start.StartGameView;
import GUI.resume.ResumeView;
import controller.facade.ControllerFacade;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControllerFacade facade = new ControllerFacade();
            ViewControllerAdapter adapter = new ViewControllerAdapter(facade);
            
            boolean[] catalog = adapter.getCatalog();

            boolean hasGames = false;
            for (boolean exists : catalog) {
                if (exists) {
                    hasGames = true;
                    break;
                }
            }

            if (!hasGames) {
                new StartGameView(adapter).setVisible(true);
            } else {
                new ResumeView(adapter).setVisible(true);
            }
        });
    }
}