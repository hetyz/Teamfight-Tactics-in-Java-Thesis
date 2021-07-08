package hu.elte.fi.szakdolgozat.gui;

import hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic;
import hu.elte.fi.szakdolgozat.model.GameState;
import hu.elte.fi.szakdolgozat.model.GameConstants;
import hu.elte.fi.szakdolgozat.model.FieldState;
import hu.elte.fi.szakdolgozat.gui.sprite.other.Gold;
import hu.elte.fi.szakdolgozat.model.Character;
import hu.elte.fi.szakdolgozat.model.map.Key;
import hu.elte.fi.szakdolgozat.model.map.LoadGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static hu.elte.fi.szakdolgozat.model.GameConstants.*;
import static hu.elte.fi.szakdolgozat.gui.UIConstants.*;
import static hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic.*;

public class TeamfightTacticsPanel extends JPanel {
    private final TeamfightTacticsLogic teamfightTacticsLogic;
    private Character hoverCharacter = null;
    private final LoadGame loadGame;

    private boolean dragAndPull = false;
    private boolean inTheShapes;
    private boolean wasRectangle = true;
    private int imagePrevPtIfFailedX;
    private int imagePrevPtIfFailedY;

    public TeamfightTacticsPanel(final TeamfightTacticsLogic teamfightTacticsLogic, LoadGame loadGame) {
        this.teamfightTacticsLogic = teamfightTacticsLogic;
        this.loadGame = loadGame;

        DragListener dragListener = new DragListener();
        ClickListener clickListener = new ClickListener();
        addMouseListener(clickListener);
        addMouseMotionListener(dragListener);
    }

