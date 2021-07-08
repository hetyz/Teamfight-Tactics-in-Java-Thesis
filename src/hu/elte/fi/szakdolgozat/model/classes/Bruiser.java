package hu.elte.fi.szakdolgozat.model.classes;

import hu.elte.fi.szakdolgozat.model.Character;

import java.awt.*;

public abstract class Bruiser extends Character {
    protected Bruiser(Image image, int x, int y, int health, int mana, int damageThresholdLower, int damageThresholdUpper, int range, int cost, String name) {
        super(image, x, y, health, mana, damageThresholdLower, damageThresholdUpper, range, cost, name);
    }

    @Override
    protected void performAction(Character target, int value) {

        int waitTime = getRandomNumber(600, 1000);

        if (mana.get() != 100) {
            addMana(20);
            target.removeHealth(value);
        } else {
            mana.set(0);
            bruiserSkill(this);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitTime -= 250;
            useSkill = false;
        }

        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract void bruiserSkill(Character me);
}
