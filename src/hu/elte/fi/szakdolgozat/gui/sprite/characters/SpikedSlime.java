package hu.elte.fi.szakdolgozat.gui.sprite.characters;

import hu.elte.fi.szakdolgozat.gui.UIConstants;
import hu.elte.fi.szakdolgozat.gui.sprite.SpriteSheet;
import hu.elte.fi.szakdolgozat.model.GameConstants;
import hu.elte.fi.szakdolgozat.model.animation.Animation;
import hu.elte.fi.szakdolgozat.model.Direction;
import hu.elte.fi.szakdolgozat.model.Character;
import hu.elte.fi.szakdolgozat.model.classes.Bruiser;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import static hu.elte.fi.szakdolgozat.model.GameConstants.MELEE_RANGE;
import static hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic.getEnemies;
import static hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic.getFightingAlliances;

public class SpikedSlime extends Bruiser {

    private static final String name = "Spiked Slime";
    private static final int health = 200;
    private static final int damageThresholdLower = 20;
    private static final int damageThresholdUpper = 30;
    private static final int range = MELEE_RANGE;
    private static final int cost = 3;
    private static final int mana = 0;
    private int skillDamage = 60;


    /**MOVE*/
    private final SpriteSheet moveUpSpriteSheet = new SpriteSheet(UIConstants.spikedSlimeMoveUpAnimation);
    private final BufferedImage[] moveUpAnimationImages = {
            moveUpSpriteSheet.grabImage(1, 1, 60, 60),
            moveUpSpriteSheet.grabImage(2, 1, 60, 60),
            moveUpSpriteSheet.grabImage(3, 1, 60, 60),
            moveUpSpriteSheet.grabImage(4, 1, 60, 60),
            moveUpSpriteSheet.grabImage(5, 1, 60, 60),
            moveUpSpriteSheet.grabImage(6, 1, 60, 60),
    };
    private final Animation moveUpAnimation = new Animation(moveUpAnimationImages, 10);

    private static final SpriteSheet moveDownSpriteSheet = new SpriteSheet(UIConstants.spikedSlimeMoveDownAnimation);
    private final BufferedImage[] moveDownAnimationImages = {
            moveDownSpriteSheet.grabImage(1, 1, 60, 60),
            moveDownSpriteSheet.grabImage(2, 1, 60, 60),
            moveDownSpriteSheet.grabImage(3, 1, 60, 60),
            moveDownSpriteSheet.grabImage(4, 1, 60, 60),
            moveDownSpriteSheet.grabImage(5, 1, 60, 60),
            moveDownSpriteSheet.grabImage(6, 1, 60, 60),
    };
    private final Animation moveDownAnimation = new Animation(moveDownAnimationImages, 10);

    /**ATTACK*/
    private final SpriteSheet attackSpriteSheet = new SpriteSheet(UIConstants.spikedSlimeAttackAnimation);
    private final BufferedImage[] attackAnimationImages = {
            attackSpriteSheet.grabImage(1, 1, 60, 60),
            attackSpriteSheet.grabImage(2, 1, 60, 60),
            attackSpriteSheet.grabImage(3, 1, 60, 60),
            attackSpriteSheet.grabImage(4, 1, 60, 60),
            attackSpriteSheet.grabImage(5, 1, 60, 60),
            attackSpriteSheet.grabImage(6, 1, 60, 60),
            attackSpriteSheet.grabImage(7, 1, 60, 60),
            attackSpriteSheet.grabImage(8, 1, 60, 60),
            attackSpriteSheet.grabImage(9, 1, 60, 60),
            attackSpriteSheet.grabImage(10, 1, 60, 60),
            attackSpriteSheet.grabImage(11, 1, 60, 60),
            attackSpriteSheet.grabImage(12, 1, 60, 60),
    };
    private final Animation attackAnimation = new Animation(attackAnimationImages, 4);

