package hu.elte.fi.szakdolgozat.model.classes;

import hu.elte.fi.szakdolgozat.model.Character;

import java.awt.*;

public abstract class Mage extends Character {

    protected Mage(Image image, int x, int y, int health, int mana, int damageThresholdLower, int damageThresholdUpper, int range, int cost, String name) {
        super(image, x, y, health, mana, damageThresholdLower, damageThresholdUpper, range, cost, name);
    }

    public abstract void shoot(Character target, int value);

    @Override
    protected void performAction(Character target, int value) {

        int waitTime = getRandomNumber(1300, 1500);

        if(mana.get() != 100) {
            useAttack = true;
            addMana(25);

            shoot(target, value);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitTime -= 800;
            useAttack = false;
        } else {
            mana.set(0);
            mageSkill(target);
        }

        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract void mageSkill(Character target);
}

