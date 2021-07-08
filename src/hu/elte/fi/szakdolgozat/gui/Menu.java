package hu.elte.fi.szakdolgozat.gui;

import hu.elte.fi.szakdolgozat.gui.sprite.SpriteSheet;
import hu.elte.fi.szakdolgozat.model.GameConstants;
import hu.elte.fi.szakdolgozat.model.animation.Animation;
import hu.elte.fi.szakdolgozat.model.GameState;
import hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic;
import hu.elte.fi.szakdolgozat.resources.ResourceLoader;

import static hu.elte.fi.szakdolgozat.gui.UIConstants.*;
import static hu.elte.fi.szakdolgozat.model.GameConstants.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu {

    private final Animation menuAnimation;
    private final TeamfightTacticsLogic teamfightTacticsLogic;
    private Font titleFont;
    private Font menuFont;

    private final int halfOfTheFrame = FRAME_WIDTH / 2;

    private final Rectangle startGameBtn = new Rectangle(halfOfTheFrame - 75, 420, 150, 50);
    private final Rectangle optionsBtn = new Rectangle(halfOfTheFrame - 75, 480, 150, 50);
    private final Rectangle helpBtn = new Rectangle(halfOfTheFrame - 75, 540, 150, 50);
    private final Rectangle exitBtn = new Rectangle(halfOfTheFrame - 75, 660, 150, 50);
    private final Rectangle yesBtn = new Rectangle(halfOfTheFrame - 155, 575, 150, 50);
    private final Rectangle noBtn = new Rectangle(halfOfTheFrame + 5, 575, 150, 50);
    private final Rectangle dataBtn = new Rectangle(halfOfTheFrame - 75, 600, 150, 50);
    private final Rectangle backBtn = new Rectangle(halfOfTheFrame - 75, 660, 150, 50);
    private final Ellipse2D.Double onOffBtn = new Ellipse2D.Double(halfOfTheFrame + 80, 450, 30,30);
    private final Ellipse2D.Double resetButton = new Ellipse2D.Double(halfOfTheFrame + 80, 500, 30,30);

    private boolean fpsOn = false;
    private boolean dataReseted = false;
    private String menuHovered = "";

    public boolean isDataReseted() {
        return dataReseted;
    }

    public boolean isFpsOn() {
        return fpsOn;
    }

    public void setFpsOn(boolean fpsOn) {
        this.fpsOn = fpsOn;
    }

    public void setDataReseted(boolean dataReseted) {
        this.dataReseted = dataReseted;
    }

    public Ellipse2D.Double getOnOffBtn() {
        return onOffBtn;
    }

    public Ellipse2D.Double getResetButton() {
        return resetButton;
    }

    public Rectangle getYesBtn() {
        return yesBtn;
    }

    public Rectangle getNoBtn() {
        return noBtn;
    }

    public Animation getMenuAnimation() {
        return menuAnimation;
    }

    public void setMenuHovered(String menuHovered) {
        this.menuHovered = menuHovered;
    }

    public Rectangle getStartGameBtn() {
        return startGameBtn;
    }

    public Rectangle getOptionsBtn() {
        return optionsBtn;
    }

    public Rectangle getExitBtn() {
        return exitBtn;
    }

    public Rectangle getBackBtn() {
        return backBtn;
    }

    public Rectangle getHelpBtn() {
        return helpBtn;
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public Rectangle getDataBtn() {
        return dataBtn;
    }

    public Menu(final TeamfightTacticsLogic teamfightTacticsLogic) {
        this.teamfightTacticsLogic = teamfightTacticsLogic;

        try {

            File file = new File("Humongous-of-Eternity-St.ttf");

            titleFont = Font.createFont(Font.TRUETYPE_FONT,
                    file).deriveFont(Font.BOLD, 20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(titleFont);

            menuFont = Font.createFont(Font.TRUETYPE_FONT,
                    file).deriveFont(Font.BOLD, 18f);
            GraphicsEnvironment ge2 = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge2.registerFont(titleFont);

        } catch (IOException | FontFormatException e) {
            System.out.println(e.getMessage());
        }

        if (titleFont == null) {
            titleFont = new Font("arial", Font.BOLD, 25);
        }
        if (menuFont == null) {
            menuFont = new Font("arial", Font.BOLD, 25);
        }

        BufferedImage menu = ResourceLoader.readImage("images/textures/menu/menu.png");
        SpriteSheet menuSpriteSheet = new SpriteSheet(menu);
        BufferedImage[] menuAnimationImages = {
                menuSpriteSheet.grabImage(1, 1, 480, 270),
                menuSpriteSheet.grabImage(2, 1, 480, 270),
                menuSpriteSheet.grabImage(3, 1, 480, 270),
                menuSpriteSheet.grabImage(4, 1, 480, 270),
                menuSpriteSheet.grabImage(5, 1, 480, 270),
                menuSpriteSheet.grabImage(6, 1, 480, 270),
                menuSpriteSheet.grabImage(7, 1, 480, 270),
                menuSpriteSheet.grabImage(8, 1, 480, 270),
                menuSpriteSheet.grabImage(9, 1, 480, 270),
                menuSpriteSheet.grabImage(10, 1, 480, 270),
        };

        menuAnimation = new Animation(menuAnimationImages, 6);
        menuAnimation.start();
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        FontMetrics metrics = g.getFontMetrics(titleFont);

        g2d.drawImage(menuAnimation.getSprite(), 0, 0, FRAME_WIDTH, FRAME_HEIGHT, null);

        g.setFont(titleFont);
        float frameThickness = 5;
        g2d.setStroke(new BasicStroke(frameThickness));
        if (teamfightTacticsLogic.getGameState() == GameState.MENU_STATE) {
            g.setColor(hoverAndTitleColor);
            g.drawString(TITLE, (FRAME_WIDTH - metrics.stringWidth(TITLE)) / 2, 160);
            g.setFont(menuFont);
            g.setColor(Color.black);
            drawCenteredString(g, "Start Game", startGameBtn, menuFont);
            if (menuHovered.equals("start game")) {
                g.setColor(hoverAndTitleColor);
            } else {
                g.setColor(frameColor);
            }
            g2d.draw(startGameBtn);
            g.setColor(Color.black);
            drawCenteredString(g, "Options", optionsBtn, menuFont);
            if (menuHovered.equals("options")) {
                g.setColor(hoverAndTitleColor);
            } else {
                g.setColor(frameColor);
            }
            g2d.draw(optionsBtn);
            g.setColor(Color.black);
            drawCenteredString(g, "Help", helpBtn, menuFont);
            if (menuHovered.equals("help")) {
                g.setColor(hoverAndTitleColor);
            } else {
                g.setColor(frameColor);
            }
            g2d.draw(helpBtn);
            g.setColor(Color.black);
            drawCenteredString(g, "Data", dataBtn, menuFont);
            if (menuHovered.equals("data")) {
                g.setColor(hoverAndTitleColor);
            } else {
                g.setColor(frameColor);
            }
            g2d.draw(dataBtn);
            g.setColor(Color.black);
            drawCenteredString(g, "Exit", exitBtn, menuFont);
            if (menuHovered.equals("exit")) {
                g.setColor(hoverAndTitleColor);
            } else {
                g.setColor(frameColor);
            }
            g2d.draw(exitBtn);
            g.setColor(Color.black);
        } else if (teamfightTacticsLogic.getGameState() == GameState.EXIT_STATE) {
            g.setColor(hoverAndTitleColor);
            g.drawString(EXIT, (FRAME_WIDTH - metrics.stringWidth(EXIT)) / 2, 160);
            g.setFont(menuFont);
            g.setColor(Color.black);

            drawString(g, EXIT_QUESTION,
                    (FRAME_WIDTH - metrics.stringWidth(EXIT_QUESTION) / 2) / 2, 475);
            drawCenteredString(g, "Yes", yesBtn, menuFont);
            if (menuHovered.equals("yes")) {
                g.setColor(hoverAndTitleColor);
            } else {
                g.setColor(frameColor);
            }
            g2d.draw(yesBtn);
            g.setColor(Color.black);
            drawCenteredString(g, "No", noBtn, menuFont);
            if (menuHovered.equals("no")) {
                g.setColor(hoverAndTitleColor);
            } else {
                g.setColor(frameColor);
            }
            g2d.draw(noBtn);
            g.setColor(Color.black);
        } else if (teamfightTacticsLogic.getGameState() == GameState.OPTION_STATE) {
            g.setColor(hoverAndTitleColor);
            g.drawString(OPTIONS, (FRAME_WIDTH - metrics.stringWidth(OPTIONS)) / 2, 160);
            g.setFont(menuFont);
            g.setColor(Color.black);
            g.drawString("Reset database: ", (int) resetButton.getX() - 200, (int) resetButton.getY() + 22);
            g.drawString("Show fps: ", (int) onOffBtn.getX() - 200, (int) onOffBtn.getY() + 22);
            drawCenteredString(g, "Back", backBtn, menuFont);
            g.setColor(frameColor);
            if(dataReseted) {
                g.fillOval((int) resetButton.getX(),(int) resetButton.getY(), 30,30);
            }
            if (!fpsOn) {
                g.setColor(levelColor);
            }
            g.fillOval((int) onOffBtn.getX(),(int) onOffBtn.getY(), 30,30);

            if (menuHovered.equals("back")) {
                g.setColor(hoverAndTitleColor);
            } else {
                g.setColor(frameColor);
            }
            g2d.draw(backBtn);
            if (menuHovered.equals("onoff")) {
                g.setColor(hoverAndTitleColor);
            } else {
                g.setColor(frameColor);
            }

            g2d.draw(onOffBtn);
            if (menuHovered.equals("reset")) {
                g.setColor(hoverAndTitleColor);
            } else {
                g.setColor(frameColor);
            }

            g2d.draw(resetButton);

            g.setColor(frameColor);
        } else if (teamfightTacticsLogic.getGameState() == GameState.HELP_STATE) {
            g.setColor(hoverAndTitleColor);
            g.drawString(HELP, (FRAME_WIDTH - metrics.stringWidth(HELP)) / 2, 160);
            g.setFont(menuFont);
            g.setColor(levelColor);
            g.fillRect(400,350, 600,420);
            g.setColor(frameColor);
            g.drawRect(300,395,680,345);
            g.setColor(Color.BLACK);
            drawString(g, HELP_SENTENCES, 305,390);
            drawCenteredString(g, "Back", backBtn, menuFont);
            if (menuHovered.equals("back")) {
                g.setColor(hoverAndTitleColor);
            } else {
                g.setColor(frameColor);
            }
            g2d.draw(backBtn);
            g.setColor(Color.black);
        } else if (teamfightTacticsLogic.getGameState() == GameState.DATA_STATE) {
            g.setColor(hoverAndTitleColor);
            g.drawString(DATA, (FRAME_WIDTH - metrics.stringWidth(DATA)) / 2, 160);
            g.setFont(menuFont);
            g.setColor(Color.BLACK);
            drawCenteredString(g, "Back", backBtn, menuFont);

            g.drawString(CURRENT_DATA,
                    (FRAME_WIDTH - metrics.stringWidth(CURRENT_DATA)) / 2, 440);
            if(teamfightTacticsLogic.isDbRunning()) {
                g.drawString(KILLS + teamfightTacticsLogic.getDataScore().getKills(),
                        (FRAME_WIDTH - metrics.stringWidth(KILLS)) / 2, 480);
                g.drawString(DEATH + teamfightTacticsLogic.getDataScore().getDeath(),
                        (FRAME_WIDTH - metrics.stringWidth(DEATH)) / 2, 510);
                g.drawString(WINS + teamfightTacticsLogic.getDataScore().getWins(),
                        (FRAME_WIDTH - metrics.stringWidth(WINS)) / 2, 540);
                g.drawString(LOSES + teamfightTacticsLogic.getDataScore().getLoses(),
                        (FRAME_WIDTH - metrics.stringWidth(LOSES)) / 2, 570);
                g.drawString(GOLDS + teamfightTacticsLogic.getDataScore().getGolds(),
                        (FRAME_WIDTH - metrics.stringWidth(GOLDS)) / 2, 600);
                g.drawString(SPENT_GOLD + teamfightTacticsLogic.getDataScore().getSpent_gold(),
                        (FRAME_WIDTH - metrics.stringWidth(SPENT_GOLD)) / 2, 630);
            } else {
                g.drawString("SERVER IS OFF",
                        (FRAME_WIDTH - metrics.stringWidth("SERVER IS OFF")) / 2 + 5, 550);
            }

            if (menuHovered.equals("back")) {
                g.setColor(hoverAndTitleColor);
            } else {
                g.setColor(frameColor);
            }
            g2d.draw(backBtn);
            g.setColor(Color.black);
        } else if (teamfightTacticsLogic.getGameState() == GameState.LOADING_STATE) {
            g.setColor(levelColor);
            g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
            g.setColor(Color.BLACK);
            g.setFont(g.getFont().deriveFont(30f));
            metrics = g.getFontMetrics(g.getFont().deriveFont(30f));
            g.drawString("Loading", (GameConstants.FRAME_WIDTH - metrics.stringWidth("Loading")) / 2, GameConstants.FRAME_HEIGHT / 2 - 50);
        }
    }

    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent() + 4;
        g.setFont(font);
        g.drawString(text, x, y);
    }
}
