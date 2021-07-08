package hu.elte.fi.szakdolgozat.gui.sprite.characters;

import hu.elte.fi.szakdolgozat.gui.sprite.SpriteSheet;
import hu.elte.fi.szakdolgozat.model.animation.Animation;
import hu.elte.fi.szakdolgozat.model.Direction;
import hu.elte.fi.szakdolgozat.model.classes.Minion;

import java.awt.*;
import java.awt.image.BufferedImage;

import static hu.elte.fi.szakdolgozat.model.GameConstants.MELEE_RANGE;
import static hu.elte.fi.szakdolgozat.gui.UIConstants.*;
import static hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic.*;

public class PurpleSlime extends Minion {

    private static final String name = "Purple Slime";
    private static final int health = 100;
    private static final int damageThresholdLower = 10;
    private static final int damageThresholdUpper = 25;
    private static final int range = MELEE_RANGE;
    private static final int cost = 999;
    private static final int mana = 25;
    /**
     * move
     */
    private final SpriteSheet moveUpSpriteSheet = new SpriteSheet(purpleMoveUpAnimation);
    private final BufferedImage[] moveUpAnimationImages = {
            moveUpSpriteSheet.grabImage(1, 1, 50, 50),
            moveUpSpriteSheet.grabImage(2, 1, 50, 50),
            moveUpSpriteSheet.grabImage(3, 1, 50, 50),
            moveUpSpriteSheet.grabImage(4, 1, 50, 50),
            moveUpSpriteSheet.grabImage(5, 1, 50, 50),
            moveUpSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation moveUpAnimation = new Animation(moveUpAnimationImages, 10);

    private static final SpriteSheet moveDownSpriteSheet = new SpriteSheet(purpleMoveDownAnimation);
    private final BufferedImage[] moveDownAnimationImages = {
            moveDownSpriteSheet.grabImage(1, 1, 50, 50),
            moveDownSpriteSheet.grabImage(2, 1, 50, 50),
            moveDownSpriteSheet.grabImage(3, 1, 50, 50),
            moveDownSpriteSheet.grabImage(4, 1, 50, 50),
            moveDownSpriteSheet.grabImage(5, 1, 50, 50),
            moveDownSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation moveDownAnimation = new Animation(moveDownAnimationImages, 10);

    private final SpriteSheet moveRightSpriteSheet = new SpriteSheet(purpleMoveRightAnimation);
    private final BufferedImage[] moveRightAnimationImages = {
            moveRightSpriteSheet.grabImage(1, 1, 50, 50),
            moveRightSpriteSheet.grabImage(2, 1, 50, 50),
            moveRightSpriteSheet.grabImage(3, 1, 50, 50),
            moveRightSpriteSheet.grabImage(4, 1, 50, 50),
            moveRightSpriteSheet.grabImage(5, 1, 50, 50),
            moveRightSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation moveRightAnimation = new Animation(moveRightAnimationImages, 10);

    private final SpriteSheet moveLeftSpriteSheet = new SpriteSheet(purpleMoveLeftAnimation);
    private final BufferedImage[] moveLeftAnimationImages = {
            moveLeftSpriteSheet.grabImage(1, 1, 50, 50),
            moveLeftSpriteSheet.grabImage(2, 1, 50, 50),
            moveLeftSpriteSheet.grabImage(3, 1, 50, 50),
            moveLeftSpriteSheet.grabImage(4, 1, 50, 50),
            moveLeftSpriteSheet.grabImage(5, 1, 50, 50),
            moveLeftSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation moveLeftAnimation = new Animation(moveLeftAnimationImages, 10);

    /**
     * attack
     */
    private final SpriteSheet attackUpSpriteSheet = new SpriteSheet(purpleAttackUpAnimation);
    private final BufferedImage[] attackUpAnimationImages = {
            attackUpSpriteSheet.grabImage(1, 1, 50, 50),
            attackUpSpriteSheet.grabImage(2, 1, 50, 50),
            attackUpSpriteSheet.grabImage(3, 1, 50, 50),
            attackUpSpriteSheet.grabImage(4, 1, 50, 50),
            attackUpSpriteSheet.grabImage(5, 1, 50, 50),
            attackUpSpriteSheet.grabImage(6, 1, 50, 50),
            attackUpSpriteSheet.grabImage(7, 1, 50, 50),
            attackUpSpriteSheet.grabImage(8, 1, 50, 50),
    };
    private final Animation attackUpAnimation = new Animation(attackUpAnimationImages, 8);

    private final SpriteSheet attackDownSpriteSheet = new SpriteSheet(purpleAttackDownAnimation);
    private final BufferedImage[] attackDownAnimationImages = {
            attackDownSpriteSheet.grabImage(1, 1, 50, 50),
            attackDownSpriteSheet.grabImage(2, 1, 50, 50),
            attackDownSpriteSheet.grabImage(3, 1, 50, 50),
            attackDownSpriteSheet.grabImage(4, 1, 50, 50),
            attackDownSpriteSheet.grabImage(5, 1, 50, 50),
            attackDownSpriteSheet.grabImage(6, 1, 50, 50),
            attackDownSpriteSheet.grabImage(7, 1, 50, 50),
            attackDownSpriteSheet.grabImage(8, 1, 50, 50),
    };
    private final Animation attackDownAnimation = new Animation(attackDownAnimationImages, 8);

    private final SpriteSheet attackLeftSpriteSheet = new SpriteSheet(purpleAttackLeftAnimation);
    private final BufferedImage[] attackLeftAnimationImages = {
            attackLeftSpriteSheet.grabImage(1, 1, 50, 50),
            attackLeftSpriteSheet.grabImage(2, 1, 50, 50),
            attackLeftSpriteSheet.grabImage(3, 1, 50, 50),
            attackLeftSpriteSheet.grabImage(4, 1, 50, 50),
            attackLeftSpriteSheet.grabImage(5, 1, 50, 50),
            attackLeftSpriteSheet.grabImage(6, 1, 50, 50),
            attackLeftSpriteSheet.grabImage(7, 1, 50, 50),
            attackLeftSpriteSheet.grabImage(8, 1, 50, 50),
    };
    private final Animation attackLeftAnimation = new Animation(attackLeftAnimationImages, 8);

    private final SpriteSheet attackRightSpriteSheet = new SpriteSheet(purpleAttackRightAnimation);
    private final BufferedImage[] attackRightAnimationImages = {
            attackRightSpriteSheet.grabImage(1, 1, 50, 50),
            attackRightSpriteSheet.grabImage(2, 1, 50, 50),
            attackRightSpriteSheet.grabImage(3, 1, 50, 50),
            attackRightSpriteSheet.grabImage(4, 1, 50, 50),
            attackRightSpriteSheet.grabImage(5, 1, 50, 50),
            attackRightSpriteSheet.grabImage(6, 1, 50, 50),
            attackRightSpriteSheet.grabImage(7, 1, 50, 50),
            attackRightSpriteSheet.grabImage(8, 1, 50, 50),
    };
    private final Animation attackRightAnimation = new Animation(attackRightAnimationImages, 8);

    /**
     * death
     */
    private final SpriteSheet deathSpriteSheet = new SpriteSheet(purpleDeathAnimation);
    private final BufferedImage[] deathAnimationImages = {
            deathSpriteSheet.grabImage(1, 1, 50, 50),
            deathSpriteSheet.grabImage(2, 1, 50, 50),
            deathSpriteSheet.grabImage(3, 1, 50, 50),
            deathSpriteSheet.grabImage(4, 1, 50, 50),
            deathSpriteSheet.grabImage(5, 1, 50, 50),
            deathSpriteSheet.grabImage(6, 1, 50, 50),
            deathSpriteSheet.grabImage(7, 1, 50, 50),
            deathSpriteSheet.grabImage(8, 1, 50, 50),
    };
    private final Animation deathAnimation = new Animation(deathAnimationImages, 8);

    private static final Image image = moveDownSpriteSheet.grabImage(1, 1, 50, 50);

    @Override
    protected void drawDeath(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();
        animation = deathAnimation;
        animation.start();
        g2d.drawImage(animation.getSprite(), x, y - 5, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
    }

    @Override
    protected void drawSkill(Graphics g) {

    }

    @Override
    protected void drawAttack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();
        drawAnimation(dir,
                attackDownAnimation,
                attackUpAnimation,
                attackRightAnimation,
                attackLeftAnimation);

        animation.start();
        g2d.drawImage(animation.getSprite(), x, y - 5, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);

    }


    @Override
    protected void drawCharacter(Graphics g, Direction dir) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();
        drawAnimation(dir,
                moveDownAnimation,
                moveUpAnimation,
                moveRightAnimation,
                moveLeftAnimation);
        animation.start();

        g2d.drawImage(animation.getSprite(), x, y - 5, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
    }

    public PurpleSlime(int x, int y, String string, int level) {
        super(image, x, y, health, mana, damageThresholdLower, damageThresholdUpper,
                range, cost, name);

        speed = 0.1;

        animation = moveLeftAnimation;

        setLevel(level);
        if (string.equals("A")) {
            getFightingAlliances().add(this);
            Thread t = new Thread(() -> behaviour(this));

            t.start();
        } else if (string.equals("E")) {
            getFightingEnemies().add(this);
            Thread t = new Thread(() -> behaviour(this));

            t.start();
        }
    }

    @Override
    protected void minionSkill() {
        addHealth(35 * level);
    }
}

