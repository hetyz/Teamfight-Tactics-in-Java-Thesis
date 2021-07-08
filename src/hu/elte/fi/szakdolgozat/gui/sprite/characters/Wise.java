package hu.elte.fi.szakdolgozat.gui.sprite.characters;

import hu.elte.fi.szakdolgozat.gui.UIConstants;
import hu.elte.fi.szakdolgozat.gui.sprite.other.Bullet;
import hu.elte.fi.szakdolgozat.gui.sprite.SpriteSheet;
import hu.elte.fi.szakdolgozat.model.GameConstants;
import hu.elte.fi.szakdolgozat.model.animation.Animation;
import hu.elte.fi.szakdolgozat.model.Direction;
import hu.elte.fi.szakdolgozat.model.Character;
import hu.elte.fi.szakdolgozat.model.classes.Summoner;

import java.awt.*;
import java.awt.image.BufferedImage;

import static hu.elte.fi.szakdolgozat.model.GameConstants.*;
import static hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic.*;

public class Wise extends Summoner {

    private static final String name = "Wise";
    private static final int health = 125;
    private static final int damageThresholdLower = 15;
    private static final int damageThresholdUpper = 35;
    private static final int range = MID_RANGE;
    private static final int cost = 3;
    private static final int mana = 25;

    private static final SpriteSheet ss = new SpriteSheet(UIConstants.wiseMoveAnimation);
    private final BufferedImage[] moveDownImages = {ss.grabImage(1, 1, 32, 32), ss.grabImage(2, 1, 32, 32), ss.grabImage(3, 1, 32, 32)};
    private final BufferedImage[] MoveUpImages = {ss.grabImage(1, 4, 32, 32), ss.grabImage(2, 4, 32, 32), ss.grabImage(3, 4, 32, 32)};
    private final BufferedImage[] MoveLeftImages = {ss.grabImage(1, 2, 32, 32), ss.grabImage(2, 2, 32, 32), ss.grabImage(3, 2, 32, 32)};
    private final BufferedImage[] MoveRightImages = {ss.grabImage(1, 3, 32, 32), ss.grabImage(2, 3, 32, 32), ss.grabImage(3, 3, 32, 32)};
    private final Animation moveDownAnimation = new Animation(moveDownImages, 10);
    private final Animation moveUpAnimation = new Animation(MoveUpImages, 10);
    private final Animation moveLeftAnimation = new Animation(MoveLeftImages, 10);
    private final Animation moveRightAnimation = new Animation(MoveRightImages, 10);

    private final Animation projectileImage = new Animation(new BufferedImage[]{UIConstants.wiseProjectileAnimation}, 10);

    public Wise(int x, int y) {
        super(ss.grabImage(1, 1, 32, 32), x, y, health, mana, damageThresholdLower, damageThresholdUpper,
                range, cost, name);
        speed = 0.1;
        if (y <= GameConstants.FRAME_HEIGHT / 2) {
            animation = moveDownAnimation;
        } else {
            animation = moveUpAnimation;
        }
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
        g2d.drawImage(animation.getSprite(), x, y, GameConstants.CHARACTER_SIZE, GameConstants.CHARACTER_SIZE, null);
    }

    @Override
    protected void drawDeath(Graphics g) {

    }

    @Override
    protected void drawSkill(Graphics g) {
    }

    @Override
    protected void drawAttack(Graphics g) {

    }

    @Override
    public void shoot(Character target, int value) {
        getCharactersBullet().add(new Bullet(x + 20, y + 20, target, value, projectileImage));
    }

    @Override
    protected void summoningFriend() {

        if (getEnemies().contains(this)) {
            new PurpleSlime(x, y + 50, "E", getLevel());
        } else if (getFightingAlliances().contains(this)) {
            new PurpleSlime(x, y - 50, "A", getLevel());
        }
    }
}