    private class DragListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent me) {
            if (teamfightTacticsLogic.getGameState() == GameState.MENU_STATE) {
                if (teamfightTacticsLogic.getMenu().getStartGameBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setMenuHovered("start game");
                } else if (teamfightTacticsLogic.getMenu().getOptionsBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setMenuHovered("options");
                } else if (teamfightTacticsLogic.getMenu().getExitBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setMenuHovered("exit");
                } else if (teamfightTacticsLogic.getMenu().getHelpBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setMenuHovered("help");
                } else if (teamfightTacticsLogic.getMenu().getDataBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setMenuHovered("data");
                } else {
                    teamfightTacticsLogic.getMenu().setMenuHovered("");
                }
                repaint();
            } else if (teamfightTacticsLogic.getGameState() == GameState.OPTION_STATE) {
                if (teamfightTacticsLogic.getMenu().getBackBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setMenuHovered("back");
                } else if (teamfightTacticsLogic.getMenu().getOnOffBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setMenuHovered("onoff");
                } else if (teamfightTacticsLogic.getMenu().getResetButton().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setMenuHovered("reset");
                } else {
                    teamfightTacticsLogic.getMenu().setMenuHovered("");
                }
                repaint();
            } else if (teamfightTacticsLogic.getGameState() == GameState.EXIT_STATE) {
                if (teamfightTacticsLogic.getMenu().getYesBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setMenuHovered("yes");
                } else if (teamfightTacticsLogic.getMenu().getNoBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setMenuHovered("no");
                } else {
                    teamfightTacticsLogic.getMenu().setMenuHovered("");
                }
                repaint();
            } else if (teamfightTacticsLogic.getGameState() == GameState.HELP_STATE) {
                if (teamfightTacticsLogic.getMenu().getBackBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setMenuHovered("back");
                } else {
                    teamfightTacticsLogic.getMenu().setMenuHovered("");
                }
                repaint();
            } else if (teamfightTacticsLogic.getGameState() == GameState.DATA_STATE) {
                if (teamfightTacticsLogic.getMenu().getBackBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setMenuHovered("back");
                } else {
                    teamfightTacticsLogic.getMenu().setMenuHovered("");
                }
                repaint();
            } else if (teamfightTacticsLogic.getGameState() == GameState.GAME_STATE) {
                hoverCharacter = null;
                for (Shape s : loadGame.getCardPanelShapeList()
                ) {
                    Key<Integer, Integer> key = new Key<>((int) ((Rectangle2D) s).getX(), (int) ((Rectangle2D) s).getY());
                    if (s.contains(me.getPoint())) {
                        if (loadGame.getCardPanelMap().get(key).equals(CHARACTER_CARD)) {
                            for (Character c : getRandomCardCharacters()
                            ) {
                                if (me.getY() <= c.getY() + 20 && me.getX() <= c.getX() + 20 &&
                                        me.getX() >= c.getX() && me.getY() >= c.getY()) {
                                    hoverCharacter = c;
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            if (teamfightTacticsLogic.getGameState() == GameState.GAME_STATE) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    if (!dragAndPull) return;

                    Point currentPt = me.getPoint();

                    if (teamfightTacticsLogic.getSelectedCharacter() != null) {
                        teamfightTacticsLogic.getSelectedCharacter().setX((int) currentPt.getX() - GameConstants.CHARACTER_SIZE / 2);
                        teamfightTacticsLogic.getSelectedCharacter().setY((int) currentPt.getY() - GameConstants.CHARACTER_SIZE / 2);
                    }
                    repaint();
                }
            }
        }
    }

    private class ClickListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent me) {
            if (teamfightTacticsLogic.getGameState() == GameState.GAME_STATE) {
                if (SwingUtilities.isRightMouseButton(me) && teamfightTacticsLogic.getPlayer().getHealth() > 0) {
                    teamfightTacticsLogic.getPlayer().playerMove(me.getX(), me.getY());
                } else if (SwingUtilities.isLeftMouseButton(me)) {
                    dragAndPull = true;
                    hoverCharacter = null;
                    for (Shape s : loadGame.getCardPanelShapeList()) {
                        if (s.contains(me.getPoint())) {
                            Key<Integer, Integer> key = new Key<>((int) ((Rectangle2D) s).getX(), (int) ((Rectangle2D) s).getY());
                            switch (loadGame.getCardPanelMap().get(key)) {
                                case CHARACTER_CARD -> {
                                    for (int i = 0; i < getRandomCardCharacters().size(); i++) {
                                        if (me.getY() <= getRandomCardCharacters().get(i).getY() + GameConstants.CHARACTER_CARD_SIZE_H &&
                                                me.getX() <= getRandomCardCharacters().get(i).getX() + GameConstants.CHARACTER_CARD_SIZE_W &&
                                                me.getX() >= getRandomCardCharacters().get(i).getX() && me.getY() >= getRandomCardCharacters().get(i).getY()) {
                                            if (teamfightTacticsLogic.getPlayer().getGold() >= getRandomCardCharacters().get(i).getCost() &&
                                                    getAlliancesWaiting().size() < 8) {
                                                teamfightTacticsLogic.getPlayer().setGold(teamfightTacticsLogic.getPlayer().getGold() - getRandomCardCharacters().get(i).getCost());
                                                teamfightTacticsLogic.setSpentGold(teamfightTacticsLogic.getSpentGold() + getRandomCardCharacters().get(i).getCost());

                                                for (int j = 0; j < loadGame.getCharacterWaitingMap().size(); j++) {
                                                    int plusX = 270 + j * 90;

                                                    Key<Integer, Integer> key2 = new Key<>(plusX, 700);
                                                    if (loadGame.getCharacterWaitingMap().get(key2) == FieldState.EMPTY) {
                                                        loadGame.getCharacterWaitingMap().replace(key2, FieldState.OCCUPIED);
                                                        getRandomCardCharacters().get(i).setX(plusX + GameConstants.CHARACTER_SIZE / 2);
                                                        getRandomCardCharacters().get(i).setY(700 + GameConstants.CHARACTER_SIZE / 2);
                                                        getRandomCardCharacters().get(i).setOriginalPosX(plusX);
                                                        getRandomCardCharacters().get(i).setOriginalPosY(700);
                                                        break;
                                                    }
                                                }

                                                getRandomCardCharacters().get(i).getAnimation().start();

                                                getAlliancesWaiting().add(getRandomCardCharacters().get(i));

                                                teamfightTacticsLogic.characterLevelingUp(getRandomCardCharacters().get(i));

                                                getRandomCardCharacters().remove(getRandomCardCharacters().get(i));
                                            }
                                        }
                                    }
                                }
                                case LEVELING_CARD -> {
                                    if (teamfightTacticsLogic.getPlayer().getGold() >= GameConstants.LEVELING_CARD_VALUE) {
                                        teamfightTacticsLogic.getPlayer().setGold(teamfightTacticsLogic.getPlayer().getGold() - GameConstants.LEVELING_CARD_VALUE);
                                        teamfightTacticsLogic.getPlayer().setLevelingLine(teamfightTacticsLogic.getPlayer().getLevelingLine() + GameConstants.LEVELING_LINE_VALUE);
                                        teamfightTacticsLogic.setSpentGold(teamfightTacticsLogic.getSpentGold() + LEVELING_CARD_VALUE);
                                    }
                                }
                                case REFRESH_CARD -> {
                                    if (teamfightTacticsLogic.getPlayer().getGold() >= GameConstants.REFRESH_CARD_VALUE &&
                                            !teamfightTacticsLogic.isCardsLock()) {
                                        teamfightTacticsLogic.getPlayer().setGold(teamfightTacticsLogic.getPlayer().getGold() - GameConstants.REFRESH_CARD_VALUE);
                                        teamfightTacticsLogic.creatingRandomCharacters();
                                        teamfightTacticsLogic.setSpentGold(teamfightTacticsLogic.getSpentGold() + REFRESH_CARD_VALUE);
                                    }
                                }
                            }
                        }
                    }
                    if (loadGame.getCardLockShape().contains(me.getPoint())) {
                        Key<Integer, Integer> key = new Key<>((int) ((Rectangle2D) loadGame.getCardLockShape()).getX(), (int) ((Rectangle2D) loadGame.getCardLockShape()).getY());
                        if (loadGame.getCardPanelMap().get(key).equals(LOCK_CARD)) {
                            teamfightTacticsLogic.setCardsLock(!teamfightTacticsLogic.isCardsLock());
                        }
                    }


                    if (isPlanningTime.get()) {
                        for (Character alliance : getAlliances()) {
                            pressedAlliance(me, alliance);
                        }

                        for (Shape s : loadGame.getCharacterPlayingShapeList()) {
                            if (s.contains(me.getPoint())) {
                                wasRectangle = false;
                                imagePrevPtIfFailedY = (int) ((Ellipse2D) s).getY();
                                imagePrevPtIfFailedX = (int) ((Ellipse2D) s).getX();
                            }
                        }
                    }

                    for (Character character : getAlliancesWaiting()) {
                        pressedAlliance(me, character);
                    }

                    for (Shape s : loadGame.getCharacterWaitingShapeList()) {

                        if (s.contains(me.getPoint())) {
                            wasRectangle = true;
                            imagePrevPtIfFailedY = (int) ((Rectangle2D) s).getY();
                            imagePrevPtIfFailedX = (int) ((Rectangle2D) s).getX();
                        }
                    }
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent me) {
            if (teamfightTacticsLogic.getGameState() == GameState.MENU_STATE) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    if (teamfightTacticsLogic.getMenu().getStartGameBtn().contains(me.getPoint())) {
                        teamfightTacticsLogic.setGameState(GameState.LOADING_STATE);
                        teamfightTacticsLogic.startGame = true;
                        repaint();
                    } else if (teamfightTacticsLogic.getMenu().getOptionsBtn().contains(me.getPoint())) {
                        teamfightTacticsLogic.setGameState(GameState.OPTION_STATE);
                        repaint();
                    } else if (teamfightTacticsLogic.getMenu().getHelpBtn().contains(me.getPoint())) {
                        teamfightTacticsLogic.setGameState(GameState.HELP_STATE);
                        repaint();
                    } else if (teamfightTacticsLogic.getMenu().getExitBtn().contains(me.getPoint())) {
                        teamfightTacticsLogic.setGameState(GameState.EXIT_STATE);
                        repaint();
                    } else if (teamfightTacticsLogic.getMenu().getDataBtn().contains(me.getPoint())) {
                        teamfightTacticsLogic.setGameState(GameState.DATA_STATE);
                        repaint();
                    }
                }
            } else if (teamfightTacticsLogic.getGameState() == GameState.EXIT_STATE) {
                if (teamfightTacticsLogic.getMenu().getYesBtn().contains(me.getPoint())) {
                    System.exit(0);
                } else if (teamfightTacticsLogic.getMenu().getNoBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.setGameState(GameState.MENU_STATE);
                    repaint();
                }
            } else if (teamfightTacticsLogic.getGameState() == GameState.OPTION_STATE) {
                if (teamfightTacticsLogic.getMenu().getBackBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.setGameState(GameState.MENU_STATE);
                    repaint();
                } else if (teamfightTacticsLogic.getMenu().getOnOffBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setFpsOn(!teamfightTacticsLogic.getMenu().isFpsOn());
                } else if (teamfightTacticsLogic.getMenu().getResetButton().contains(me.getPoint())) {
                    teamfightTacticsLogic.getMenu().setDataReseted(true);
                }
            } else if (teamfightTacticsLogic.getGameState() == GameState.HELP_STATE) {
                if (teamfightTacticsLogic.getMenu().getBackBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.setGameState(GameState.MENU_STATE);
                    repaint();
                }
            } else if (teamfightTacticsLogic.getGameState() == GameState.DATA_STATE) {
                if (teamfightTacticsLogic.getMenu().getBackBtn().contains(me.getPoint())) {
                    teamfightTacticsLogic.setGameState(GameState.MENU_STATE);
                    repaint();
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            if (teamfightTacticsLogic.getGameState() == GameState.GAME_STATE) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    dragAndPull = false;
                    inTheShapes = false;

                    for (Shape s : loadGame.getCharacterPlayingShapeList()) {
                        if (s.contains(me.getPoint())) {
                            releasedShapes(s);
                        }
                    }

                    for (Shape s : loadGame.getCharacterWaitingShapeList()) {
                        if (s.contains(me.getPoint())) {
                            releasedShapes(s);
                        }
                    }

                    if (!inTheShapes && teamfightTacticsLogic.getSelectedCharacter() != null
                            && (!getFightingAlliances().contains(teamfightTacticsLogic.getSelectedCharacter())
                            || getAlliancesWaiting().contains(teamfightTacticsLogic.getSelectedCharacter()))) {
                        teamfightTacticsLogic.getSelectedCharacter().setX(imagePrevPtIfFailedX + GameConstants.CHARACTER_SIZE / 2);
                        teamfightTacticsLogic.getSelectedCharacter().setY(imagePrevPtIfFailedY + GameConstants.CHARACTER_SIZE / 2);
                    }

                    if (loadGame.getCardSellShape().contains(me.getPoint()) && teamfightTacticsLogic.getSelectedCharacter() != null) {
                        Key<Integer, Integer> key = new Key<>((int) ((Rectangle2D) loadGame.getCardSellShape()).getX(), (int) ((Rectangle2D) loadGame.getCardSellShape()).getY());
                        if (loadGame.getCardPanelMap().get(key).equals(SELL_CARD)) {
                            if (getAlliances().contains(teamfightTacticsLogic.getSelectedCharacter())) {
                                Key<Integer, Integer> key2 = new Key<>(imagePrevPtIfFailedX, imagePrevPtIfFailedY);
                                loadGame.getCharacterPlayingMap().replace(key2, FieldState.EMPTY);
                                teamfightTacticsLogic.getPlayer().setGold(teamfightTacticsLogic.getPlayer().getGold() + teamfightTacticsLogic.getSelectedCharacter().getCost());
                                getAlliances().remove(teamfightTacticsLogic.getSelectedCharacter());
                            } else if (getAlliancesWaiting().contains(teamfightTacticsLogic.getSelectedCharacter())) {
                                Key<Integer, Integer> key2 = new Key<>(imagePrevPtIfFailedX, imagePrevPtIfFailedY);
                                loadGame.getCharacterWaitingMap().replace(key2, FieldState.EMPTY);
                                teamfightTacticsLogic.getPlayer().setGold(teamfightTacticsLogic.getPlayer().getGold() + teamfightTacticsLogic.getSelectedCharacter().getCost());
                                getAlliancesWaiting().remove(teamfightTacticsLogic.getSelectedCharacter());
                            }
                        }
                    }

                    teamfightTacticsLogic.setSelectedCharacter(null);
                    teamfightTacticsLogic.setSwapSpotCharacter(null);
                }
            }
        }
    }

    private void releasedShapes(Shape s) {
        if (!dragAndPull && teamfightTacticsLogic.getSelectedCharacter() != null) {
            if (s instanceof Ellipse2D && isPlanningTime.get()) {
                releasedShapesEllipse2D(s);
            } else if (s instanceof Rectangle2D) {
                releasedShapesRectangle2D(s);
            }
        }
    }

    private void pressedAlliance(MouseEvent me, Character c) {
        if (me.getY() <= c.getY() + GameConstants.CHARACTER_SIZE && me.getX() <= c.getX() + GameConstants.CHARACTER_SIZE &&
                me.getX() >= c.getX() && me.getY() >= c.getY()) {
            teamfightTacticsLogic.setSelectedCharacter(c);
            teamfightTacticsLogic.setSellCost(c.getCost() * c.getLevel());
            imagePrevPtIfFailedY = teamfightTacticsLogic.getSelectedCharacter().getY();
            imagePrevPtIfFailedX = teamfightTacticsLogic.getSelectedCharacter().getX();
            teamfightTacticsLogic.getSelectedCharacter().setOriginalPosX(teamfightTacticsLogic.getSelectedCharacter().getX());
            teamfightTacticsLogic.getSelectedCharacter().setOriginalPosY(teamfightTacticsLogic.getSelectedCharacter().getY());
        }
    }

    private void releasedShapesEllipse2D(Shape s) {
        inTheShapes = true;
        Key<Integer, Integer> key = new Key<>((int) ((Ellipse2D) s).getX(), (int) ((Ellipse2D) s).getY());
        Key<Integer, Integer> key2 = new Key<>(imagePrevPtIfFailedX, imagePrevPtIfFailedY);

        if (loadGame.getCharacterPlayingMap().get(key) == FieldState.EMPTY && teamfightTacticsLogic.getPlayer().getPlayableCharacterNum() == getAlliances().size()) {
            if (!wasRectangle) {
                loadGame.getCharacterPlayingMap().replace(key, FieldState.OCCUPIED);
                loadGame.getCharacterPlayingMap().replace(key2, FieldState.EMPTY);
                teamfightTacticsLogic.getSelectedCharacter().setX((int) ((Ellipse2D) s).getX() + GameConstants.CHARACTER_SIZE / 2);
                teamfightTacticsLogic.getSelectedCharacter().setY((int) ((Ellipse2D) s).getY() + GameConstants.CHARACTER_SIZE / 2);
                teamfightTacticsLogic.getSelectedCharacter().setOriginalPos();
            } else {
                teamfightTacticsLogic.getSelectedCharacter().setX(imagePrevPtIfFailedX + GameConstants.CHARACTER_SIZE / 2);
                teamfightTacticsLogic.getSelectedCharacter().setY(imagePrevPtIfFailedY + GameConstants.CHARACTER_SIZE / 2);
            }
        } else if (loadGame.getCharacterPlayingMap().get(key) == FieldState.EMPTY && teamfightTacticsLogic.getPlayer().getPlayableCharacterNum() >= getAlliances().size() + 1) {
            if (wasRectangle) {
                getAlliances().add(teamfightTacticsLogic.getSelectedCharacter());
                getAlliancesWaiting().remove(teamfightTacticsLogic.getSelectedCharacter());
                loadGame.getCharacterWaitingMap().replace(key2, FieldState.EMPTY);
                loadGame.getCharacterPlayingMap().replace(key, FieldState.OCCUPIED);
            } else {
                loadGame.getCharacterPlayingMap().replace(key, FieldState.OCCUPIED);
                loadGame.getCharacterPlayingMap().replace(key2, FieldState.EMPTY);
            }

            teamfightTacticsLogic.getSelectedCharacter().setX((int) ((Ellipse2D) s).getX() + GameConstants.CHARACTER_SIZE / 2);
            teamfightTacticsLogic.getSelectedCharacter().setY((int) ((Ellipse2D) s).getY() + GameConstants.CHARACTER_SIZE / 2);
            teamfightTacticsLogic.getSelectedCharacter().setOriginalPos();
        } else if (!key.equals(key2)) {
            swapCharacterSpots(s);
        } else if (key.equals(key2)) {
            teamfightTacticsLogic.getSelectedCharacter().setX((int) ((Ellipse2D) s).getX() + GameConstants.CHARACTER_SIZE / 2);
            teamfightTacticsLogic.getSelectedCharacter().setY((int) ((Ellipse2D) s).getY() + GameConstants.CHARACTER_SIZE / 2);
        }
    }

    private void releasedShapesRectangle2D(Shape s) {
        inTheShapes = true;
        Key<Integer, Integer> key = new Key<>((int) ((Rectangle2D) s).getX(), (int) ((Rectangle2D) s).getY());
        Key<Integer, Integer> key2 = new Key<>(imagePrevPtIfFailedX, imagePrevPtIfFailedY);

        if (loadGame.getCharacterWaitingMap().get(key) == FieldState.EMPTY) {

            if (wasRectangle) {
                loadGame.getCharacterWaitingMap().replace(key2, FieldState.EMPTY);
                loadGame.getCharacterWaitingMap().replace(key, FieldState.OCCUPIED);
                teamfightTacticsLogic.getSelectedCharacter().setX((int) ((Rectangle2D) s).getX() + GameConstants.CHARACTER_SIZE / 2);
                teamfightTacticsLogic.getSelectedCharacter().setY((int) ((Rectangle2D) s).getY() + GameConstants.CHARACTER_SIZE / 2);
            } else if (getAlliancesWaiting().size() + 1 <= getAlliances().size() + getAlliancesWaiting().size() && isPlanningTime.get()) {
                getAlliances().remove(teamfightTacticsLogic.getSelectedCharacter());
                getAlliancesWaiting().add(teamfightTacticsLogic.getSelectedCharacter());
                loadGame.getCharacterPlayingMap().replace(key2, FieldState.EMPTY);
                loadGame.getCharacterWaitingMap().replace(key, FieldState.OCCUPIED);
                teamfightTacticsLogic.getSelectedCharacter().setX((int) ((Rectangle2D) s).getX() + GameConstants.CHARACTER_SIZE / 2);
                teamfightTacticsLogic.getSelectedCharacter().setY((int) ((Rectangle2D) s).getY() + GameConstants.CHARACTER_SIZE / 2);
            } else {
                teamfightTacticsLogic.getSelectedCharacter().setX(imagePrevPtIfFailedX + GameConstants.CHARACTER_SIZE / 2);
                teamfightTacticsLogic.getSelectedCharacter().setY(imagePrevPtIfFailedY + GameConstants.CHARACTER_SIZE / 2);
            }
        } else if (!key.equals(key2)) {
            swapCharacterSpots(s);
        } else if (key.equals(key2)) {
            teamfightTacticsLogic.getSelectedCharacter().setX((int) ((Rectangle2D) s).getX() + GameConstants.CHARACTER_SIZE / 2);
            teamfightTacticsLogic.getSelectedCharacter().setY((int) ((Rectangle2D) s).getY() + GameConstants.CHARACTER_SIZE / 2);
        }
    }

    private void swapCharacterSpots(Shape s) {
        {
            int k = 0;
            while (k < getAlliancesWaiting().size()) {
                swapCharacterLists(s, getAlliancesWaiting().get(k));
                k++;
            }
        }
        if (isPlanningTime.get()) {
            int k = 0;
            while (k < getAlliances().size()) {
                swapCharacterLists(s, getAlliances().get(k));
                k++;
            }
        }
    }


    private void swapCharacterLists(Shape s, Character c) {
        if (s.contains(c.getX(), c.getY()) && teamfightTacticsLogic.getSelectedCharacter() != c) {
            teamfightTacticsLogic.setSwapSpotCharacter(c);

            if (isPlanningTime.get()) {
                if (getAlliancesWaiting().contains(teamfightTacticsLogic.getSwapSpotCharacter()) && getAlliances().contains(teamfightTacticsLogic.getSelectedCharacter())) {
                    getAlliancesWaiting().add(teamfightTacticsLogic.getSelectedCharacter());
                    getAlliances().add(teamfightTacticsLogic.getSwapSpotCharacter());
                    getAlliancesWaiting().remove(teamfightTacticsLogic.getSwapSpotCharacter());
                    getAlliances().remove(teamfightTacticsLogic.getSelectedCharacter());
                } else if (getAlliancesWaiting().contains(teamfightTacticsLogic.getSelectedCharacter())
                        && getAlliances().contains(teamfightTacticsLogic.getSwapSpotCharacter())) {
                    getAlliances().remove(teamfightTacticsLogic.getSwapSpotCharacter());
                    getAlliancesWaiting().add(teamfightTacticsLogic.getSwapSpotCharacter());
                    getAlliances().add(teamfightTacticsLogic.getSelectedCharacter());
                    getAlliancesWaiting().remove(teamfightTacticsLogic.getSelectedCharacter());
                }

                teamfightTacticsLogic.getSelectedCharacter().setX(teamfightTacticsLogic.getSwapSpotCharacter().getX());
                teamfightTacticsLogic.getSelectedCharacter().setY(teamfightTacticsLogic.getSwapSpotCharacter().getY());

                teamfightTacticsLogic.getSwapSpotCharacter().setX(imagePrevPtIfFailedX + GameConstants.CHARACTER_SIZE / 2);
                teamfightTacticsLogic.getSwapSpotCharacter().setY(imagePrevPtIfFailedY + GameConstants.CHARACTER_SIZE / 2);

                teamfightTacticsLogic.getSwapSpotCharacter().setOriginalPos();
                teamfightTacticsLogic.getSelectedCharacter().setOriginalPos();
            } else if (getAlliancesWaiting().contains(teamfightTacticsLogic.getSelectedCharacter())
                    && getAlliancesWaiting().contains(teamfightTacticsLogic.getSwapSpotCharacter())) {
                teamfightTacticsLogic.getSelectedCharacter().setX(teamfightTacticsLogic.getSwapSpotCharacter().getX());
                teamfightTacticsLogic.getSelectedCharacter().setY(teamfightTacticsLogic.getSwapSpotCharacter().getY());

                teamfightTacticsLogic.getSwapSpotCharacter().setX(imagePrevPtIfFailedX + GameConstants.CHARACTER_SIZE / 2);
                teamfightTacticsLogic.getSwapSpotCharacter().setY(imagePrevPtIfFailedY + GameConstants.CHARACTER_SIZE / 2);

                teamfightTacticsLogic.getSwapSpotCharacter().setOriginalPosX(teamfightTacticsLogic.getSwapSpotCharacter().getX() - 20);
                teamfightTacticsLogic.getSwapSpotCharacter().setOriginalPosY(teamfightTacticsLogic.getSwapSpotCharacter().getY() - 20);
                teamfightTacticsLogic.getSelectedCharacter().setOriginalPosX(teamfightTacticsLogic.getSelectedCharacter().getX() - 20);
                teamfightTacticsLogic.getSelectedCharacter().setOriginalPosY(teamfightTacticsLogic.getSelectedCharacter().getY() - 20);
            }
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.setFont(standardFont);

        if (teamfightTacticsLogic.getGameState() == GameState.GAME_STATE) {

            if (loadGame.getBackgroundImage() != null) {
                g2d.drawImage(loadGame.getBackgroundImage(), 0, 0, FRAME_WIDTH, FRAME_HEIGHT, this);
            }

            drawTexture(g);

            if (dragAndPull && teamfightTacticsLogic.getSelectedCharacter() != null && isPlanningTime.get()) {
                for (Shape s : loadGame.getCharacterPlayingShapeList()) {
                    g.setColor(Color.BLUE);
                    g2d.draw(s);
                }
            }

            if (teamfightTacticsLogic.getSelectedCharacter() == null) {
                if (loadGame.getCardPanelImage() != null) {
                    g2d.drawImage(loadGame.getCardPanelImage(), 0, 0, FRAME_WIDTH, FRAME_HEIGHT, this);
                }

                for (Shape s : loadGame.getCardPanelShapeList()) {
                    Key<Integer, Integer> key = new Key<>((int) ((Rectangle2D) s).getX(), (int) ((Rectangle2D) s).getY());
                    g2d.setStroke(new BasicStroke(1));
                    switch (loadGame.getCardPanelMap().get(key)) {
                        case LEVELING_CARD -> drawLevelingCard(g, s);
                        case REFRESH_CARD -> drawRefreshCard(g, s);
                        case CHARACTER_CARD -> drawCharacterCard(g);
                    }
                    g2d.draw(s);
                }
            } else if (!getFightingAlliances().contains(teamfightTacticsLogic.getSelectedCharacter())) {
                if (loadGame.getCardPanelSellImage() != null) {
                    g2d.drawImage(loadGame.getCardPanelSellImage(), 0, 0, FRAME_WIDTH, FRAME_HEIGHT, this);
                }
                g.setFont(sellFont);
                g.setColor(Color.black);
                String sellFor = "Sell for " + teamfightTacticsLogic.getSellCost() + "g";
                g.drawString(sellFor, 580, 890);
            }
            g.setFont(standardFont);
            g.drawString(String.valueOf(teamfightTacticsLogic.getPlayer().getGold()), loadGame.getCardPlayerGoldPolygon().xpoints[0] + 30, loadGame.getCardPlayerGoldPolygon().ypoints[0] - 5);

            for (Gold gold : getDroppedGold()
            ) {
                gold.draw(g);
            }

            teamfightTacticsLogic.getPlayer().drawPlayer(g);

            if (isPlanningTime.get()) {
                drawWaiting(g);
            } else if (!isOver.get()) {
                drawFight(g);
            }

            for (Shape s : loadGame.getCharacterWaitingShapeList()) {
                if (dragAndPull && teamfightTacticsLogic.getSelectedCharacter() != null
                        && !getFightingAlliances().contains(teamfightTacticsLogic.getSelectedCharacter())) {
                    g.setColor(Color.BLUE);
                }
                g2d.draw(s);
            }

            g.setColor(Color.black);

            if (hoverCharacter != null) {
                drawInfoPanel(g);
            }

            if (teamfightTacticsLogic.isCardsLock()) {
                g.drawImage(lockOn, (int) loadGame.getCardLockShape().getBounds().getX() + 13, (int) loadGame.getCardLockShape().getBounds().getY() + 3, this);
            } else {
                g.drawImage(lockOff, (int) loadGame.getCardLockShape().getBounds().getX() + 13, (int) loadGame.getCardLockShape().getBounds().getY() + 3, this);
            }

            for (int i = 0; i < getAlliancesWaiting().size(); i++) {
                getAlliancesWaiting().get(i).draw(g);
            }


            if (teamfightTacticsLogic.getPlayer().getHealth() <= 0) {
                drawGameOver(g);
            }
        } else {
            teamfightTacticsLogic.getMenu().draw(g);
        }

        if (teamfightTacticsLogic.getMenu().isFpsOn()) {
            g.setFont(standardFont);
            g.setColor(Color.black);
            g.drawString(String.format("Fps: %d", teamfightTacticsLogic.getFramePerSecond()), 10, 20);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawWaiting(Graphics g) {
        if (teamfightTacticsLogic.getPlanningClock() != null) {
            String timer = String.format("Planning time: %d", teamfightTacticsLogic.getPlanningClock().elapsedTime());
            g.drawString(getAlliances().size() + "/" + teamfightTacticsLogic.getPlayer().getPlayableCharacterNum(), 620, 125);
            g.drawString(timer, 580, 100);
        }
        for (int i = 0; i < getAlliances().size(); i++) {
            getAlliances().get(i).draw(g);
        }
    }

    private void drawFight(Graphics g) {
        if (teamfightTacticsLogic.getFightClock() != null) {
            String timer = String.format("Fight time: %d", teamfightTacticsLogic.getFightClock().elapsedTime());
            g.drawString(timer, 590, 100);
        }
        for (int i = 0; i < getFightingAlliances().size(); i++) {
            getFightingAlliances().get(i).draw(g);
        }
        for (int i = 0; i < getFightingEnemies().size(); i++) {
            getFightingEnemies().get(i).draw(g);
        }
        for (int i = 0; i < getCharactersBullet().size(); i++) {
            getCharactersBullet().get(i).draw(g);
            getCharactersBullet().get(i).update();
        }
    }

    private void drawGameOver(Graphics g) {
        teamfightTacticsLogic.getMenu().getTitleFont().deriveFont(50f);
        FontMetrics metrics = g.getFontMetrics(teamfightTacticsLogic.getMenu().getTitleFont());
        g.setFont(teamfightTacticsLogic.getMenu().getTitleFont());
        g.drawString(GAME_OVER, (GameConstants.FRAME_WIDTH - metrics.stringWidth(GAME_OVER)) / 2, 350);
    }

    private void drawInfoPanel(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(infoPanel, hoverCharacter.getX() + 1, hoverCharacter.getY() + 1, this);
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(Font.PLAIN));
        g.drawString("i", hoverCharacter.getX() + 10, hoverCharacter.getY() + 15);
        g.setFont(g.getFont().deriveFont(Font.BOLD));
        g2d.drawString("Character info", hoverCharacter.getX() + 25, hoverCharacter.getY() + 15);
        g2d.drawString("Name: " + hoverCharacter.getName(), hoverCharacter.getX() + 5, hoverCharacter.getY() + 35);
        g2d.drawString("Health: " + hoverCharacter.getMaxHealth(), hoverCharacter.getX() + 5, hoverCharacter.getY() + 50);
        g2d.drawString("Damage-low: " + hoverCharacter.getDamageThresholdLower(), hoverCharacter.getX() + 5, hoverCharacter.getY() + 65);
        g2d.drawString("Damage-up: " + hoverCharacter.getDamageThresholdUpper(), hoverCharacter.getX() + 5, hoverCharacter.getY() + 80);
        g2d.drawString("Range: " + hoverCharacter.getRange(), hoverCharacter.getX() + 5, hoverCharacter.getY() + 95);
        g2d.setStroke(new BasicStroke(0));
    }

    private void drawCharacterCard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.black);
        if (!getRandomCardCharacters().isEmpty()) {
            ArrayList<Character> randomCharacters = new ArrayList<>(getRandomCardCharacters());
            for (Character randomCharacter : randomCharacters) {
                g2d.drawImage(randomCharacter.getImage(),
                        randomCharacter.getX(),
                        randomCharacter.getY(),
                        GameConstants.CHARACTER_CARD_SIZE_W,
                        GameConstants.CHARACTER_CARD_SIZE_H - GameConstants.CHARACTER_CARD_NAME_PLATES_SIZE,
                        this);

                float frameThickness = 2;
                g2d.setStroke(new BasicStroke(frameThickness));
                g.setColor(hoverAndTitleColor);
                g.fillRect(randomCharacter.getX() + 2, randomCharacter.getY() + 2, 18, 17);
                g.setColor(frameColor);
                g.drawRect(randomCharacter.getX() + 2, randomCharacter.getY() + 2, 19, 18);
                g.setColor(Color.BLACK);

                g.setFont(g.getFont().deriveFont(Font.PLAIN));
                g.drawString("i", randomCharacter.getX() + 10, randomCharacter.getY() + 15);
                g2d.setStroke(new BasicStroke(0));
                g.setFont(g.getFont().deriveFont(Font.BOLD));

                g.setColor(Color.ORANGE);

                g.fillRect(randomCharacter.getX() + 1, randomCharacter.getY() + CHARACTER_CARD_SIZE_H - CHARACTER_CARD_NAME_PLATES_SIZE, 119, 19);
                g.setColor(Color.BLACK);
                g.drawString(randomCharacter.getName(),
                        randomCharacter.getX() + 5,
                        randomCharacter.getY() + GameConstants.CHARACTER_CARD_SIZE_H - 5);
                g.drawString(String.valueOf(randomCharacter.getCost()),
                        randomCharacter.getX() + GameConstants.CHARACTER_CARD_SIZE_W - 10,
                        randomCharacter.getY() + GameConstants.CHARACTER_CARD_SIZE_H - 5);

                g.drawRect(randomCharacter.getX(), randomCharacter.getY(), 120, 85);
            }
        }
    }

    private void drawRefreshCard(Graphics g, Shape s) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g.drawString("Refresh",
                (int) ((Rectangle2D) s).getX() + 8,
                (int) ((Rectangle2D) s).getY() + 15);
        g.drawString(String.valueOf(GameConstants.REFRESH_CARD_VALUE),
                (int) ((Rectangle2D) s).getX() + 15,
                (int) ((Rectangle2D) s).getY() + 30);
        if (teamfightTacticsLogic.isCardsLock()) {
            g.setColor(frameColor);
            float frameThickness = 2;
            g2d.setStroke(new BasicStroke(frameThickness));
            g.drawLine((int) ((Rectangle2D) s).getX(), (int) ((Rectangle2D) s).getY(), (int) ((Rectangle2D) s).getMaxX(), (int) ((Rectangle2D) s).getMaxY());
            g.drawLine((int) ((Rectangle2D) s).getMaxX(), (int) ((Rectangle2D) s).getY(), (int) ((Rectangle2D) s).getX(), (int) ((Rectangle2D) s).getMaxY());
        }
    }

    private void drawLevelingCard(Graphics g, Shape s) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g2d.drawRect((int) ((Rectangle2D) s).getX() + 40, (int) ((Rectangle2D) s).getMaxY() - 12, 60, 5);
        g.setColor(levelColor);
        g2d.fillRect((int) ((Rectangle2D) s).getX() + 41, (int) ((Rectangle2D) s).getMaxY() - 11, (60 * teamfightTacticsLogic.getPlayer().getLevelingLine()) / teamfightTacticsLogic.getPlayer().getLevelingLineCap(), 4);
        g.setColor(Color.BLACK);
        g.drawString("Buy XP",
                (int) ((Rectangle2D) s).getX() + 8,
                (int) ((Rectangle2D) s).getY() + 15);
        g.drawString(String.valueOf(GameConstants.LEVELING_CARD_VALUE),
                (int) ((Rectangle2D) s).getX() + 12,
                (int) ((Rectangle2D) s).getY() + 29);
        String levelingLinePerLevelingCap = teamfightTacticsLogic.getPlayer().getLevelingLine() + "/" + teamfightTacticsLogic.getPlayer().getLevelingLineCap();
        g.drawString(levelingLinePerLevelingCap,
                (int) ((Rectangle2D) s).getX() + 8,
                (int) ((Rectangle2D) s).getMaxY() - 5);
        g.setColor(levelColor);
        g.fillRect((int) ((Rectangle2D) s).getMaxX() - 19, (int) ((Rectangle2D) s).getMaxY() - 15, 11, 11);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(teamfightTacticsLogic.getPlayer().getLevel()),
                (int) (((Rectangle2D) s).getMaxX() - 17),
                (int) ((Rectangle2D) s).getMaxY() - 5);
        g.drawRect((int) ((Rectangle2D) s).getMaxX() - 20, (int) ((Rectangle2D) s).getMaxY() - 16, 12, 12);
    }

    private void drawTexture(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(loadGame.getWaterFountainAnimation().getSprite(), 1110, 400, loadGame.getWaterFountainAnimation().getSprite().getWidth(), loadGame.getWaterFountainAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getTreeAnimation().getSprite(), 900, 10, loadGame.getTreeAnimation().getSprite().getWidth(), loadGame.getTreeAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getTreeAnimation().getSprite(), 100, 670, loadGame.getTreeAnimation().getSprite().getWidth(), loadGame.getTreeAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getTreeAnimation().getSprite(), 1050, 600, loadGame.getTreeAnimation().getSprite().getWidth(), loadGame.getTreeAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getTreeAnimation().getSprite(), 10, 170, loadGame.getTreeAnimation().getSprite().getWidth(), loadGame.getTreeAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getTreeAnimation().getSprite(), 10, 470, loadGame.getTreeAnimation().getSprite().getWidth(), loadGame.getTreeAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getTreeAnimation().getSprite(), 200, 300, loadGame.getTreeAnimation().getSprite().getWidth(), loadGame.getTreeAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getTreeAnimation().getSprite(), 70, 800, loadGame.getTreeAnimation().getSprite().getWidth(), loadGame.getTreeAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getFishingMushroom().getSprite(), 1180, 805, loadGame.getFishingMushroom().getSprite().getWidth(), loadGame.getFishingMushroom().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getTreeAnimation().getSprite(), 1100, 700, loadGame.getTreeAnimation().getSprite().getWidth(), loadGame.getTreeAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getFlameAnimation().getSprite(), 230, 120, loadGame.getFlameAnimation().getSprite().getWidth(), loadGame.getFlameAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getFlameAnimation().getSprite(), 220, 670, loadGame.getFlameAnimation().getSprite().getWidth(), loadGame.getFlameAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getFlameAnimation().getSprite(), 990, 120, loadGame.getFlameAnimation().getSprite().getWidth(), loadGame.getFlameAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getFlameAnimation().getSprite(), 1000, 670, loadGame.getFlameAnimation().getSprite().getWidth(), loadGame.getFlameAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getDogAnimation().getSprite(), 120, 140, loadGame.getDogAnimation().getSprite().getWidth(), loadGame.getDogAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getMushroomAnimation().getSprite(), 134, 598, loadGame.getMushroomAnimation().getSprite().getWidth(), loadGame.getMushroomAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getMushroomAnimation().getSprite(), 1018, 324, loadGame.getMushroomAnimation().getSprite().getWidth(), loadGame.getMushroomAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getMushroomRopesAnimation().getSprite(), 100, 350, loadGame.getMushroomRopesAnimation().getSprite().getWidth(), loadGame.getMushroomRopesAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getHouseSmokeAnimation().getSprite(), 1060, -10, loadGame.getHouseSmokeAnimation().getSprite().getWidth(), loadGame.getHouseSmokeAnimation().getSprite().getHeight(), this);
        g2d.drawImage(loadGame.getShrineAnimation().getSprite(), 1040, 700, loadGame.getShrineAnimation().getSprite().getWidth(), loadGame.getShrineAnimation().getSprite().getHeight(), this);
    }
}
