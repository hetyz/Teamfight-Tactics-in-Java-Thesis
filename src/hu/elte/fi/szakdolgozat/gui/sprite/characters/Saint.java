package hu.elte.fi.szakdolgozat.gui.sprite.characters;

import hu.elte.fi.szakdolgozat.gui.UIConstants;
import hu.elte.fi.szakdolgozat.gui.sprite.SpriteSheet;
import hu.elte.fi.szakdolgozat.model.GameConstants;
import hu.elte.fi.szakdolgozat.model.animation.Animation;
import hu.elte.fi.szakdolgozat.model.Direction;
import hu.elte.fi.szakdolgozat.model.Character;
import hu.elte.fi.szakdolgozat.model.classes.Healer;

import java.awt.*;
import java.awt.image.BufferedImage;

import static hu.elte.fi.szakdolgozat.model.GameConstants.MELEE_RANGE;
import static hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic.getFightingAlliances;
import static hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic.getFightingEnemies;

public class Saint extends Healer {
    private static final String name = "Saint";
    private static final int health = 250;
    private static final int damageThresholdLower = 15;
    private static final int damageThresholdUpper = 20;
    private static final int range = MELEE_RANGE;
    private static final int cost = 3;
    private static final int mana = 20;

    /**
     * MOVE
     */
    private final SpriteSheet saintMoveUpSpriteSheet = new SpriteSheet(UIConstants.saintMoveUpAnimation);
    private final BufferedImage[] saintMoveUpAnimationImages = {
            saintMoveUpSpriteSheet.grabImage(1, 1, 80, 80),
            saintMoveUpSpriteSheet.grabImage(2, 1, 80, 80),
            saintMoveUpSpriteSheet.grabImage(3, 1, 80, 80),
            saintMoveUpSpriteSheet.grabImage(4, 1, 80, 80),
            saintMoveUpSpriteSheet.grabImage(5, 1, 80, 80),
            saintMoveUpSpriteSheet.grabImage(6, 1, 80, 80),
    };
    private final Animation saintMoveUpAnimation = new Animation(saintMoveUpAnimationImages, 10);

    private static final SpriteSheet saintMoveDownSpriteSheet = new SpriteSheet(UIConstants.saintMoveDownAnimation);
    private final BufferedImage[] saintMoveDownAnimationImages = {
            saintMoveDownSpriteSheet.grabImage(1, 1, 80, 80),
            saintMoveDownSpriteSheet.grabImage(2, 1, 80, 80),
            saintMoveDownSpriteSheet.grabImage(3, 1, 80, 80),
            saintMoveDownSpriteSheet.grabImage(4, 1, 80, 80),
            saintMoveDownSpriteSheet.grabImage(5, 1, 80, 80),
            saintMoveDownSpriteSheet.grabImage(6, 1, 80, 80),
    };
    private final Animation saintMoveDownAnimation = new Animation(saintMoveDownAnimationImages, 10);


    private final SpriteSheet saintMoveLeftSpriteSheet = new SpriteSheet(UIConstants.saintMoveLeftAnimation);
    private final BufferedImage[] saintMoveLeftAnimationImages = {
            saintMoveLeftSpriteSheet.grabImage(1, 1, 80, 80),
            saintMoveLeftSpriteSheet.grabImage(2, 1, 80, 80),
            saintMoveLeftSpriteSheet.grabImage(3, 1, 80, 80),
            saintMoveLeftSpriteSheet.grabImage(4, 1, 80, 80),
            saintMoveLeftSpriteSheet.grabImage(5, 1, 80, 80),
            saintMoveLeftSpriteSheet.grabImage(6, 1, 80, 80),
    };
    private final Animation saintMoveLeftAnimation = new Animation(saintMoveLeftAnimationImages, 10);

