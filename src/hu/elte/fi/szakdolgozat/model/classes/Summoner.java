package hu.elte.fi.szakdolgozat.model.classes;

import hu.elte.fi.szakdolgozat.model.Character;

import java.awt.*;

public abstract class Summoner extends Character {

    protected Summoner(Image image, int x, int y, int health, int mana, int damageThresholdLower, int damageThresholdUpper, int range, int cost, String name) {
        super(image, x, y, health, mana, damageThresholdLower, damageThresholdUpper, range, cost, name);
    }

    protected abstract void shoot(Character character, int value);

    @Override
    protected void performAction(Character target, int value) {

        if(mana.get() != 100) {
            addMana(25);
            shoot(target, value);
        } else {
            mana.set(0);
            summoningFriend();
        }
        int waitTime = getRandomNumber(1300, 1500);
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract void summoningFriend();
}