    /**DEATH*/
    private final SpriteSheet spikedDeathSpriteSheet = new SpriteSheet(UIConstants.spikedSlimeDeathAnimation);
    private final BufferedImage[] spikedDeathAnimationImages = {
            spikedDeathSpriteSheet.grabImage(1, 1, 60, 60),
            spikedDeathSpriteSheet.grabImage(2, 1, 60, 60),
            spikedDeathSpriteSheet.grabImage(3, 1, 60, 60),
            spikedDeathSpriteSheet.grabImage(4, 1, 60, 60),
            spikedDeathSpriteSheet.grabImage(5, 1, 60, 60),
            spikedDeathSpriteSheet.grabImage(6, 1, 60, 60),
            spikedDeathSpriteSheet.grabImage(7, 1, 60, 60),
            spikedDeathSpriteSheet.grabImage(8, 1, 60, 60),

    };
    private final Animation spikedDeathAnimation = new Animation(spikedDeathAnimationImages, 8);

    private static final Image image = moveDownSpriteSheet.grabImage(1, 1, 60, 60);

    public SpikedSlime(int x, int y) {
        super(image, x, y, health, mana, damageThresholdLower, damageThresholdUpper,
                range, cost, name);
        speed = 0.1;

        if (y <= GameConstants.FRAME_HEIGHT / 2) {
            animation = moveDownAnimation;
            dir = Direction.SOUTH;
        } else {
            animation = moveUpAnimation;
            dir = Direction.NORTH;
        }
        SpriteSheet spikedSkillSpriteSheet = new SpriteSheet(UIConstants.spikedSlimeSkillAnimation);
        BufferedImage[] spikedSkillAnimationImages = {
                spikedSkillSpriteSheet.grabImage(1, 1, 240, 240),
                spikedSkillSpriteSheet.grabImage(2, 1, 240, 240),
                spikedSkillSpriteSheet.grabImage(3, 1, 240, 240),

        };
        skillAnimation = new Animation(spikedSkillAnimationImages, 10);
    }

    @Override
    protected void drawDeath(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();
        animation = spikedDeathAnimation;
        animation.start();
        g2d.drawImage(animation.getSprite(), x- 10, y - 10, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
    }

    @Override
    protected void drawSkill(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        skillAnimation.stop();
        skillAnimation.start();

        g2d.drawImage(skillAnimation.getSprite(), xCenterOfOval() - 20, yCenterOfOval() - 20, skillAnimation.getSprite().getWidth(), skillAnimation.getSprite().getHeight(), null);
    }

    @Override
    protected void drawAttack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();

        animation = attackAnimation;

        animation.start();
        g2d.drawImage(animation.getSprite(), x - 10, y - 10, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
    }

    @Override
    protected void drawCharacter(Graphics g, Direction dir) {
        Graphics2D g2d = (Graphics2D) g;
        animation.stop();

        if(dir == Direction.NORTH) {
            animation = moveUpAnimation;
        } else {
            animation = moveDownAnimation;
        }
        animation.start();
        g2d.drawImage(animation.getSprite(), x - 10, y - 10, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
    }

    @Override
    protected void bruiserSkill(Character me) {
        Ellipse2D.Double skillRangeHardDmg = new Ellipse2D.Double(xCenterOfOval() - 20, yCenterOfOval() - 20,
                range + 80, range + 80);
        Ellipse2D.Double skillRangeLowDmg = new Ellipse2D.Double(xCenterOfOval() + 20, yCenterOfOval() + 20,
                range, range);
        useSkill = true;
        if (level == 2) {
            skillDamage = 120;
        } else if (level == 3) {
            skillDamage = 200;
        }
        if (getEnemies().contains(me)) {
            for (int i = 0; i < getFightingAlliances().size(); i++) {
                if (skillRangeLowDmg.contains(getFightingAlliances().get(i).getX(), getFightingAlliances().get(i).getY())) {
                    skillDamage -= level*10;
                    getFightingAlliances().get(i).removeHealth(skillDamage);
                } else if (skillRangeHardDmg.contains(getFightingAlliances().get(i).getX(), getFightingAlliances().get(i).getY())) {
                    getFightingAlliances().get(i).removeHealth(skillDamage);
                }
            }
        } else {
            for (int i = 0; i < getEnemies().size(); i++) {
                if (skillRangeLowDmg.contains(getEnemies().get(i).getX(), getEnemies().get(i).getY())) {
                    skillDamage /= 2;
                    getEnemies().get(i).removeHealth(skillDamage);
                } else if (skillRangeHardDmg.contains(getEnemies().get(i).getX(), getEnemies().get(i).getY())) {
                    getEnemies().get(i).removeHealth(skillDamage);
                }
            }
        }
    }
}