    private final SpriteSheet saintMoveRightSpriteSheet = new SpriteSheet(UIConstants.saintMoveRightAnimation);
    private final BufferedImage[] saintMoveRightAnimationImages = {
            saintMoveRightSpriteSheet.grabImage(1, 1, 80, 80),
            saintMoveRightSpriteSheet.grabImage(2, 1, 80, 80),
            saintMoveRightSpriteSheet.grabImage(3, 1, 80, 80),
            saintMoveRightSpriteSheet.grabImage(4, 1, 80, 80),
            saintMoveRightSpriteSheet.grabImage(5, 1, 80, 80),
            saintMoveRightSpriteSheet.grabImage(6, 1, 80, 80),
    };
    private final Animation saintMoveRightAnimation = new Animation(saintMoveRightAnimationImages, 10);

    /**
     * ATTACK
     */
    private final SpriteSheet saintAttackUpSpriteSheet = new SpriteSheet(UIConstants.saintAttackUpAnimation);
    private final BufferedImage[] saintAttackUpAnimationImages = {
            saintAttackUpSpriteSheet.grabImage(1, 1, 80, 80),
            saintAttackUpSpriteSheet.grabImage(2, 1, 80, 80),
            saintAttackUpSpriteSheet.grabImage(3, 1, 80, 80),
            saintAttackUpSpriteSheet.grabImage(4, 1, 80, 80),
            saintAttackUpSpriteSheet.grabImage(5, 1, 80, 80),
            saintAttackUpSpriteSheet.grabImage(6, 1, 80, 80),
    };
    private final Animation saintAttackUpAnimation = new Animation(saintAttackUpAnimationImages, 10);

    private final SpriteSheet saintAttackDownSpriteSheet = new SpriteSheet(UIConstants.saintAttackDownAnimation);
    private final BufferedImage[] saintAttackDownAnimationImages = {
            saintAttackDownSpriteSheet.grabImage(1, 1, 80, 80),
            saintAttackDownSpriteSheet.grabImage(2, 1, 80, 80),
            saintAttackDownSpriteSheet.grabImage(3, 1, 80, 80),
            saintAttackDownSpriteSheet.grabImage(4, 1, 80, 80),
            saintAttackDownSpriteSheet.grabImage(5, 1, 80, 80),
            saintAttackDownSpriteSheet.grabImage(6, 1, 80, 80),
    };
    private final Animation saintAttackDownAnimation = new Animation(saintAttackDownAnimationImages, 10);

    private final SpriteSheet saintAttackLeftSpriteSheet = new SpriteSheet(UIConstants.saintAttackLeftAnimation);
    private final BufferedImage[] saintAttackLeftAnimationImages = {
            saintAttackLeftSpriteSheet.grabImage(1, 1, 80, 80),
            saintAttackLeftSpriteSheet.grabImage(2, 1, 80, 80),
            saintAttackLeftSpriteSheet.grabImage(3, 1, 80, 80),
            saintAttackLeftSpriteSheet.grabImage(4, 1, 80, 80),
            saintAttackLeftSpriteSheet.grabImage(5, 1, 80, 80),
            saintAttackLeftSpriteSheet.grabImage(6, 1, 80, 80),
    };
    private final Animation saintAttackLeftAnimation = new Animation(saintAttackLeftAnimationImages, 10);

    private final SpriteSheet saintAttackRightSpriteSheet = new SpriteSheet(UIConstants.saintAttackRightAnimation);
    private final BufferedImage[] saintAttackRightAnimationImages = {
            saintAttackRightSpriteSheet.grabImage(1, 1, 80, 80),
            saintAttackRightSpriteSheet.grabImage(2, 1, 80, 80),
            saintAttackRightSpriteSheet.grabImage(3, 1, 80, 80),
            saintAttackRightSpriteSheet.grabImage(4, 1, 80, 80),
            saintAttackRightSpriteSheet.grabImage(5, 1, 80, 80),
            saintAttackRightSpriteSheet.grabImage(6, 1, 80, 80),
    };
    private final Animation saintAttackRightAnimation = new Animation(saintAttackRightAnimationImages, 10);

