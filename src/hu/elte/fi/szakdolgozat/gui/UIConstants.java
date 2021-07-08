package hu.elte.fi.szakdolgozat.gui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIConstants {
    public static final String TITLE = "Teamfight Tactics";
    public static final String SELL_CARD = "SELL_CARD";
    public static final String REFRESH_CARD = "REFRESH_CARD";
    public static final String LEVELING_CARD = "LEVELING_CARD";
    public static final String CHARACTER_CARD = "CHARACTER_CARD";
    public static final String LOCK_CARD = "LOCK_CARD";
    public static final String EXIT_QUESTION = "   Are you sure,\nyou want to exit?";
    public static final String HELP_SENTENCES = "You can move your own character with Right Mouse Click to the " +
            "\nclicked position, use it for picking up the dropped gold from\n" +
            "the enemy. Use left mouse click to buy fighting unit, level up your\ncharacter or refresh the buyable units. You can drag and drop\n" +
            "the characters to the game panel.";
    public static final String OPTIONS = "Options";
    public static final String HELP = "Help";
    public static final String EXIT = "Exit";
    public static final String DATA = "Data";
    public static final String CURRENT_DATA = "Current data:";
    public static final String KILLS = "Kills: ";
    public static final String DEATH = "Death: ";
    public static final String WINS = "Wins: ";
    public static final String LOSES = "Loses: ";
    public static final String GOLDS = "Gold: ";
    public static final String SPENT_GOLD = "Spent gold: ";
    public static final String GAME_OVER = "Game Over!";
    public static BufferedImage wiseMoveAnimation;
    public static BufferedImage wiseProjectileAnimation;
    public static BufferedImage PLAYER_SPRITESHEET;
    public static BufferedImage goldAnimation;
    /**SHOOTING SLIME*/
    public static BufferedImage shootingSlimeMoveUpAnimation;
    public static BufferedImage shootingSlimeMoveDownAnimation;
    public static BufferedImage shootingSlimeMoveLeftAnimation;
    public static BufferedImage shootingSlimeMoveRightAnimation;
    public static BufferedImage shootingSlimeAttackFrontAnimation;
    public static BufferedImage shootingSlimeAttackBackAnimation;
    public static BufferedImage shootingSlimeAttackLeftAnimation;
    public static BufferedImage shootingSlimeAttackRightAnimation;
    public static BufferedImage shootingSlimeDeathAnimation;
    public static BufferedImage shootingSlimeAttackProjectile;
    public static BufferedImage shootingSlimeSkillUpProjectile;
    public static BufferedImage shootingSlimeSkillDownProjectile;
    public static BufferedImage shootingSlimeSkillLeftProjectile;
    public static BufferedImage shootingSlimeSkillRightProjectile;
    /**CARROT CHARACTER*/
    public static BufferedImage carrotMoveUpAnimation;
    public static BufferedImage carrotMoveDownAnimation;
    public static BufferedImage carrotMoveLeftAnimation;
    public static BufferedImage carrotMoveRightAnimation;
    public static BufferedImage carrotAttackUpAnimation;
    public static BufferedImage carrotAttackDownAnimation;
    public static BufferedImage carrotAttackLeftAnimation;
    public static BufferedImage carrotAttackRightAnimation;
    public static BufferedImage carrotDeathAnimation;
    public static BufferedImage carrotSkillAnimation;
    /**SPIKED SLIME*/
    public static BufferedImage spikedSlimeMoveUpAnimation;
    public static BufferedImage spikedSlimeMoveDownAnimation;
    public static BufferedImage spikedSlimeDeathAnimation;
    public static BufferedImage spikedSlimeAttackAnimation;
    public static BufferedImage spikedSlimeSkillAnimation;
    /**SAINT*/
    public static BufferedImage saintDeathUpAnimation;
    public static BufferedImage saintDeathDownAnimation;
    public static BufferedImage saintDeathLeftAnimation;
    public static BufferedImage saintDeathRightAnimation;
    public static BufferedImage saintSkillUpAnimation;
    public static BufferedImage saintSkillDownAnimation;
    public static BufferedImage saintSkillRightAnimation;
    public static BufferedImage saintSkillLeftAnimation;
    public static BufferedImage saintMoveUpAnimation;
    public static BufferedImage saintMoveLeftAnimation;
    public static BufferedImage saintMoveRightAnimation;
    public static BufferedImage saintMoveDownAnimation;
    public static BufferedImage saintAttackUpAnimation;
    public static BufferedImage saintAttackDownAnimation;
    public static BufferedImage saintAttackLeftAnimation;
    public static BufferedImage saintAttackRightAnimation;
    /**RED SLIME*/
    public static BufferedImage redMoveUpAnimation;
    public static BufferedImage redMoveDownAnimation;
    public static BufferedImage redMoveLeftAnimation;
    public static BufferedImage redMoveRightAnimation;
    public static BufferedImage redAttackUpAnimation;
    public static BufferedImage redAttackDownAnimation;
    public static BufferedImage redAttackLeftAnimation;
    public static BufferedImage redAttackRightAnimation;
    public static BufferedImage redDeathAnimation;
    /**PURPLE SLIME*/
    public static BufferedImage purpleMoveUpAnimation;
    public static BufferedImage purpleMoveDownAnimation;
    public static BufferedImage purpleMoveLeftAnimation;
    public static BufferedImage purpleMoveRightAnimation;
    public static BufferedImage purpleAttackUpAnimation;
    public static BufferedImage purpleAttackDownAnimation;
    public static BufferedImage purpleAttackLeftAnimation;
    public static BufferedImage purpleAttackRightAnimation;
    public static BufferedImage purpleDeathAnimation;
    /**GAME PANEL*/
    public static BufferedImage lockOn;
    public static BufferedImage lockOff;
    public static BufferedImage infoPanel;
    /**COLORS*/
    public static final Color hoverAndTitleColor = new Color(255,162,20);
    public static final Color frameColor = new Color(94,55,28);
    public static final Color levelColor = new Color(235,213,192);
    /**FONTS*/
    public static Font standardFont = new Font("TimesRoman", Font.BOLD, 12);
    public static Font sellFont = new Font("TimesRoman", Font.PLAIN, 20);
}
