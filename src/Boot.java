import hu.elte.fi.szakdolgozat.gui.TeamfightTacticsFrame;
import hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic;

import javax.swing.*;

public class Boot {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TeamfightTacticsFrame(new TeamfightTacticsLogic()).setVisible(true));
    }
}