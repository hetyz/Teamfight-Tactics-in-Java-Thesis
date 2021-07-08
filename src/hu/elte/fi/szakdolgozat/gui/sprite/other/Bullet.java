package hu.elte.fi.szakdolgozat.gui.sprite.other;

import hu.elte.fi.szakdolgozat.gui.sprite.Sprite;
import hu.elte.fi.szakdolgozat.model.Character;
import hu.elte.fi.szakdolgozat.model.animation.Animation;

import java.awt.*;

import static hu.elte.fi.szakdolgozat.model.GameConstants.CHARACTER_SIZE;
import static hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic.*;

public class Bullet extends Sprite {
    private final int damage;
    private final Character target;
    private final Animation animation;

    public Animation getAnimation() {
        return animation;
    }

    public Bullet(int x, int y, Character target, int damage, Animation animation) {
        super(x, y, animation.getSprite());
        this.animation = animation;

        this.target = target;
        this.damage = damage;
    }


    public void update() {
        animation.stop();
        double dx = target.getX() - x + 15;
        double dy = target.getY() - y + 15;
        double angle = Math.atan2(dy, dx);
        double speed = 5.0;
        double xVelocity = (speed) * Math.cos(angle);
        double yVelocity = (speed) * Math.sin(angle);

        x += xVelocity;
        y += yVelocity;

        animation.start();
        bulletHit();
    }

    private void bulletHit() {
        if(y <= target.getY() + CHARACTER_SIZE &&
        x <= target.getX() + CHARACTER_SIZE &&
        x >= target.getX() && y >= target.getY()) {
            target.removeHealth(damage);
                getCharactersBullet().remove(this);
        }
        if(target.isDead()) {
            getCharactersBullet().remove(this);
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(animation.getSprite(), x - animation.getSprite().getWidth() / 2, y - animation.getSprite().getHeight() / 2, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
    }
}
