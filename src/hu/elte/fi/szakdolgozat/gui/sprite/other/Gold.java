package hu.elte.fi.szakdolgozat.gui.sprite.other;

import hu.elte.fi.szakdolgozat.gui.sprite.Sprite;
import hu.elte.fi.szakdolgozat.gui.sprite.SpriteSheet;
import hu.elte.fi.szakdolgozat.model.animation.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

import static hu.elte.fi.szakdolgozat.gui.UIConstants.goldAnimation;

public class Gold extends Sprite {

    private final SpriteSheet animationSpriteSheet = new SpriteSheet(goldAnimation);
    private final BufferedImage[] animationImages = {
            animationSpriteSheet.grabImage(1, 1, 20, 20),
            animationSpriteSheet.grabImage(2, 1, 20, 20),
            animationSpriteSheet.grabImage(3, 1, 20, 20),
            animationSpriteSheet.grabImage(4, 1, 20, 20),
    };
    private final Animation animation = new Animation(animationImages, 10);

    public Animation getAnimation() {
        return animation;
    }

    public Gold(int x, int y, Image image) {
        super(x, y, image);
        animation.start();
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(animation.getSprite(), x + 10, y + 10, animation.getSprite().getWidth(), animation.getSprite().getHeight(), null);
    }

}
