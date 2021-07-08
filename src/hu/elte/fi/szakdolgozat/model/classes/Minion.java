package hu.elte.fi.szakdolgozat.model.classes;

import hu.elte.fi.szakdolgozat.model.Character;

import java.awt.*;

public abstract class Minion extends Character {

    protected Minion(Image image, int x, int y, int health, int mana, int damageThresholdLower, int damageThresholdUpper, int range, int cost, String name) {
        super(image, x, y, health, mana, damageThresholdLower, damageThresholdUpper, range, cost, name);
    }

    @Override
    protected void performAction(Character target, int value) {
        int waitTime = getRandomNumber(1000, 1500);

        if(mana.get() != 100) {
            useAttack = true;
            addMana(25);
            target.removeHealth(value);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitTime -= 800;
            useAttack = false;
        } else {
            mana.set(0);
            minionSkill();
        }

        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract void minionSkill();
}