    /**
     * SKILL
     */

    private final SpriteSheet saintSkillUpSpriteSheet = new SpriteSheet(UIConstants.saintSkillUpAnimation);
    private final BufferedImage[] saintSkillUpAnimationImages = {
            saintSkillUpSpriteSheet.grabImage(1, 1, 80, 80),
            saintSkillUpSpriteSheet.grabImage(2, 1, 80, 80),
            saintSkillUpSpriteSheet.grabImage(3, 1, 80, 80),
            saintSkillUpSpriteSheet.grabImage(4, 1, 80, 80),
            saintSkillUpSpriteSheet.grabImage(5, 1, 80, 80),
            saintSkillUpSpriteSheet.grabImage(6, 1, 80, 80),
            saintSkillUpSpriteSheet.grabImage(7, 1, 80, 80),
            saintSkillUpSpriteSheet.grabImage(8, 1, 80, 80),
            saintSkillUpSpriteSheet.grabImage(9, 1, 80, 80),
            saintSkillUpSpriteSheet.grabImage(10, 1, 80, 80),
    };
    private final Animation saintSkillUpAnimation = new Animation(saintSkillUpAnimationImages, 4);

    private final SpriteSheet saintSkillDownSpriteSheet = new SpriteSheet(UIConstants.saintSkillDownAnimation);
    private final BufferedImage[] saintSkillDownAnimationImages = {
            saintSkillDownSpriteSheet.grabImage(1, 1, 80, 80),
            saintSkillDownSpriteSheet.grabImage(2, 1, 80, 80),
            saintSkillDownSpriteSheet.grabImage(3, 1, 80, 80),
            saintSkillDownSpriteSheet.grabImage(4, 1, 80, 80),
            saintSkillDownSpriteSheet.grabImage(5, 1, 80, 80),
            saintSkillDownSpriteSheet.grabImage(6, 1, 80, 80),
            saintSkillDownSpriteSheet.grabImage(7, 1, 80, 80),
            saintSkillDownSpriteSheet.grabImage(8, 1, 80, 80),
            saintSkillDownSpriteSheet.grabImage(9, 1, 80, 80),
            saintSkillDownSpriteSheet.grabImage(10, 1, 80, 80),
    };
    private final Animation saintSkillDownAnimation = new Animation(saintSkillDownAnimationImages, 4);


    private final SpriteSheet saintSkillLeftSpriteSheet = new SpriteSheet(UIConstants.saintSkillLeftAnimation);
    private final BufferedImage[] saintSkillLeftAnimationImages = {
            saintSkillLeftSpriteSheet.grabImage(1, 1, 80, 80),
            saintSkillLeftSpriteSheet.grabImage(2, 1, 80, 80),
            saintSkillLeftSpriteSheet.grabImage(3, 1, 80, 80),
            saintSkillLeftSpriteSheet.grabImage(4, 1, 80, 80),
            saintSkillLeftSpriteSheet.grabImage(5, 1, 80, 80),
            saintSkillLeftSpriteSheet.grabImage(6, 1, 80, 80),
            saintSkillLeftSpriteSheet.grabImage(7, 1, 80, 80),
            saintSkillLeftSpriteSheet.grabImage(8, 1, 80, 80),
            saintSkillLeftSpriteSheet.grabImage(9, 1, 80, 80),
            saintSkillLeftSpriteSheet.grabImage(10, 1, 80, 80),
    };
    private final Animation saintSkillLeftAnimation = new Animation(saintSkillLeftAnimationImages, 4);


