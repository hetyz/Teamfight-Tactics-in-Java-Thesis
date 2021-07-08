package hu.elte.fi.szakdolgozat.gui;

import hu.elte.fi.szakdolgozat.model.map.LoadGame;
import hu.elte.fi.szakdolgozat.model.GameConstants;
import hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic;

import javax.swing.*;
import java.awt.*;

import static hu.elte.fi.szakdolgozat.gui.UIConstants.TITLE;

public class TeamfightTacticsFrame extends JFrame {

    public TeamfightTacticsFrame(final TeamfightTacticsLogic teamfightTacticsLogic) {
        initFrameProperties();

        LoadGame loadGame = new LoadGame(teamfightTacticsLogic);
        teamfightTacticsLogic.setLoadGame(loadGame);
        TeamfightTacticsPanel teamfightTacticsBoard = new TeamfightTacticsPanel(teamfightTacticsLogic, loadGame);
        teamfightTacticsLogic.setMyPanel(teamfightTacticsBoard);

        add(loadGame.getProgressBar());
        add(teamfightTacticsBoard, BorderLayout.CENTER);
        pack();
    }

    private void initFrameProperties() {
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(GameConstants.FRAME_WIDTH, GameConstants.FRAME_HEIGHT);
        setPreferredSize(new Dimension(GameConstants.FRAME_WIDTH, GameConstants.FRAME_HEIGHT));
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
