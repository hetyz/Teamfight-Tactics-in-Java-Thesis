package hu.elte.fi.szakdolgozat.model;

import hu.elte.fi.szakdolgozat.gui.UIConstants;
import hu.elte.fi.szakdolgozat.gui.sprite.characters.Saint;
import hu.elte.fi.szakdolgozat.resources.ResourceLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @BeforeEach
    public void setUp() {
        UIConstants.saintMoveDownAnimation = ResourceLoader.readImage("images/characters/saint/move/saintMoveDown.png");
        UIConstants.saintMoveUpAnimation = ResourceLoader.readImage("images/characters/saint/move/saintMoveUp.png");
        UIConstants.saintMoveLeftAnimation = ResourceLoader.readImage("images/characters/saint/move/saintMoveLeft.png");
        UIConstants.saintMoveRightAnimation = ResourceLoader.readImage("images/characters/saint/move/saintMoveRight.png");
        UIConstants.saintAttackDownAnimation = ResourceLoader.readImage("images/characters/saint/attack/saintAttackDown.png");
        UIConstants.saintAttackUpAnimation = ResourceLoader.readImage("images/characters/saint/attack/saintAttackUp.png");
        UIConstants.saintAttackLeftAnimation = ResourceLoader.readImage("images/characters/saint/attack/saintAttackLeft.png");
        UIConstants.saintAttackRightAnimation = ResourceLoader.readImage("images/characters/saint/attack/saintAttackRight.png");
        UIConstants.saintDeathDownAnimation = ResourceLoader.readImage("images/characters/saint/death/saintDeathDown.png");
        UIConstants.saintDeathUpAnimation = ResourceLoader.readImage("images/characters/saint/death/saintDeathUp.png");
        UIConstants.saintDeathLeftAnimation = ResourceLoader.readImage("images/characters/saint/death/saintDeathLeft.png");
        UIConstants.saintDeathRightAnimation = ResourceLoader.readImage("images/characters/saint/death/saintDeathRight.png");
        UIConstants.saintSkillDownAnimation = ResourceLoader.readImage("images/characters/saint/skill/saintSkillDown.png");
        UIConstants.saintSkillUpAnimation = ResourceLoader.readImage("images/characters/saint/skill/saintSkillUp.png");
        UIConstants.saintSkillLeftAnimation = ResourceLoader.readImage("images/characters/saint/skill/saintSkillLeft.png");
        UIConstants.saintSkillRightAnimation = ResourceLoader.readImage("images/characters/saint/skill/saintSkillRight.png");
    }

    @Test
    @DisplayName("Set level")
    void setLevel() {
        Saint saint = new Saint(10,10);
        int health = saint.getMaxHealth();
        int damageLow = saint.getDamageThresholdLower();
        int damageHigh = saint.getDamageThresholdUpper();

        saint.setLevel(2);
        assertEquals(2, saint.getLevel(), "Level two test");

        assertEquals((int) (damageHigh * saint.level * 0.75), saint.getDamageThresholdUpper());
        assertEquals((int) (damageLow * saint.level * 0.75), saint.getDamageThresholdLower());
        assertEquals((int) (health * saint.level * 0.75), saint.maxHealth);

        health = saint.getMaxHealth();
        damageLow = saint.getDamageThresholdLower();
        damageHigh = saint.getDamageThresholdUpper();

        saint.setLevel(4);
        assertEquals(3, saint.getLevel(), "Level higher than 3 test");

        assertEquals((int) (damageLow * 3 * 0.75), saint.getDamageThresholdLower());
        assertEquals((int) (damageHigh * 3 * 0.75), saint.getDamageThresholdUpper());
        assertEquals((int) (health * 3 * 0.75), saint.maxHealth);
    }

    @Test
    @DisplayName("Add health method with and without overheal")
    void addHealth() {
        Saint saint = new Saint(10,10);
        saint.removeHealth(40);
        saint.addHealth(40);
        assertEquals(250, saint.getHealth(), "Should be 250 without overheal");
        saint.removeHealth(40);
        saint.addHealth(60);
        assertEquals(saint.maxHealth, saint.getHealth(), "Should be 250 with overheal");
    }

    @Test
    @DisplayName("Set health to max")
    void setMaxHealth() {
        Saint saint = new Saint(10,10);
        saint.removeHealth(60);
        saint.setMaxHealth();
        assertEquals(saint.maxHealth, saint.getHealth());
    }

    @RepeatedTest(5)
    @DisplayName("Remove health")
    void removeHealth() {
        Saint saint = new Saint(10,10);
        saint.removeHealth(20);
        assertEquals(saint.maxHealth - 20, saint.getHealth());

        saint.setMaxHealth();

        saint.removeHealth(300);
        assertEquals(saint.maxHealth - 300, saint.getHealth());
        assertTrue(saint.isDead());
    }

    @Test
    @DisplayName("Closest and farthest target calculate")
    void calculateTarget() {
        Saint saint = new Saint(10,10);
        Saint saint2 = new Saint(10,10);
        Saint saint3 = new Saint(10,40);

        TeamfightTacticsLogic.getFightingEnemies().add(saint);
        TeamfightTacticsLogic.getFightingAlliances().add(saint2);

        saint.calculateTarget("MIN");
        assertEquals(saint2, saint.getTargetEnemyCharacter());


        saint.targetEnemyCharacter = null;
        TeamfightTacticsLogic.getFightingAlliances().add(saint3);
        saint.calculateTarget("MIN");

        assertEquals(saint2, saint.getTargetEnemyCharacter());

        saint.targetEnemyCharacter = null;
        saint.calculateTarget("MAX");
        assertEquals(saint3, saint.getTargetEnemyCharacter());
    }
}