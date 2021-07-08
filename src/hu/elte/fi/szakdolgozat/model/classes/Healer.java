package hu.elte.fi.szakdolgozat.model.classes;

import hu.elte.fi.szakdolgozat.model.Character;

import java.awt.*;

public abstract class Healer extends Character {

    protected Healer(Image image, int x, int y, int health, int mana, int damageThresholdLower, int damageThresholdUpper, int range, int cost, String name) {
        super(image, x, y, health, mana, damageThresholdLower, damageThresholdUpper, range, cost, name);
    }

    protected abstract void healSkill();

    @Override
    protected void performAction(Character target, int value) {
        int waitTime = getRandomNumber(1000, 1500);

        if (mana.get() != 100) {
            addMana(20);
            target.removeHealth(value);
        } else {
            mana.set(0);
            healSkill();
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitTime -= 750;
            useSkill = false;
        }

        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