    private final SpriteSheet saintSkillRightSpriteSheet = new SpriteSheet(UIConstants.saintSkillRightAnimation);
    private final BufferedImage[] saintSkillRightAnimationImages = {
            saintSkillRightSpriteSheet.grabImage(1, 1, 80, 80),
            saintSkillRightSpriteSheet.grabImage(2, 1, 80, 80),
            saintSkillRightSpriteSheet.grabImage(3, 1, 80, 80),
            saintSkillRightSpriteSheet.grabImage(4, 1, 80, 80),
            saintSkillRightSpriteSheet.grabImage(5, 1, 80, 80),
            saintSkillRightSpriteSheet.grabImage(6, 1, 80, 80),
            saintSkillRightSpriteSheet.grabImage(7, 1, 80, 80),
            saintSkillRightSpriteSheet.grabImage(8, 1, 80, 80),
            saintSkillRightSpriteSheet.grabImage(9, 1, 80, 80),
            saintSkillRightSpriteSheet.grabImage(10, 1, 80, 80),
    };
    private final Animation saintSkillRightAnimation = new Animation(saintSkillRightAnimationImages, 4);

    /**
     * DEATH
     */
    private final SpriteSheet saintDeathUpSpriteSheet = new SpriteSheet(UIConstants.saintDeathUpAnimation);
    private final BufferedImage[] saintDeathUpAnimationImages = {
            saintDeathUpSpriteSheet.grabImage(1, 1, 80, 80),
            saintDeathUpSpriteSheet.grabImage(2, 1, 80, 80),
            saintDeathUpSpriteSheet.grabImage(3, 1, 80, 80),
            saintDeathUpSpriteSheet.grabImage(4, 1, 80, 80),
            saintDeathUpSpriteSheet.grabImage(5, 1, 80, 80),
            saintDeathUpSpriteSheet.grabImage(6, 1, 80, 80),
            saintDeathUpSpriteSheet.grabImage(7, 1, 80, 80),
            saintDeathUpSpriteSheet.grabImage(8, 1, 80, 80),
    };
    private final Animation saintDeathUpAnimation = new Animation(saintDeathUpAnimationImages, 10);

    private final SpriteSheet saintDeathDownSpriteSheet = new SpriteSheet(UIConstants.saintDeathDownAnimation);
    private final BufferedImage[] saintDeathDownAnimationImages = {
            saintDeathDownSpriteSheet.grabImage(1, 1, 80, 80),
            saintDeathDownSpriteSheet.grabImage(2, 1, 80, 80),
            saintDeathDownSpriteSheet.grabImage(3, 1, 80, 80),
            saintDeathDownSpriteSheet.grabImage(4, 1, 80, 80),
            saintDeathDownSpriteSheet.grabImage(5, 1, 80, 80),
            saintDeathDownSpriteSheet.grabImage(6, 1, 80, 80),
            saintDeathDownSpriteSheet.grabImage(7, 1, 80, 80),
            saintDeathDownSpriteSheet.grabImage(8, 1, 80, 80),
    };
    private final Animation saintDeathDownAnimation = new Animation(saintDeathDownAnimationImages, 10);

    private final SpriteSheet saintDeathLeftSpriteSheet = new SpriteSheet(UIConstants.saintDeathLeftAnimation);
    private final BufferedImage[] saintDeathLeftAnimationImages = {
            saintDeathLeftSpriteSheet.grabImage(1, 1, 80, 80),
            saintDeathLeftSpriteSheet.grabImage(2, 1, 80, 80),
            saintDeathLeftSpriteSheet.grabImage(3, 1, 80, 80),
            saintDeathLeftSpriteSheet.grabImage(4, 1, 80, 80),
            saintDeathLeftSpriteSheet.grabImage(5, 1, 80, 80),
            saintDeathLeftSpriteSheet.grabImage(6, 1, 80, 80),
            saintDeathLeftSpriteSheet.grabImage(7, 1, 80, 80),
            saintDeathLeftSpriteSheet.grabImage(8, 1, 80, 80),
    };
    private final Animation saintDeathLeftAnimation = new Animation(saintDeathLeftAnimationImages, 10);


