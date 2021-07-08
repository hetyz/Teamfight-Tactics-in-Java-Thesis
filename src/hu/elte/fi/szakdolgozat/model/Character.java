package hu.elte.fi.szakdolgozat.model;

import hu.elte.fi.szakdolgozat.model.classes.Assassin;
import hu.elte.fi.szakdolgozat.gui.sprite.other.Gold;
import hu.elte.fi.szakdolgozat.model.classes.Minion;
import hu.elte.fi.szakdolgozat.gui.sprite.Sprite;
import hu.elte.fi.szakdolgozat.model.animation.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static hu.elte.fi.szakdolgozat.model.GameConstants.PERIOD;
import static hu.elte.fi.szakdolgozat.gui.UIConstants.*;
import static hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic.*;

public abstract class Character extends Sprite {

    protected Character targetEnemyCharacter;
    protected Direction dir;
    protected Animation animation;
    protected Animation skillAnimation;
    protected final AtomicInteger health;
    protected final AtomicInteger mana;
    protected boolean inRange = false;
    protected volatile int maxHealth;
    protected final int maxMana = 100;
    protected int damageThresholdLower;
    protected int damageThresholdUpper;
    protected final String name;
    protected final int range;
    protected boolean useSkill = false;
    protected boolean useAttack = false;
    protected double speed;
    protected final int cost;
    protected int level;

    private final Timer timer;
    private Ellipse2D.Double characterRange;
    private static final Random randomGenerator = new Random();
    private double runTime;
    private Long startTime;
    private int targetX;
    private int targetY;
    private int startX, startY;
    private final int startMana;
    private int originalPosX;
    private int originalPosY;

