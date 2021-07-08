package hu.elte.fi.szakdolgozat.gui.sprite.characters;

import hu.elte.fi.szakdolgozat.gui.UIConstants;
import hu.elte.fi.szakdolgozat.gui.sprite.other.Bullet;
import hu.elte.fi.szakdolgozat.gui.sprite.SpriteSheet;
import hu.elte.fi.szakdolgozat.model.GameConstants;
import hu.elte.fi.szakdolgozat.model.animation.Animation;
import hu.elte.fi.szakdolgozat.model.Direction;
import hu.elte.fi.szakdolgozat.model.Character;
import hu.elte.fi.szakdolgozat.model.classes.Mage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static hu.elte.fi.szakdolgozat.model.GameConstants.*;
import static hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic.*;

public class AppleSlime extends Mage {

    private static final String name = "Apple Slime";
    private static final int health = 150;
    private static final int damageThresholdLower = 20;
    private static final int damageThresholdUpper = 30;
    private static final int range = HIGH_RANGE;
    private static final int cost = 2;
    private static final int mana = 50;

    /**
     * MOVING
     */
    private static final SpriteSheet shootingSlimeMoveUpSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeMoveUpAnimation);
    private final BufferedImage[] shootingSlimeMoveUpAnimationImages = {
            shootingSlimeMoveUpSpriteSheet.grabImage(1, 1, 50, 50),
            shootingSlimeMoveUpSpriteSheet.grabImage(2, 1, 50, 50),
            shootingSlimeMoveUpSpriteSheet.grabImage(3, 1, 50, 50),
            shootingSlimeMoveUpSpriteSheet.grabImage(4, 1, 50, 50),
            shootingSlimeMoveUpSpriteSheet.grabImage(5, 1, 50, 50),
            shootingSlimeMoveUpSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation shootingSlimeMoveUpAnimation = new Animation(shootingSlimeMoveUpAnimationImages, 10);

    private final SpriteSheet shootingSlimeMoveDownSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeMoveDownAnimation);
    private final BufferedImage[] shootingSlimeMoveDownAnimationImages = {
            shootingSlimeMoveDownSpriteSheet.grabImage(1, 1, 50, 50),
            shootingSlimeMoveDownSpriteSheet.grabImage(2, 1, 50, 50),
            shootingSlimeMoveDownSpriteSheet.grabImage(3, 1, 50, 50),
            shootingSlimeMoveDownSpriteSheet.grabImage(4, 1, 50, 50),
            shootingSlimeMoveDownSpriteSheet.grabImage(5, 1, 50, 50),
            shootingSlimeMoveDownSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation shootingSlimeMoveDownAnimation = new Animation(shootingSlimeMoveDownAnimationImages, 10);

    private final SpriteSheet shootingSlimeMoveRightSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeMoveRightAnimation);
    private final BufferedImage[] shootingSlimeMoveRightAnimationImages = {
            shootingSlimeMoveRightSpriteSheet.grabImage(1, 1, 50, 50),
            shootingSlimeMoveRightSpriteSheet.grabImage(2, 1, 50, 50),
            shootingSlimeMoveRightSpriteSheet.grabImage(3, 1, 50, 50),
            shootingSlimeMoveRightSpriteSheet.grabImage(4, 1, 50, 50),
            shootingSlimeMoveRightSpriteSheet.grabImage(5, 1, 50, 50),
            shootingSlimeMoveRightSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation shootingSlimeMoveRightAnimation = new Animation(shootingSlimeMoveRightAnimationImages, 10);

    private final SpriteSheet shootingSlimeMoveLeftSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeMoveLeftAnimation);
    private final BufferedImage[] shootingSlimeMoveLeftAnimationImages = {
            shootingSlimeMoveLeftSpriteSheet.grabImage(1, 1, 50, 50),
            shootingSlimeMoveLeftSpriteSheet.grabImage(2, 1, 50, 50),
            shootingSlimeMoveLeftSpriteSheet.grabImage(3, 1, 50, 50),
            shootingSlimeMoveLeftSpriteSheet.grabImage(4, 1, 50, 50),
            shootingSlimeMoveLeftSpriteSheet.grabImage(5, 1, 50, 50),
            shootingSlimeMoveLeftSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation shootingSlimeMoveLeftAnimation = new Animation(shootingSlimeMoveLeftAnimationImages, 10);

    /**
     * SHOOTING
     */

    private final SpriteSheet shootingSlimeShootFrontSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeAttackFrontAnimation);
    private final BufferedImage[] shootingSlimeShootFrontAnimationImages = {
            shootingSlimeShootFrontSpriteSheet.grabImage(1, 1, 50, 50),
            shootingSlimeShootFrontSpriteSheet.grabImage(2, 1, 50, 50),
            shootingSlimeShootFrontSpriteSheet.grabImage(3, 1, 50, 50),
            shootingSlimeShootFrontSpriteSheet.grabImage(4, 1, 50, 50),
            shootingSlimeShootFrontSpriteSheet.grabImage(5, 1, 50, 50),
            shootingSlimeShootFrontSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation shootingSlimeShootFrontAnimation = new Animation(shootingSlimeShootFrontAnimationImages, 10);

    private final SpriteSheet shootingSlimeShootBackSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeAttackBackAnimation);
    private final BufferedImage[] shootingSlimeShootBackAnimationImages = {
            shootingSlimeShootBackSpriteSheet.grabImage(1, 1, 50, 50),
            shootingSlimeShootBackSpriteSheet.grabImage(2, 1, 50, 50),
            shootingSlimeShootBackSpriteSheet.grabImage(3, 1, 50, 50),
            shootingSlimeShootBackSpriteSheet.grabImage(4, 1, 50, 50),
            shootingSlimeShootBackSpriteSheet.grabImage(5, 1, 50, 50),
            shootingSlimeShootBackSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation shootingSlimeShootBackAnimation = new Animation(shootingSlimeShootBackAnimationImages, 10);

    private final SpriteSheet shootingSlimeShootLeftSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeAttackLeftAnimation);
    private final BufferedImage[] shootingSlimeShootLeftAnimationImages = {
            shootingSlimeShootLeftSpriteSheet.grabImage(1, 1, 50, 50),
            shootingSlimeShootLeftSpriteSheet.grabImage(2, 1, 50, 50),
            shootingSlimeShootLeftSpriteSheet.grabImage(3, 1, 50, 50),
            shootingSlimeShootLeftSpriteSheet.grabImage(4, 1, 50, 50),
            shootingSlimeShootLeftSpriteSheet.grabImage(5, 1, 50, 50),
            shootingSlimeShootLeftSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation shootingSlimeShootLeftAnimation = new Animation(shootingSlimeShootLeftAnimationImages, 10);

    private final SpriteSheet shootingSlimeShootRightSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeAttackRightAnimation);
    private final BufferedImage[] shootingSlimeShootRightAnimationImages = {
            shootingSlimeShootRightSpriteSheet.grabImage(1, 1, 50, 50),
            shootingSlimeShootRightSpriteSheet.grabImage(2, 1, 50, 50),
            shootingSlimeShootRightSpriteSheet.grabImage(3, 1, 50, 50),
            shootingSlimeShootRightSpriteSheet.grabImage(4, 1, 50, 50),
            shootingSlimeShootRightSpriteSheet.grabImage(5, 1, 50, 50),
            shootingSlimeShootRightSpriteSheet.grabImage(6, 1, 50, 50),
    };
    private final Animation shootingSlimeShootRightAnimation = new Animation(shootingSlimeShootRightAnimationImages, 10);

    /**
     * SKILL ANIMATION
     */
    private final SpriteSheet shootingSlimeSkillDownSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeSkillDownProjectile);
    private final BufferedImage[] shootingSlimeSkillDownAnimationImages = {
            shootingSlimeSkillDownSpriteSheet.grabImage(1, 1, 80, 80),
            shootingSlimeSkillDownSpriteSheet.grabImage(2, 1, 80, 80),
            shootingSlimeSkillDownSpriteSheet.grabImage(3, 1, 80, 80),
            shootingSlimeSkillDownSpriteSheet.grabImage(4, 1, 80, 80),
            shootingSlimeSkillDownSpriteSheet.grabImage(5, 1, 80, 80),
    };
    private final Animation shootingSlimeSkillDownAnimation = new Animation(shootingSlimeSkillDownAnimationImages, 10);

    private final SpriteSheet shootingSlimeSkillUpSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeSkillUpProjectile);
    private final BufferedImage[] shootingSlimeSkillUpAnimationImages = {
            shootingSlimeSkillUpSpriteSheet.grabImage(1, 1, 80, 80),
            shootingSlimeSkillUpSpriteSheet.grabImage(2, 1, 80, 80),
            shootingSlimeSkillUpSpriteSheet.grabImage(3, 1, 80, 80),
            shootingSlimeSkillUpSpriteSheet.grabImage(4, 1, 80, 80),
            shootingSlimeSkillUpSpriteSheet.grabImage(5, 1, 80, 80),
    };
    private final Animation shootingSlimeSkillUpAnimation = new Animation(shootingSlimeSkillUpAnimationImages, 10);

    private final SpriteSheet shootingSlimeSkillLeftSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeSkillLeftProjectile);
    private final BufferedImage[] shootingSlimeSkillLeftAnimationImages = {
            shootingSlimeSkillLeftSpriteSheet.grabImage(1, 1, 80, 80),
            shootingSlimeSkillLeftSpriteSheet.grabImage(2, 1, 80, 80),
            shootingSlimeSkillLeftSpriteSheet.grabImage(3, 1, 80, 80),
            shootingSlimeSkillLeftSpriteSheet.grabImage(4, 1, 80, 80),
            shootingSlimeSkillLeftSpriteSheet.grabImage(5, 1, 80, 80),
    };
    private final Animation shootingSlimeSkillLeftAnimation = new Animation(shootingSlimeSkillLeftAnimationImages, 10);

    private final SpriteSheet shootingSlimeSkillRightSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeSkillRightProjectile);
    private final BufferedImage[] shootingSlimeSkillRightAnimationImages = {
            shootingSlimeSkillRightSpriteSheet.grabImage(1, 1, 80, 80),
            shootingSlimeSkillRightSpriteSheet.grabImage(2, 1, 80, 80),
            shootingSlimeSkillRightSpriteSheet.grabImage(3, 1, 80, 80),
            shootingSlimeSkillRightSpriteSheet.grabImage(4, 1, 80, 80),
            shootingSlimeSkillRightSpriteSheet.grabImage(5, 1, 80, 80),
    };
    private final Animation shootingSlimeSkillRightAnimation = new Animation(shootingSlimeSkillRightAnimationImages, 10);

    /**
     * DEATH ANIMATION
     */
    private final SpriteSheet shootingSlimeDeathSpriteSheet = new SpriteSheet(UIConstants.shootingSlimeDeathAnimation);
    private final BufferedImage[] shootingSlimeDeathAnimationImages = {
            shootingSlimeDeathSpriteSheet.grabImage(1, 1, 50, 50),
            shootingSlimeDeathSpriteSheet.grabImage(2, 1, 50, 50),
            shootingSlimeDeathSpriteSheet.grabImage(3, 1, 50, 50),
            shootingSlimeDeathSpriteSheet.grabImage(4, 1, 50, 50),
            shootingSlimeDeathSpriteSheet.grabImage(5, 1, 50, 50),
            shootingSlimeDeathSpriteSheet.grabImage(6, 1, 50, 50),
            shootingSlimeDeathSpriteSheet.grabImage(7, 1, 50, 50),
            shootingSlimeDeathSpriteSheet.grabImage(8, 1, 50, 50),
            shootingSlimeDeathSpriteSheet.grabImage(9, 1, 50, 50),
            shootingSlimeDeathSpriteSheet.grabImage(10, 1, 50, 50),
            shootingSlimeDeathSpriteSheet.grabImage(11, 1, 50, 50),
            shootingSlimeDeathSpriteSheet.grabImage(12, 1, 50, 50),
    };
    private final Animation shootingSlimeDeathAnimation = new Animation(shootingSlimeDeathAnimationImages, 5);

    private final Animation projectileImage = new Animation(new BufferedImage[]{UIConstants.shootingSlimeAttackProjectile}, 10);
    private static final Image image = shootingSlimeMoveUpSpriteSheet.grabImage(1, 1, 50, 50);


    public AppleSlime(int x, int y) {
        super(image, x, y, health, mana, damageThresholdLower, damageThresholdUpper,
                range, cost, name);
        speed = 0.1;

        if (y <= GameConstants.FRAME_HEIGHT / 2) {
            animation = shootingSlimeMoveUpAnimation;
        } else {
            animation = shootingSlimeMoveDownAnimation;
        }
    }

    @Override
    protected void drawCharacter(Graphics g, Direction dir) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();
        drawAnimation(dir, shootingSlimeMoveUpAnimation, shootingSlimeMoveDownAnimation, shootingSlimeMoveRightAnimation, shootingSlimeMoveLeftAnimation);
        animation.start();

        g2d.drawImage(animation.getSprite(), x, y, GameConstants.CHARACTER_SIZE, GameConstants.CHARACTER_SIZE, null);
    }

    @Override
    public void shoot(Character target, int value) {
        getCharactersBullet().add(new Bullet(x + 20, y + 20, target, value, projectileImage));
    }

    @Override
    public void drawDeath(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();
        animation = shootingSlimeDeathAnimation;
        animation.start();
        g2d.drawImage(animation.getSprite(), x, y, GameConstants.CHARACTER_SIZE, GameConstants.CHARACTER_SIZE, null);
    }

    @Override
    protected void drawSkill(Graphics g) {
    }

    @Override
    protected void drawAttack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        animation.stop();
        drawAnimation(dir, shootingSlimeShootFrontAnimation, shootingSlimeShootBackAnimation, shootingSlimeShootRightAnimation, shootingSlimeShootLeftAnimation);

        animation.start();

        g2d.drawImage(animation.getSprite(), x, y, GameConstants.CHARACTER_SIZE, GameConstants.CHARACTER_SIZE, null);
    }

    @Override
    protected void mageSkill(Character target) {
        int value = 50;
        Animation image = shootingSlimeSkillUpAnimation;
        if (dir == Direction.SOUTH) {
            image = shootingSlimeSkillDownAnimation;
        } else if (dir == Direction.EAST) {
            image = shootingSlimeSkillRightAnimation;
        } else if (dir == Direction.WEST) {
            image = shootingSlimeSkillLeftAnimation;
        }
        getCharactersBullet().add(new Bullet(x + 20, y + 20, target, value * level, image));
    }
}

