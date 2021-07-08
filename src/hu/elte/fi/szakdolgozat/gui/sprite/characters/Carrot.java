package hu.elte.fi.szakdolgozat.gui.sprite.characters;

import hu.elte.fi.szakdolgozat.gui.UIConstants;
import hu.elte.fi.szakdolgozat.gui.sprite.SpriteSheet;
import hu.elte.fi.szakdolgozat.model.GameConstants;
import hu.elte.fi.szakdolgozat.model.animation.Animation;
import hu.elte.fi.szakdolgozat.model.Direction;
import hu.elte.fi.szakdolgozat.model.Character;
import hu.elte.fi.szakdolgozat.model.classes.Assassin;

import java.awt.*;
import java.awt.image.BufferedImage;

import static hu.elte.fi.szakdolgozat.model.GameConstants.MELEE_RANGE;

public class Carrot extends Assassin {

    private static final String name = "Carrot";
    private static final int health = 100;
    private static final int damageThresholdLower = 25;
    private static final int damageThresholdUpper = 40;
    private static final int range = MELEE_RANGE;
    private static final int cost = 1;
    private static final int mana = 25;

    /**
     * MOVE
     */
    private static final SpriteSheet carrotMoveUpSpriteSheet = new SpriteSheet(UIConstants.carrotMoveUpAnimation);
    private final BufferedImage[] carrotMoveUpAnimationImages = {
            carrotMoveUpSpriteSheet.grabImage(1, 1, 50, 50),
            carrotMoveUpSpriteSheet.grabImage(2, 1, 50, 50),
            carrotMoveUpSpriteSheet.grabImage(3, 1, 50, 50),
            carrotMoveUpSpriteSheet.grabImage(4, 1, 50, 50),
            carrotMoveUpSpriteSheet.grabImage(5, 1, 50, 50),
            carrotMoveUpSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation carrotMoveUpAnimation = new Animation(carrotMoveUpAnimationImages, 10);

    private final SpriteSheet carrotMoveDownSpriteSheet = new SpriteSheet(UIConstants.carrotMoveDownAnimation);
    private final BufferedImage[] carrotMoveDownAnimationImages = {
            carrotMoveDownSpriteSheet.grabImage(1, 1, 50, 50),
            carrotMoveDownSpriteSheet.grabImage(2, 1, 50, 50),
            carrotMoveDownSpriteSheet.grabImage(3, 1, 50, 50),
            carrotMoveDownSpriteSheet.grabImage(4, 1, 50, 50),
            carrotMoveDownSpriteSheet.grabImage(5, 1, 50, 50),
            carrotMoveDownSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation carrotMoveDownAnimation = new Animation(carrotMoveDownAnimationImages, 10);

    private final SpriteSheet carrotMoveRightSpriteSheet = new SpriteSheet(UIConstants.carrotMoveRightAnimation);
    private final BufferedImage[] carrotMoveRightAnimationImages = {
            carrotMoveRightSpriteSheet.grabImage(1, 1, 50, 50),
            carrotMoveRightSpriteSheet.grabImage(2, 1, 50, 50),
            carrotMoveRightSpriteSheet.grabImage(3, 1, 50, 50),
            carrotMoveRightSpriteSheet.grabImage(4, 1, 50, 50),
            carrotMoveRightSpriteSheet.grabImage(5, 1, 50, 50),
            carrotMoveRightSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation carrotMoveRightAnimation = new Animation(carrotMoveRightAnimationImages, 10);

    private final SpriteSheet carrotMoveLeftSpriteSheet = new SpriteSheet(UIConstants.carrotMoveLeftAnimation);
    private final BufferedImage[] carrotMoveLeftAnimationImages = {
            carrotMoveLeftSpriteSheet.grabImage(1, 1, 50, 50),
            carrotMoveLeftSpriteSheet.grabImage(2, 1, 50, 50),
            carrotMoveLeftSpriteSheet.grabImage(3, 1, 50, 50),
            carrotMoveLeftSpriteSheet.grabImage(4, 1, 50, 50),
            carrotMoveLeftSpriteSheet.grabImage(5, 1, 50, 50),
            carrotMoveLeftSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation carrotMoveLeftAnimation = new Animation(carrotMoveLeftAnimationImages, 10);

    /**
     * ATTACK
     */
    private final SpriteSheet carrotAttackUpSpriteSheet = new SpriteSheet(UIConstants.carrotAttackUpAnimation);
    private final BufferedImage[] carrotAttackUpAnimationImages = {
            carrotAttackUpSpriteSheet.grabImage(1, 1, 50, 50),
            carrotAttackUpSpriteSheet.grabImage(2, 1, 50, 50),
            carrotAttackUpSpriteSheet.grabImage(3, 1, 50, 50),
            carrotAttackUpSpriteSheet.grabImage(4, 1, 50, 50),
            carrotAttackUpSpriteSheet.grabImage(5, 1, 50, 50),
            carrotAttackUpSpriteSheet.grabImage(6, 1, 50, 50),
            carrotAttackUpSpriteSheet.grabImage(7, 1, 50, 50),
            carrotAttackUpSpriteSheet.grabImage(8, 1, 50, 50),
            carrotAttackUpSpriteSheet.grabImage(9, 1, 50, 50),
            carrotAttackUpSpriteSheet.grabImage(10, 1, 50, 50),
    };
    private final Animation carrotAttackUpAnimation = new Animation(carrotAttackUpAnimationImages, 8);

    private final SpriteSheet carrotAttackDownSpriteSheet = new SpriteSheet(UIConstants.carrotAttackDownAnimation);
    private final BufferedImage[] carrotAttackDownAnimationImages = {
            carrotAttackDownSpriteSheet.grabImage(1, 1, 50, 50),
            carrotAttackDownSpriteSheet.grabImage(2, 1, 50, 50),
            carrotAttackDownSpriteSheet.grabImage(3, 1, 50, 50),
            carrotAttackDownSpriteSheet.grabImage(4, 1, 50, 50),
            carrotAttackDownSpriteSheet.grabImage(5, 1, 50, 50),
            carrotAttackDownSpriteSheet.grabImage(6, 1, 50, 50),
            carrotAttackDownSpriteSheet.grabImage(7, 1, 50, 50),
            carrotAttackDownSpriteSheet.grabImage(8, 1, 50, 50),
            carrotAttackDownSpriteSheet.grabImage(9, 1, 50, 50),
            carrotAttackDownSpriteSheet.grabImage(10, 1, 50, 50),
    };
    private final Animation carrotAttackDownAnimation = new Animation(carrotAttackDownAnimationImages, 8);

    private final SpriteSheet carrotAttackLeftSpriteSheet = new SpriteSheet(UIConstants.carrotAttackLeftAnimation);
    private final BufferedImage[] carrotAttackLeftAnimationImages = {
            carrotAttackLeftSpriteSheet.grabImage(1, 1, 50, 50),
            carrotAttackLeftSpriteSheet.grabImage(2, 1, 50, 50),
            carrotAttackLeftSpriteSheet.grabImage(3, 1, 50, 50),
            carrotAttackLeftSpriteSheet.grabImage(4, 1, 50, 50),
            carrotAttackLeftSpriteSheet.grabImage(5, 1, 50, 50),
            carrotAttackLeftSpriteSheet.grabImage(6, 1, 50, 50),
            carrotAttackLeftSpriteSheet.grabImage(7, 1, 50, 50),
            carrotAttackLeftSpriteSheet.grabImage(8, 1, 50, 50),
            carrotAttackLeftSpriteSheet.grabImage(9, 1, 50, 50),
            carrotAttackLeftSpriteSheet.grabImage(10, 1, 50, 50),
    };
    private final Animation carrotAttackLeftAnimation = new Animation(carrotAttackLeftAnimationImages, 8);

    private final SpriteSheet carrotAttackRightSpriteSheet = new SpriteSheet(UIConstants.carrotAttackRightAnimation);
    private final BufferedImage[] carrotAttackRightAnimationImages = {
            carrotAttackRightSpriteSheet.grabImage(1, 1, 50, 50),
            carrotAttackRightSpriteSheet.grabImage(2, 1, 50, 50),
            carrotAttackRightSpriteSheet.grabImage(3, 1, 50, 50),
            carrotAttackRightSpriteSheet.grabImage(4, 1, 50, 50),
            carrotAttackRightSpriteSheet.grabImage(5, 1, 50, 50),
            carrotAttackRightSpriteSheet.grabImage(6, 1, 50, 50),
            carrotAttackRightSpriteSheet.grabImage(7, 1, 50, 50),
            carrotAttackRightSpriteSheet.grabImage(8, 1, 50, 50),
            carrotAttackRightSpriteSheet.grabImage(9, 1, 50, 50),
            carrotAttackRightSpriteSheet.grabImage(10, 1, 50, 50),
    };
    private final Animation carrotAttackRightAnimation = new Animation(carrotAttackRightAnimationImages, 8);

    /**
     * DEATH
     */
    private final SpriteSheet carrotDeathSpriteSheet = new SpriteSheet(UIConstants.carrotDeathAnimation);
    private final BufferedImage[] carrotDeathAnimationImages = {
            carrotDeathSpriteSheet.grabImage(1, 1, 50, 50),
            carrotDeathSpriteSheet.grabImage(2, 1, 50, 50),
            carrotDeathSpriteSheet.grabImage(3, 1, 50, 50),
            carrotDeathSpriteSheet.grabImage(4, 1, 50, 50),
            carrotDeathSpriteSheet.grabImage(5, 1, 50, 50),
            carrotDeathSpriteSheet.grabImage(6, 1, 50, 50),
            carrotDeathSpriteSheet.grabImage(7, 1, 50, 50),
            carrotDeathSpriteSheet.grabImage(8, 1, 50, 50),
    };
    private final Animation carrotDeathAnimation = new Animation(carrotDeathAnimationImages, 8);

    private static final Image image = carrotMoveUpSpriteSheet.grabImage(2, 1, 50, 50);


    public Carrot(int x, int y) {
        super(image, x, y, health, mana, damageThresholdLower, damageThresholdUpper,
                range, cost, name);
        speed = 0.1;

        if (y <= GameConstants.FRAME_HEIGHT / 2) {
            animation = carrotMoveUpAnimation;
        } else {
            animation = carrotMoveDownAnimation;
        }
        SpriteSheet carrotSkillSpriteSheet = new SpriteSheet(UIConstants.carrotSkillAnimation);
        BufferedImage[] carrotSkillAnimationImages = {
                carrotSkillSpriteSheet.grabImage(1, 1, 50, 50),
                carrotSkillSpriteSheet.grabImage(2, 1, 50, 50),
                carrotSkillSpriteSheet.grabImage(3, 1, 50, 50),
                carrotSkillSpriteSheet.grabImage(4, 1, 50, 50),
                carrotSkillSpriteSheet.grabImage(5, 1, 50, 50),
                carrotSkillSpriteSheet.grabImage(6, 1, 50, 50),
                carrotSkillSpriteSheet.grabImage(7, 1, 50, 50),
                carrotSkillSpriteSheet.grabImage(8, 1, 50, 50),
                carrotSkillSpriteSheet.grabImage(9, 1, 50, 50),
                carrotSkillSpriteSheet.grabImage(10, 1, 50, 50),
        };
        skillAnimation = new Animation(carrotSkillAnimationImages, 10);
    }

    @Override
    protected void drawDeath(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();
        animation = carrotDeathAnimation;
        animation.start();
        g2d.drawImage(animation.getSprite(), x - 5, y - 5, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
    }

    @Override
    protected void drawSkill(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        skillAnimation.stop();
        skillAnimation.start();
        if (targetEnemyCharacter != null) {
            g2d.drawImage(skillAnimation.getSprite(), targetEnemyCharacter.getX() - 5, targetEnemyCharacter.getY() - 5, skillAnimation.getSprite().getWidth(), skillAnimation.getSprite().getHeight(), null);
        }
    }

    @Override
    protected void drawAttack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();
        drawAnimation(dir,
                carrotAttackDownAnimation,
                carrotAttackUpAnimation,
                carrotAttackRightAnimation,
                carrotAttackLeftAnimation);

        animation.start();
        g2d.drawImage(animation.getSprite(), x - 5, y - 5, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
    }

    @Override
    protected void drawCharacter(Graphics g, Direction dir) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();
        drawAnimation(dir,
                carrotMoveUpAnimation,
                carrotMoveDownAnimation,
                carrotMoveRightAnimation,
                carrotMoveLeftAnimation);
        animation.start();

        g2d.drawImage(animation.getSprite(), x - 5, y - 5, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
    }

    @Override
    protected void assassinSkill(Character target, int value) {
        useSkill = true;
        target.removeHealth(value * 2);
    }
}
