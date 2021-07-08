package hu.elte.fi.szakdolgozat.model.classes;

import hu.elte.fi.szakdolgozat.model.Character;

import java.awt.*;

public abstract class Assassin extends Character {

    private static boolean atEarlyGame;

    public static boolean isAtEarlyGame() {
        return atEarlyGame;
    }

    public static void setAtEarlyGame(boolean atEarlyGame) {
        Assassin.atEarlyGame = atEarlyGame;
    }

    protected Assassin(Image image, int x, int y, int health, int mana, int damageThresholdLower, int damageThresholdUpper, int range, int cost, String name) {
        super(image, x, y, health, mana, damageThresholdLower, damageThresholdUpper, range, cost, name);
    }

    @Override
    protected void performAction(Character target, int value) {
        int waitTime = getRandomNumber(1300, 1500);

        if (mana.get() != 100) {
            useAttack = true;
            addMana(50);
            target.removeHealth(value);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitTime -= 500;
            useAttack = false;
        } else {
            mana.set(0);
            assassinSkill(target, value);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitTime -= 500;
            useSkill = false;
        }

        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract void assassinSkill(Character target, int value);
}

