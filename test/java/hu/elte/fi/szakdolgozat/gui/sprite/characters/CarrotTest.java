package hu.elte.fi.szakdolgozat.gui.sprite.characters;

import hu.elte.fi.szakdolgozat.gui.UIConstants;
import hu.elte.fi.szakdolgozat.resources.ResourceLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarrotTest {

    @BeforeEach
    public void setUp() {
        UIConstants.carrotMoveDownAnimation = ResourceLoader.readImage("images/characters/carrot/move/carrotMoveBack.png");
        UIConstants.carrotMoveUpAnimation = ResourceLoader.readImage("images/characters/carrot/move/carrotMoveFront.png");
        UIConstants.carrotMoveLeftAnimation = ResourceLoader.readImage("images/characters/carrot/move/carrotMoveLeft.png");
        UIConstants.carrotMoveRightAnimation = ResourceLoader.readImage("images/characters/carrot/move/carrotMoveRight.png");
        UIConstants.carrotAttackUpAnimation = ResourceLoader.readImage("images/characters/carrot/attack/carrotAttackUp.png");
        UIConstants.carrotAttackDownAnimation = ResourceLoader.readImage("images/characters/carrot/attack/carrotAttackDown.png");
        UIConstants.carrotAttackLeftAnimation = ResourceLoader.readImage("images/characters/carrot/attack/carrotAttackLeft.png");
        UIConstants.carrotAttackRightAnimation = ResourceLoader.readImage("images/characters/carrot/attack/carrotAttackRight.png");
        UIConstants.carrotDeathAnimation = ResourceLoader.readImage("images/characters/carrot/death/carrotDeath.png");
        UIConstants.carrotSkillAnimation = ResourceLoader.readImage("images/characters/carrot/skill/carrotSkill.png");
    }

    @Test
    void assassinSkill() {
        Carrot carrot = new Carrot(10,10);
        Carrot carrot2 = new Carrot(10,10);

        carrot.assassinSkill(carrot2, 35);

        assertEquals(carrot2.getMaxHealth() - 70, carrot2.getHealth());

        carrot.assassinSkill(carrot2, 70);
        assertTrue(carrot2.isDead());
    }
}