    private final SpriteSheet saintDeathRightSpriteSheet = new SpriteSheet(UIConstants.saintDeathRightAnimation);
    private final BufferedImage[] saintDeathRightAnimationImages = {
            saintDeathRightSpriteSheet.grabImage(1, 1, 80, 80),
            saintDeathRightSpriteSheet.grabImage(2, 1, 80, 80),
            saintDeathRightSpriteSheet.grabImage(3, 1, 80, 80),
            saintDeathRightSpriteSheet.grabImage(4, 1, 80, 80),
            saintDeathRightSpriteSheet.grabImage(5, 1, 80, 80),
            saintDeathRightSpriteSheet.grabImage(6, 1, 80, 80),
            saintDeathRightSpriteSheet.grabImage(7, 1, 80, 80),
            saintDeathRightSpriteSheet.grabImage(8, 1, 80, 80),
    };
    private final Animation saintDeathRightAnimation = new Animation(saintDeathRightAnimationImages, 10);

    private static final Image image = saintMoveDownSpriteSheet.grabImage(1, 1, 80, 80);

    public Saint(int x, int y) {
        super(image, x, y, health, mana, damageThresholdLower, damageThresholdUpper,
                range, cost, name);
        speed = 0.1;

        if (y <= GameConstants.FRAME_HEIGHT / 2) {
            animation = saintMoveDownAnimation;
        } else {
            animation = saintMoveUpAnimation;
        }

        skillAnimation = saintSkillUpAnimation;
    }

    @Override
    protected void healSkill() {
        useSkill = true;
        if(getFightingAlliances().contains(this)) {
            Character lowestHealthHero = getFightingAlliances().get(0);
            for (Character hero : getFightingAlliances()) {
                if (hero.getHealth() < lowestHealthHero.getHealth()) {
                    lowestHealthHero = hero;
                }
            }

            lowestHealthHero.addHealth(50 * level);
        } else if(getFightingEnemies().contains(this)) {
            Character lowestHealthEnemy = getFightingEnemies().get(0);
            for (Character enemy : getFightingEnemies()) {
                if (enemy.getHealth() < lowestHealthEnemy.getHealth()) {
                    lowestHealthEnemy = enemy;
                }
            }

            lowestHealthEnemy.addHealth(50 * level);
        }
    }

    @Override
    protected void drawDeath(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();
        drawAnimation(dir,
                saintDeathDownAnimation,
                saintDeathUpAnimation,
                saintDeathRightAnimation,
                saintDeathLeftAnimation);
        animation.start();
        g2d.drawImage(animation.getSprite(), x - 20, y - 20, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
    }

    @Override
    protected void drawSkill(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        skillAnimation.stop();

        if (dir == Direction.SOUTH) {
            skillAnimation = saintSkillDownAnimation;
        } else if (dir == Direction.NORTH) {
            skillAnimation = saintSkillUpAnimation;
        } else if (dir == Direction.EAST) {
            skillAnimation = saintSkillRightAnimation;
        } else if (dir == Direction.WEST) {
            skillAnimation = saintSkillLeftAnimation;
        }

        skillAnimation.start();
        g2d.drawImage(skillAnimation.getSprite(), x - 20, y - 20, skillAnimation.getSprite().getWidth(), skillAnimation.getSprite().getHeight(), null);
    }

    @Override
    protected void drawAttack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();
        drawAnimation(dir,
                saintAttackDownAnimation,
                saintAttackUpAnimation,
                saintAttackRightAnimation,
                saintAttackLeftAnimation);

        animation.start();
        g2d.drawImage(animation.getSprite(), x - 20, y - 20, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
    }

    @Override
    protected void drawCharacter(Graphics g, Direction dir) {
        if(!useSkill) {
            Graphics2D g2d = (Graphics2D) g;
            animation.stop();
            drawAnimation(dir,
                    saintMoveDownAnimation,
                    saintMoveUpAnimation,
                    saintMoveRightAnimation,
                    saintMoveLeftAnimation);

            animation.start();
            g2d.drawImage(animation.getSprite(), x - 20, y - 20, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
        }
    }
}