    public void setInRange(boolean inRange) {
        this.inRange = inRange;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Character getTargetEnemyCharacter() {
        return targetEnemyCharacter;
    }

    public Animation getAnimation() {
        return animation;
    }

    public Animation getSkillAnimation() {
        return skillAnimation;
    }

    public int xCenterOfOval() {
        return x - range / 2;
    }

    public int yCenterOfOval() {
        return y - range / 2;
    }

    public int getOriginalPosX() {
        return originalPosX;
    }

    public void setOriginalPosX(int originalPosX) {
        this.originalPosX = originalPosX;
    }

    public void setOriginalPosY(int originalPosY) {
        this.originalPosY = originalPosY;
    }

    public int getOriginalPosY() {
        return originalPosY;
    }

    public int getLevel() {
        return level;
    }

    public void setStartMana() {
        mana.set(startMana);
    }

    public void setMaxHealth() {
        health.set(maxHealth);
    }

    public int getDamageThresholdLower() {
        return this.damageThresholdLower;
    }

    public int getDamageThresholdUpper() {
        return this.damageThresholdUpper;
    }

    public void setDirection(Direction dir) {
        this.dir = dir;
    }

    public void setTargetEnemyCharacter(Character targetEnemyCharacter) {
        this.targetEnemyCharacter = targetEnemyCharacter;
    }


    public int getHealth() {
        return this.health.get();
    }

    public boolean isDead() {
        return this.health.get() <= 0;
    }

    public String getName() {
        return this.name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getRange() {
        return range;
    }

    public void setLevel(int level) {
        this.level = Math.min(level, 3);
        damageThresholdLower = (int) (damageThresholdLower * this.level * 0.75);
        damageThresholdUpper = (int) (damageThresholdUpper * this.level * 0.75);
        health.set((int) (maxHealth * this.level * 0.75));
        maxHealth = health.get();
    }

    public int getCost() {
        return cost;
    }

    public void originalPos() {
        x = originalPosX;
        y = originalPosY;
    }

    public void setOriginalPos() {
        originalPosX = x;
        originalPosY = y;
    }

    protected Character(Image image, int sourceX, int sourceY, int health, int mana, int damageThresholdLower, int damageThresholdUpper, int range, int cost, String name) {
        super(sourceX, sourceY, image);
        this.originalPosX = sourceX;
        this.originalPosY = sourceY;
        this.health = new AtomicInteger(health);
        this.maxHealth = health;
        this.mana = new AtomicInteger(mana);
        this.startMana = mana;
        this.damageThresholdLower = damageThresholdLower;
        this.damageThresholdUpper = damageThresholdUpper;
        this.range = range;
        this.name = name;
        this.cost = cost;
        this.level = 1;
        Action moving = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOver.get() || inRange || characterRange.contains(targetX, targetY)) {
                    inRange = true;
                    timer.stop();
                    return;
                }
                if (getFightingAlliances().isEmpty() || getFightingEnemies().isEmpty() || isDead()) {
                    timer.stop();
                    return;
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
    }

    protected abstract void performAction(Character target, int value);

    public void behaviour(Character character) {
        if (character instanceof Assassin) {
            sleepAtStart(1000);
            speed = 0.8;
            Assassin.setAtEarlyGame(true);
            calculateTarget("MAX");
            sleepAtStart(1000);
            speed = 0.1;
            Assassin.setAtEarlyGame(false);
        } else if (character instanceof Minion) {
            calculateTarget("MIN");
        } else {
            sleepAtStart(2000);
            calculateTarget("MIN");
        }

        while (!character.isDead() && !isOver.get()) {
            if (getFightingAlliances().isEmpty() || getFightingEnemies().isEmpty()) {
                break;
            }
            calculateTarget("MIN");

            if (character.targetEnemyCharacter.isDead() && character.targetEnemyCharacter != null) {
                inRange = false;
            }
            if (character.targetEnemyCharacter != null && character.inRange) {
                int value = getRandomNumber(character.getDamageThresholdLower(), character.getDamageThresholdUpper());
                character.performAction(character.targetEnemyCharacter, value);
            } else {
                int waitTime = 250;
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (character.getHealth() <= 0) {
            handleHeroDeath(character);
        }
    }

    protected static synchronized int getRandomNumber(int lower, int upper) {
        int bound = upper - lower;
        if (bound == 0)
            return 0;
        return lower + Math.abs(randomGenerator.nextInt() % bound);
    }

    public void addHealth(int value) {
        if (!isDead()) {
            health.getAndUpdate(prev -> Math.min(prev + value, maxHealth));
        }
    }


    public void removeHealth(int value) {
        if (!isDead()) {
            health.addAndGet(-value);
        }
    }

    protected void addMana(int value) {
        if (!isDead() && mana.get() <= 100) {
            if (mana.get() + value > 100) {
                mana.set(100);
            } else {
                mana.addAndGet(value);
            }
        }
    }

    private int distance(double x1, double y1, double x2, double y2) {
        double x = Math.pow(x2 - x1, 2);
        double y = Math.pow(y2 - y1, 2);
        return (int) Math.sqrt(x + y);
    }

    private void characterMove(int targetX, int targetY) {
        if (!isOver.get() && !this.isDead()) {
            timer.stop();
            calculateCharacterMovement(targetX, targetY);
            startTime = System.currentTimeMillis();
            timer.start();
        }
    }

    protected void sleepAtStart(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract void drawDeath(Graphics g);

    protected void drawAnimation(Direction dir, Animation down, Animation up, Animation right, Animation left) {
        if (dir == Direction.SOUTH) {
            animation = down;
        } else if (dir == Direction.NORTH) {
            animation = up;
        } else if (dir == Direction.EAST) {
            animation = right;
        } else if (dir == Direction.WEST) {
            animation = left;
        }
    }

    private void calculateCharacterMovement(int targetX, int targetY) {
        if (characterRange.contains(targetX, targetY)) {
            inRange = true;
            return;
        }
        if (isDead()) {
            return;
        }

        this.targetX = targetX;
        this.targetY = targetY;

        startX = x;
        startY = y;

        double distance = Math.sqrt(
                (startX - targetX) * (startX - targetX)
                        + (startY - targetY) * (startY - targetY));

        runTime = distance / speed;
    }

    protected void updateDirection() {
        if (animation != null && targetEnemyCharacter != null) {
            animation.stop();
            if (Math.abs(targetEnemyCharacter.x - x) > Math.abs(targetEnemyCharacter.y - y)) {
                if (x > targetEnemyCharacter.x) {
                    dir = Direction.WEST;
                } else {
                    dir = Direction.EAST;
                }
            } else {
                if (y > targetEnemyCharacter.y) {
                    dir = Direction.NORTH;
                } else {
                    dir = Direction.SOUTH;
                }
            }
            animation.start();
        }
    }

    protected void calculateTarget(String distance) {
        characterRange = new Ellipse2D.Double(xCenterOfOval() + 20, yCenterOfOval() + 20,
                range, range);
        updateDirection();

        if (targetEnemyCharacter == null || targetEnemyCharacter.isDead() || !inRange) {
            int closestPointX = 0;
            int closestPointY = 0;
            if (getFightingAlliances().contains(this) && !getFightingEnemies().isEmpty()) {
                closestPointX = getFightingEnemies().get(0).x + 15;
                closestPointY = getFightingEnemies().get(0).y + 15;
                targetEnemyCharacter = getFightingEnemies().get(0);
                int closestDist = distance(x, y, getFightingEnemies().get(0).x + 15, getFightingEnemies().get(0).y + 15);

                for (int i = 0; i < getFightingEnemies().size(); i++) {
                    int dist = distance(x, y, getFightingEnemies().get(i).x + 15, getFightingEnemies().get(i).y + 15);
                    if (distance.equals("MIN")) {
                        if (dist < closestDist && !getFightingEnemies().get(i).isDead()) {
                            closestDist = dist;
                            closestPointX = getFightingEnemies().get(i).x + 15;
                            closestPointY = getFightingEnemies().get(i).y + 15;
                            targetEnemyCharacter = getFightingEnemies().get(i);
                        }
                    } else if (distance.equals("MAX")) {
                        if (dist > closestDist && !getFightingEnemies().get(i).isDead()) {
                            closestDist = dist;
                            closestPointX = getFightingEnemies().get(i).x + 15;
                            closestPointY = getFightingEnemies().get(i).y + 15;
                            targetEnemyCharacter = getFightingEnemies().get(i);
                        }
                    }
                }

                if (characterRange.contains(closestPointX, closestPointY)) {
                    inRange = true;
                    return;
                } else {
                    inRange = false;
                }

                if (this instanceof Assassin && Assassin.isAtEarlyGame()) {
                    closestPointY += 50;
                }
            } else if (getFightingEnemies().contains(this) && !getFightingAlliances().isEmpty()) {
                closestPointX = getFightingAlliances().get(0).x + 15;
                closestPointY = getFightingAlliances().get(0).y + 15;
                targetEnemyCharacter = getFightingAlliances().get(0);
                int closestDist = distance(x, y, getFightingAlliances().get(0).x + 15, getFightingAlliances().get(0).y + 15);

                for (int i = 0; i < getFightingAlliances().size(); i++) {
                    int dist = distance(x, y, getFightingAlliances().get(i).x + 15, getFightingAlliances().get(i).y + 15);
                    if (distance.equals("MIN")) {
                        if (dist < closestDist && !getFightingAlliances().get(i).isDead()) {
                            closestDist = dist;
                            closestPointX = getFightingAlliances().get(i).x + 15;
                            closestPointY = getFightingAlliances().get(i).y + 15;
                            targetEnemyCharacter = getFightingAlliances().get(i);
                        }
                    } else if (distance.equals("MAX")) {
                        if (dist > closestDist && !getFightingAlliances().get(i).isDead()) {
                            closestDist = dist;
                            closestPointX = getFightingAlliances().get(i).x + 15;
                            closestPointY = getFightingAlliances().get(i).y + 15;
                            targetEnemyCharacter = getFightingAlliances().get(i);
                        }
                    }
                }


                if (characterRange.contains(closestPointX, closestPointY)) {
                    inRange = true;
                    return;
                } else {
                    inRange = false;
                }
                if (this instanceof Assassin && Assassin.isAtEarlyGame()) {
                    closestPointY -= 50;
                }
            }
            characterMove(closestPointX, closestPointY);
        } else {
            characterMove(targetEnemyCharacter.x, targetEnemyCharacter.y);
        }
    }

    protected void handleHeroDeath(Character character) {
        if (character.isDead()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (getFightingAlliances().contains(character)) {
                getFightingAlliances().remove(character);
                TeamfightTacticsLogic.setDeaths(getDeaths() + 1);
            } else {
                int dropChance = getRandomNumber(0, 100);
                if (dropChance >= 85 && !(character instanceof Minion)) {
                    getDroppedGold().add(new Gold(character.x, character.y, goldAnimation));
                }
                TeamfightTacticsLogic.setKills(getKills() + 1);
                getFightingEnemies().remove(character);
            }
        }
        if (getFightingEnemies().isEmpty() || getFightingAlliances().isEmpty()) {
            isOver.set(true);
        }
    }

    protected abstract void drawSkill(Graphics g);

    protected abstract void drawAttack(Graphics g);

    protected abstract void drawCharacter(Graphics g, Direction dir);

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (isPlanningTime.get()) {
            drawCharacter(g, dir);

            g2d.fillRect(x - 10, y - 22, 60, 9);
            g2d.fillRect(x - 10, y - 13, 60, 6);
            g2d.drawRect(x - 11, y - 23, 61, 16);

            g.setColor(Color.green);

            drawCharacterInfo(g);
        } else {
            if (getHealth() > 0) {
                if (getFightingAlliances().contains(this) || getFightingEnemies().contains(this) || getAlliances().contains(this)) {
                    if (useAttack) {
                        drawAttack(g);
                    } else {
                        drawCharacter(g, dir);
                    }

                    if (useSkill) {
                        drawSkill(g);
                    }

                    g.setColor(Color.black);
                    g2d.fillRect(x - 10, y - 22, 60, 9);
                    g2d.fillRect(x - 10, y - 13, 60, 6);
                    g2d.drawRect(x - 11, y - 23, 61, 16);

                    if (getFightingAlliances().contains(this) || getAlliances().contains(this)) {
                        g.setColor(Color.green);
                    } else {
                        g.setColor(Color.red);
                    }
                    drawCharacterInfo(g);
                } else {
                    drawCharacter(g, dir);
                }
            } else {
                if (!animation.isCompleted()) {
                    drawDeath(g);
                }
            }
        }
    }

    private void drawCharacterInfo(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRect(x - 10, y - 22, (60 * health.get()) / maxHealth, 9);
        g.setColor(Color.blue);
        g2d.fillRect(x - 10, y - 13, (60 * mana.get()) / maxMana, 6);

        g.setColor(Color.black);
        g.drawString(String.valueOf(level), x - 20, y - 10);
    }
}
