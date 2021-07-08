package hu.elte.fi.szakdolgozat.gui.sprite.player;

import hu.elte.fi.szakdolgozat.gui.sprite.Sprite;
import hu.elte.fi.szakdolgozat.model.animation.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;

import static hu.elte.fi.szakdolgozat.model.GameConstants.*;
import static hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic.getDroppedGold;

public class Player extends Sprite {

    private Animation moveLeftAnimation;
    private Animation moveRightAnimation;
    private Animation moveDownAnimation;
    private Animation moveUpAnimation;
    private Animation boringAnimation;
    private Animation animation;
    private final Timer timer;
    private final Timer boredTimer;
    private final Color darkBlue = new Color(51, 153, 255);

    private Ellipse2D.Double pickUpRange;
    private Ellipse2D.Double moveStopRange;

    private int level;
    private int levelingLine;
    private final int maxHealth;
    private int winStreak;
    private int loseStreak;
    private int health;
    private int gold;
    private int targetX, targetY;
    private double runTime;
    private double startX, startY;
    private Long startTime;
    private boolean bored = true;
    private long start = System.currentTimeMillis();
    private int levelingLineCap;
    private int playableCharacterNum;

    public void setBoringAnimation(Animation boringAnimation) {
        this.boringAnimation = boringAnimation;
    }

    public int getWinStreak() {
        return winStreak;
    }

    public void setWinStreak(int winStreak) {
        this.winStreak = winStreak;
    }

    public int getLoseStreak() {
        return loseStreak;
    }

    public void setLoseStreak(int loseStreak) {
        this.loseStreak = loseStreak;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevelingLineCap() {
        return levelingLineCap;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setMoveLeftAnimation(Animation moveLeftAnimation) {
        this.moveLeftAnimation = moveLeftAnimation;
    }

    public void setMoveRightAnimation(Animation moveRightAnimation) {
        this.moveRightAnimation = moveRightAnimation;
    }

    public void setMoveDownAnimation(Animation moveDownAnimation) {
        this.moveDownAnimation = moveDownAnimation;
    }

    public void setMoveUpAnimation(Animation moveUpAnimation) {
        this.moveUpAnimation = moveUpAnimation;
    }

    public int getLevel() {
        return level;
    }

    public int getLevelingLine() {
        return levelingLine;
    }

    public void setLevelingLine(int levelingLine) {
        this.levelingLine = levelingLine;
        if (this.levelingLine >= levelingLineCap) {
            this.levelingLine -= levelingLineCap;
            this.levelingLineCap = (int) (2 * Math.round((levelingLine * 1.5) / 2.0));
            this.level++;
            this.playableCharacterNum++;
        }
    }

    public int getPlayableCharacterNum() {
        return playableCharacterNum;
    }

    public int getHealth() {
        return health;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public Player(int sourceX, int sourceY, Image image) {
        super(sourceX, sourceY, image);
        health = 100;
        maxHealth = 100;
        gold = 8;
        playableCharacterNum = 3;
        level = 1;
        levelingLine = 0;
        levelingLineCap = 6;

        Action moving = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pickUpRange = new Ellipse2D.Double(x - 40, y - 35, 120, 120);

                for (int i = 0; i < getDroppedGold().size(); i++) {
                    if (pickUpRange.contains(getDroppedGold().get(i).getX(), getDroppedGold().get(i).getY())) {
                        getDroppedGold().remove(getDroppedGold().get(i));
                        gold += 2;
                    }
                }

                moveStopRange = new Ellipse2D.Double(x - 10, y - 10, 40, 40);
                if (moveStopRange.contains(targetX, targetY)) {
                    animation.stop();
                    if (y > FRAME_HEIGHT / 2) {
                        animation = moveUpAnimation;
                    } else {
                        animation = moveDownAnimation;
                    }
                    animation.start();
                    boredTimer.stop();
                    timer.stop();
                }

                if(health <= 0) {
                    animation.stop();
                    timer.stop();
                }


                long duration = System.currentTimeMillis() - startTime;
                double progress = duration / runTime;

                if (progress >= 1.0) {
                    progress = 1.0;
                    timer.stop();
                }

                x = (int) (startX + ((targetX - startX) * progress));
                y = (int) (startY + ((targetY - startY) * progress));
            }
        };
        timer = new Timer(PERIOD, moving);
        Action boring = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!timer.isRunning()) {
                    long duration = System.currentTimeMillis() - start;
                    if (duration / 1000 / 6 == 1 && bored) {
                        bored = false;
                        animation = boringAnimation;
                        animation.start();
                    }
                    if (duration / 1000 / 9 == 1) {
                        bored = true;
                        if (y > FRAME_HEIGHT / 2) {
                            animation = moveUpAnimation;
                        } else {
                            animation = moveDownAnimation;
                        }

                        start = System.currentTimeMillis();
                    }
                }
            }
        };
        boredTimer = new Timer(PERIOD, boring);
        boredTimer.start();
    }

    public void drawPlayer(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(animation.getSprite(), x - 20, y - 20, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);

        g.setColor(Color.black);
        g2d.fillRect(x - 10, y - 22, 60, 15);

        g.setColor(Color.gray);
        g2d.drawRect(x - 11, y - 23, 61, 16);
        g2d.fillRect(x - 31, y - 25, 20, 20);
        g.setColor(darkBlue);
        g2d.fillRect(x - 30, y - 24, 18, 18);

        if (getHealth() > maxHealth * 0.66) {
            g.setColor(Color.green);
        } else if (health < maxHealth * 0.66 && health > maxHealth * 0.33) {
            g.setColor(Color.orange);
        } else if (health < maxHealth * 0.33) {
            g.setColor(Color.red);
        }

        g2d.fillRect(x - 10, y - 22, (60 * health) / maxHealth, 15);

        g.setColor(Color.black);
        g.drawString(String.valueOf(level), x - 24, y - 11);
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void playerMove(int targetX, int targetY) {
        timer.stop();
        calculateMovement(targetX, targetY);
        startTime = System.currentTimeMillis();
        bored = false;
        timer.start();
    }

    public void calculateMovement(int targetX, int targetY) {
        this.targetX = targetX - 10;
        this.targetY = targetY - 10;

        startX = x;
        startY = y;

        animation.stop();
        if (Math.abs(targetX - x) > Math.abs(targetY - y)) {
            if (x > targetX) {
                animation = moveLeftAnimation;
            } else if (x < targetX) {
                animation = moveRightAnimation;
            }
        } else {
            if (y > targetY) {
                animation = moveUpAnimation;
            } else if (y < targetY) {
                animation = moveDownAnimation;
            }
        }
        animation.start();

        double distance = Math.sqrt(
                (startX - targetX) * (startX - targetX)
                        + (startY - targetY) * (startY - targetY));

        double speed = 0.1;
        runTime = distance / speed;
    }
}
