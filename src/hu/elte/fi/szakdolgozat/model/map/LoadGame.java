package hu.elte.fi.szakdolgozat.model.map;

import hu.elte.fi.szakdolgozat.gui.UIConstants;
import hu.elte.fi.szakdolgozat.gui.sprite.SpriteSheet;
import hu.elte.fi.szakdolgozat.model.FieldState;
import hu.elte.fi.szakdolgozat.model.GameState;
import hu.elte.fi.szakdolgozat.model.GameConstants;
import hu.elte.fi.szakdolgozat.model.Stopwatch;
import hu.elte.fi.szakdolgozat.model.TeamfightTacticsLogic;
import hu.elte.fi.szakdolgozat.model.animation.Animation;
import hu.elte.fi.szakdolgozat.gui.sprite.player.Player;
import hu.elte.fi.szakdolgozat.resources.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static hu.elte.fi.szakdolgozat.gui.UIConstants.PLAYER_SPRITESHEET;
import static hu.elte.fi.szakdolgozat.gui.UIConstants.goldAnimation;
import static hu.elte.fi.szakdolgozat.model.GameConstants.FIGHT_TIME;
import static hu.elte.fi.szakdolgozat.model.GameConstants.PLANNING_TIME;

public class LoadGame {

    private final JProgressBar progressBar;
    private final TeamfightTacticsLogic teamfightTacticsLogic;

    private Animation playerMoveLeftAnimation;
    private Animation playerMoveRightAnimation;
    private Animation playerMoveDownAnimation;
    private Animation playerMoveUpAnimation;
    private Animation playerBoringAnimation;
    private Animation waterFountainAnimation;
    private Animation fishingMushroom;
    private Animation houseSmokeAnimation;
    private Animation shrineAnimation;
    private Animation mushroomRopesAnimation;
    private Animation dogAnimation;
    private Animation flameAnimation;
    private Animation treeAnimation;
    private Animation mushroomAnimation;

    private Image backgroundImage;
    private Image cardPanelImage;
    private Image cardPanelSellImage;

    private Shape cardSellShape;
    private Shape cardLockShape;
    private Polygon cardPlayerGoldPolygon;

    private final ArrayList<Shape> cardPanelShapeList = new ArrayList<>();
    private final ArrayList<Shape> characterWaitingShapeList = new ArrayList<>();
    private final ArrayList<Shape> characterPlayingShapeList = new ArrayList<>();
    private final HashMap<Key<Integer, Integer>, FieldState> enemyPlayingMap = new HashMap<>();
    private final HashMap<Key<Integer, Integer>, FieldState> characterWaitingMap = new HashMap<>();
    private final HashMap<Key<Integer, Integer>, String> cardPanelMap = new HashMap<>();
    private final HashMap<Key<Integer, Integer>, FieldState> characterPlayingMap = new HashMap<>();

    private boolean creatingMap = false;
    private boolean loadingCharacters = false;
    private boolean loadingTextures = false;


    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public ArrayList<Shape> getCardPanelShapeList() {
        return cardPanelShapeList;
    }

    public ArrayList<Shape> getCharacterWaitingShapeList() {
        return characterWaitingShapeList;
    }

    public ArrayList<Shape> getCharacterPlayingShapeList() {
        return characterPlayingShapeList;
    }

    public HashMap<Key<Integer, Integer>, FieldState> getEnemyPlayingMap() {
        return enemyPlayingMap;
    }

    public HashMap<Key<Integer, Integer>, FieldState> getCharacterWaitingMap() {
        return characterWaitingMap;
    }

    public HashMap<Key<Integer, Integer>, String> getCardPanelMap() {
        return cardPanelMap;
    }

    public HashMap<Key<Integer, Integer>, FieldState> getCharacterPlayingMap() {
        return characterPlayingMap;
    }

    public Shape getCardSellShape() {
        return cardSellShape;
    }

    public Shape getCardLockShape() {
        return cardLockShape;
    }

    public Polygon getCardPlayerGoldPolygon() {
        return cardPlayerGoldPolygon;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public Image getCardPanelImage() {
        return cardPanelImage;
    }

    public Image getCardPanelSellImage() {
        return cardPanelSellImage;
    }

    public Animation getWaterFountainAnimation() {
        return waterFountainAnimation;
    }

    public Animation getTreeAnimation() {
        return treeAnimation;
    }

    public Animation getFlameAnimation() {
        return flameAnimation;
    }

    public Animation getMushroomAnimation() {
        return mushroomAnimation;
    }

    public Animation getDogAnimation() {
        return dogAnimation;
    }

    public Animation getMushroomRopesAnimation() {
        return mushroomRopesAnimation;
    }

    public Animation getShrineAnimation() {
        return shrineAnimation;
    }

    public Animation getHouseSmokeAnimation() {
        return houseSmokeAnimation;
    }

    public Animation getFishingMushroom() {
        return fishingMushroom;
    }

    public TeamfightTacticsLogic getTeamfightTacticsLogic() {
        return teamfightTacticsLogic;
    }

    public LoadGame(TeamfightTacticsLogic teamfightTacticsLogic) {
        this.teamfightTacticsLogic = teamfightTacticsLogic;
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setBounds(GameConstants.FRAME_WIDTH / 2 - 80, GameConstants.FRAME_HEIGHT / 2 - 30, 160, 30);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
    }

    private void loadingTextures() {
        if (waterFountainAnimation == null) {
            BufferedImage waterFountain = ResourceLoader.readImage("images/textures/water_fountain/waterFountain.png");
            SpriteSheet waterFountainSpriteSheet = new SpriteSheet(waterFountain);
            BufferedImage[] waterFountainAnimationImages = {
                    waterFountainSpriteSheet.grabImage(1, 1, 80, 95),
                    waterFountainSpriteSheet.grabImage(2, 1, 80, 95),
                    waterFountainSpriteSheet.grabImage(3, 1, 80, 95),
                    waterFountainSpriteSheet.grabImage(4, 1, 80, 95),
                    waterFountainSpriteSheet.grabImage(5, 1, 80, 95),
                    waterFountainSpriteSheet.grabImage(6, 1, 80, 95),
                    waterFountainSpriteSheet.grabImage(7, 1, 80, 95),
                    waterFountainSpriteSheet.grabImage(8, 1, 80, 95)
            };
            waterFountainAnimation = new Animation(waterFountainAnimationImages, 10);
        }
        if (treeAnimation == null) {
            BufferedImage tree = ResourceLoader.readImage("images/textures/tree/tree.png");
            SpriteSheet treeSpriteSheet = new SpriteSheet(tree);
            BufferedImage[] treeAnimationImages = {
                    treeSpriteSheet.grabImage(1, 1, 64, 96),
                    treeSpriteSheet.grabImage(2, 1, 64, 96),
                    treeSpriteSheet.grabImage(3, 1, 64, 96),
                    treeSpriteSheet.grabImage(4, 1, 64, 96),
                    treeSpriteSheet.grabImage(5, 1, 64, 96),
                    treeSpriteSheet.grabImage(6, 1, 64, 96),
                    treeSpriteSheet.grabImage(7, 1, 64, 96),
                    treeSpriteSheet.grabImage(8, 1, 64, 96)
            };
            treeAnimation = new Animation(treeAnimationImages, 10);


        }

        if (goldAnimation == null) {
            goldAnimation = ResourceLoader.readImage("images/textures/gold/goldSpriteSheet.png");
        }

        if (backgroundImage == null) {
            backgroundImage = ResourceLoader.readImage("images/textures/background/background.png");


        }

        if (cardPanelImage == null) {
            cardPanelImage = ResourceLoader.readImage("images/textures/cardpanel/cardpanel.png");


        }

        if (cardPanelSellImage == null) {
            cardPanelSellImage = ResourceLoader.readImage("images/textures/cardpanel/cardpanelsell.png");


        }

        if (UIConstants.lockOn == null) {
            UIConstants.lockOn = ResourceLoader.readImage("images/textures/cardpanel/on.png");


        }
        if (UIConstants.lockOff == null) {
            UIConstants.lockOff = ResourceLoader.readImage("images/textures/cardpanel/off.png");


        }

        if (UIConstants.infoPanel == null) {
            UIConstants.infoPanel = ResourceLoader.readImage("images/textures/cardpanel/infoPanel.png");


        }


        if (houseSmokeAnimation == null) {
            SpriteSheet spriteSheet = new SpriteSheet(ResourceLoader.readImage("images/textures/house_smoke/houseSmoke.png"));
            BufferedImage[] houseSmokeAnimationImages = {
                    spriteSheet.grabImage(1, 1, 40, 40),
                    spriteSheet.grabImage(2, 1, 40, 40),
                    spriteSheet.grabImage(3, 1, 40, 40),
                    spriteSheet.grabImage(4, 1, 40, 40),
            };
            houseSmokeAnimation = new Animation(houseSmokeAnimationImages, 10);


        }

        if (flameAnimation == null) {
            BufferedImage tree = ResourceLoader.readImage("images/textures/flame/flame.png");
            SpriteSheet flameSpriteSheet = new SpriteSheet(tree);
            BufferedImage[] flameAnimationImages = {
                    flameSpriteSheet.grabImage(1, 1, 32, 45),
                    flameSpriteSheet.grabImage(2, 1, 32, 45),
                    flameSpriteSheet.grabImage(3, 1, 32, 45),
                    flameSpriteSheet.grabImage(4, 1, 32, 45),
                    flameSpriteSheet.grabImage(5, 1, 32, 45),
                    flameSpriteSheet.grabImage(6, 1, 32, 45),
            };
            flameAnimation = new Animation(flameAnimationImages, 8);


        }

        if (shrineAnimation == null) {
            BufferedImage tree = ResourceLoader.readImage("images/textures/shrine/shrine.png");
            SpriteSheet flameSpriteSheet = new SpriteSheet(tree);
            BufferedImage[] flameAnimationImages = {
                    flameSpriteSheet.grabImage(1, 1, 40, 60),
                    flameSpriteSheet.grabImage(2, 1, 40, 60),
                    flameSpriteSheet.grabImage(3, 1, 40, 60),
                    flameSpriteSheet.grabImage(4, 1, 40, 60),
                    flameSpriteSheet.grabImage(5, 1, 40, 60),
                    flameSpriteSheet.grabImage(6, 1, 40, 60),
                    flameSpriteSheet.grabImage(7, 1, 40, 60),
                    flameSpriteSheet.grabImage(8, 1, 40, 60),
                    flameSpriteSheet.grabImage(9, 1, 40, 60),
                    flameSpriteSheet.grabImage(10, 1, 40, 60),
                    flameSpriteSheet.grabImage(11, 1, 40, 60),
                    flameSpriteSheet.grabImage(12, 1, 40, 60),
                    flameSpriteSheet.grabImage(13, 1, 40, 60),
                    flameSpriteSheet.grabImage(14, 1, 40, 60),
                    flameSpriteSheet.grabImage(15, 1, 40, 60),
            };
            shrineAnimation = new Animation(flameAnimationImages, 8);
        }

        if (mushroomAnimation == null) {
            BufferedImage mushroom = ResourceLoader.readImage("images/textures/small_anims/smallAnimsMushroom.png");
            SpriteSheet mushSpriteSheet = new SpriteSheet(mushroom);
            BufferedImage[] mushAnimationImages = {
                    mushSpriteSheet.grabImage(1, 1, 48, 48),
                    mushSpriteSheet.grabImage(2, 1, 48, 48),
                    mushSpriteSheet.grabImage(3, 1, 48, 48),
                    mushSpriteSheet.grabImage(4, 1, 48, 48),
                    mushSpriteSheet.grabImage(5, 1, 48, 48),
                    mushSpriteSheet.grabImage(6, 1, 48, 48),
                    mushSpriteSheet.grabImage(7, 1, 48, 48),
                    mushSpriteSheet.grabImage(8, 1, 48, 48),
            };
            mushroomAnimation = new Animation(mushAnimationImages, 8);


        }

        if (fishingMushroom == null) {
            BufferedImage mushroom = ResourceLoader.readImage("images/textures/small_anims/fishingMushroom.png");
            SpriteSheet mushSpriteSheet = new SpriteSheet(mushroom);
            BufferedImage[] mushAnimationImages = {
                    mushSpriteSheet.grabImage(1, 1, 48, 48),
                    mushSpriteSheet.grabImage(2, 1, 48, 48),
                    mushSpriteSheet.grabImage(3, 1, 48, 48),
                    mushSpriteSheet.grabImage(4, 1, 48, 48),
                    mushSpriteSheet.grabImage(5, 1, 48, 48),
                    mushSpriteSheet.grabImage(6, 1, 48, 48),
                    mushSpriteSheet.grabImage(7, 1, 48, 48),
                    mushSpriteSheet.grabImage(8, 1, 48, 48),
            };
            fishingMushroom = new Animation(mushAnimationImages, 8);


        }

        if (mushroomRopesAnimation == null) {
            BufferedImage mushroom = ResourceLoader.readImage("images/textures/small_anims/smallAnimsMushroomRopes.png");
            SpriteSheet mushSpriteSheet = new SpriteSheet(mushroom);
            BufferedImage[] mushAnimationImages = {
                    mushSpriteSheet.grabImage(1, 1, 80, 45),
                    mushSpriteSheet.grabImage(2, 1, 80, 45),
                    mushSpriteSheet.grabImage(3, 1, 80, 45),
                    mushSpriteSheet.grabImage(4, 1, 80, 45),
                    mushSpriteSheet.grabImage(5, 1, 80, 45),
                    mushSpriteSheet.grabImage(6, 1, 80, 45),
                    mushSpriteSheet.grabImage(7, 1, 80, 45),
                    mushSpriteSheet.grabImage(8, 1, 80, 45),
            };
            mushroomRopesAnimation = new Animation(mushAnimationImages, 6);
        }

        if (dogAnimation == null) {
            BufferedImage dog = ResourceLoader.readImage("images/textures/small_anims/dog.png");
            SpriteSheet dogSpriteSheet = new SpriteSheet(dog);
            BufferedImage[] dogAnimationImages = {
                    dogSpriteSheet.grabImage(1, 1, 48, 48),
                    dogSpriteSheet.grabImage(2, 1, 48, 48),
                    dogSpriteSheet.grabImage(3, 1, 48, 48),
                    dogSpriteSheet.grabImage(4, 1, 48, 48),
                    dogSpriteSheet.grabImage(5, 1, 48, 48),
                    dogSpriteSheet.grabImage(6, 1, 48, 48),
            };
            dogAnimation = new Animation(dogAnimationImages, 10);


        }
    }

    private void loadingCharacters() {
        loadingPlayer();
        loadingShootingSlime();
        loadingCarrot();
        loadingSpikedSlime();
        loadingSaint();
        loadingRedSlime();
        loadingPurpleSlime();
        loadingWise();
    }

    private void loadingSaint() {
        if (UIConstants.saintMoveDownAnimation == null) {
            UIConstants.saintMoveDownAnimation = ResourceLoader.readImage("images/characters/saint/move/saintMoveDown.png");
        }
        if (UIConstants.saintMoveUpAnimation == null) {
            UIConstants.saintMoveUpAnimation = ResourceLoader.readImage("images/characters/saint/move/saintMoveUp.png");
        }
        if (UIConstants.saintMoveLeftAnimation == null) {
            UIConstants.saintMoveLeftAnimation = ResourceLoader.readImage("images/characters/saint/move/saintMoveLeft.png");
        }
        if (UIConstants.saintMoveRightAnimation == null) {
            UIConstants.saintMoveRightAnimation = ResourceLoader.readImage("images/characters/saint/move/saintMoveRight.png");
        }

        if (UIConstants.saintAttackDownAnimation == null) {
            UIConstants.saintAttackDownAnimation = ResourceLoader.readImage("images/characters/saint/attack/saintAttackDown.png");
        }
        if (UIConstants.saintAttackUpAnimation == null) {
            UIConstants.saintAttackUpAnimation = ResourceLoader.readImage("images/characters/saint/attack/saintAttackUp.png");
        }
        if (UIConstants.saintAttackLeftAnimation == null) {
            UIConstants.saintAttackLeftAnimation = ResourceLoader.readImage("images/characters/saint/attack/saintAttackLeft.png");
        }
        if (UIConstants.saintAttackRightAnimation == null) {
            UIConstants.saintAttackRightAnimation = ResourceLoader.readImage("images/characters/saint/attack/saintAttackRight.png");
        }

        if (UIConstants.saintDeathDownAnimation == null) {
            UIConstants.saintDeathDownAnimation = ResourceLoader.readImage("images/characters/saint/death/saintDeathDown.png");
        }
        if (UIConstants.saintDeathUpAnimation == null) {
            UIConstants.saintDeathUpAnimation = ResourceLoader.readImage("images/characters/saint/death/saintDeathUp.png");
        }
        if (UIConstants.saintDeathLeftAnimation == null) {
            UIConstants.saintDeathLeftAnimation = ResourceLoader.readImage("images/characters/saint/death/saintDeathLeft.png");
        }
        if (UIConstants.saintDeathRightAnimation == null) {
            UIConstants.saintDeathRightAnimation = ResourceLoader.readImage("images/characters/saint/death/saintDeathRight.png");
        }

        if (UIConstants.saintSkillDownAnimation == null) {
            UIConstants.saintSkillDownAnimation = ResourceLoader.readImage("images/characters/saint/skill/saintSkillDown.png");
        }
        if (UIConstants.saintSkillUpAnimation == null) {
            UIConstants.saintSkillUpAnimation = ResourceLoader.readImage("images/characters/saint/skill/saintSkillUp.png");
        }
        if (UIConstants.saintSkillLeftAnimation == null) {
            UIConstants.saintSkillLeftAnimation = ResourceLoader.readImage("images/characters/saint/skill/saintSkillLeft.png");
        }
        if (UIConstants.saintSkillRightAnimation == null) {
            UIConstants.saintSkillRightAnimation = ResourceLoader.readImage("images/characters/saint/skill/saintSkillRight.png");
        }
    }

    private void loadingRedSlime() {
        if (UIConstants.redMoveDownAnimation == null) {
            UIConstants.redMoveDownAnimation = ResourceLoader.readImage("images/characters/red_slime/move/redslime@front-move_50x50.png");

        }
        if (UIConstants.redMoveUpAnimation == null) {
            UIConstants.redMoveUpAnimation = ResourceLoader.readImage("images/characters/red_slime/move/redslime@back-move_50x50.png");

        }
        if (UIConstants.redMoveLeftAnimation == null) {
            UIConstants.redMoveLeftAnimation = ResourceLoader.readImage("images/characters/red_slime/move/redslime@left-move_50x50.png");

        }
        if (UIConstants.redMoveRightAnimation == null) {
            UIConstants.redMoveRightAnimation = ResourceLoader.readImage("images/characters/red_slime/move/redslime@right-move_50x50.png");

        }

        if (UIConstants.redAttackDownAnimation == null) {
            UIConstants.redAttackDownAnimation = ResourceLoader.readImage("images/characters/red_slime/attack/redslime@front-attack_50x50.png");

        }
        if (UIConstants.redAttackUpAnimation == null) {
            UIConstants.redAttackUpAnimation = ResourceLoader.readImage("images/characters/red_slime/attack/redslime@back-attack_50x50.png");

        }
        if (UIConstants.redAttackLeftAnimation == null) {
            UIConstants.redAttackLeftAnimation = ResourceLoader.readImage("images/characters/red_slime/attack/redslime@left-attack_50x50.png");

        }
        if (UIConstants.redAttackRightAnimation == null) {
            UIConstants.redAttackRightAnimation = ResourceLoader.readImage("images/characters/red_slime/attack/redslime@right-attack_50x50.png");

        }

        if (UIConstants.redDeathAnimation == null) {
            UIConstants.redDeathAnimation = ResourceLoader.readImage("images/characters/red_slime/death/redslime@front-death_50x50.png");
        }
    }

    private void loadingPurpleSlime() {
        if (UIConstants.purpleMoveDownAnimation == null) {
            UIConstants.purpleMoveDownAnimation = ResourceLoader.readImage("images/characters/purple_slime/move/slime@front-move_50x50.png");

        }
        if (UIConstants.purpleMoveUpAnimation == null) {
            UIConstants.purpleMoveUpAnimation = ResourceLoader.readImage("images/characters/purple_slime/move/slime@back-move_50x50.png");

        }
        if (UIConstants.purpleMoveLeftAnimation == null) {
            UIConstants.purpleMoveLeftAnimation = ResourceLoader.readImage("images/characters/purple_slime/move/slime@left-move_50x50.png");

        }
        if (UIConstants.purpleMoveRightAnimation == null) {
            UIConstants.purpleMoveRightAnimation = ResourceLoader.readImage("images/characters/purple_slime/move/slime@right-move_50x50.png");

        }

        if (UIConstants.purpleAttackDownAnimation == null) {
            UIConstants.purpleAttackDownAnimation = ResourceLoader.readImage("images/characters/purple_slime/attack/slime@front-attack_50x50.png");

        }
        if (UIConstants.purpleAttackUpAnimation == null) {
            UIConstants.purpleAttackUpAnimation = ResourceLoader.readImage("images/characters/purple_slime/attack/slime@back-attack_50x50.png");

        }
        if (UIConstants.purpleAttackLeftAnimation == null) {
            UIConstants.purpleAttackLeftAnimation = ResourceLoader.readImage("images/characters/purple_slime/attack/slime@left-attack_50x50.png");

        }
        if (UIConstants.purpleAttackRightAnimation == null) {
            UIConstants.purpleAttackRightAnimation = ResourceLoader.readImage("images/characters/purple_slime/attack/slime@right-attack_50x50.png");

        }

        if (UIConstants.purpleDeathAnimation == null) {
            UIConstants.purpleDeathAnimation = ResourceLoader.readImage("images/characters/purple_slime/death/slime@front-death_50x50.png");

        }
    }

    private void loadingWise() {
        if (UIConstants.wiseMoveAnimation == null) {
            UIConstants.wiseMoveAnimation = ResourceLoader.readImage("images/characters/wise/move/wFRJj.png");

        }
        if (UIConstants.wiseProjectileAnimation == null) {
            UIConstants.wiseProjectileAnimation = ResourceLoader.readImage("images/characters/wise/projectile/wiseProjectile.png");

        }
    }

    private void loadingSpikedSlime() {
        if (UIConstants.spikedSlimeMoveUpAnimation == null) {
            UIConstants.spikedSlimeMoveUpAnimation = ResourceLoader.readImage("images/characters/spiked_slime/move/spikedSlimeMoveUp.png");

        }
        if (UIConstants.spikedSlimeMoveDownAnimation == null) {
            UIConstants.spikedSlimeMoveDownAnimation = ResourceLoader.readImage("images/characters/spiked_slime/move/spikedSlimeMoveDown.png");

        }

        if (UIConstants.spikedSlimeAttackAnimation == null) {
            UIConstants.spikedSlimeAttackAnimation = ResourceLoader.readImage("images/characters/spiked_slime/attack/spikedSlimeAttack.png");

        }

        if (UIConstants.spikedSlimeDeathAnimation == null) {
            UIConstants.spikedSlimeDeathAnimation = ResourceLoader.readImage("images/characters/spiked_slime/death/spikedSlimeDeath.png");

        }

        if (UIConstants.spikedSlimeSkillAnimation == null) {
            UIConstants.spikedSlimeSkillAnimation = ResourceLoader.readImage("images/characters/spiked_slime/skill/spikedSkill.png");

        }
    }

    private void loadingCarrot() {
        if (UIConstants.carrotMoveDownAnimation == null) {
            UIConstants.carrotMoveDownAnimation = ResourceLoader.readImage("images/characters/carrot/move/carrotMoveBack.png");

        }
        if (UIConstants.carrotMoveUpAnimation == null) {
            UIConstants.carrotMoveUpAnimation = ResourceLoader.readImage("images/characters/carrot/move/carrotMoveFront.png");

        }
        if (UIConstants.carrotMoveLeftAnimation == null) {
            UIConstants.carrotMoveLeftAnimation = ResourceLoader.readImage("images/characters/carrot/move/carrotMoveLeft.png");

        }
        if (UIConstants.carrotMoveRightAnimation == null) {
            UIConstants.carrotMoveRightAnimation = ResourceLoader.readImage("images/characters/carrot/move/carrotMoveRight.png");

        }

        if (UIConstants.carrotAttackUpAnimation == null) {
            UIConstants.carrotAttackUpAnimation = ResourceLoader.readImage("images/characters/carrot/attack/carrotAttackUp.png");

        }
        if (UIConstants.carrotAttackDownAnimation == null) {
            UIConstants.carrotAttackDownAnimation = ResourceLoader.readImage("images/characters/carrot/attack/carrotAttackDown.png");

        }
        if (UIConstants.carrotAttackLeftAnimation == null) {
            UIConstants.carrotAttackLeftAnimation = ResourceLoader.readImage("images/characters/carrot/attack/carrotAttackLeft.png");

        }
        if (UIConstants.carrotAttackRightAnimation == null) {
            UIConstants.carrotAttackRightAnimation = ResourceLoader.readImage("images/characters/carrot/attack/carrotAttackRight.png");

        }

        if (UIConstants.carrotDeathAnimation == null) {
            UIConstants.carrotDeathAnimation = ResourceLoader.readImage("images/characters/carrot/death/carrotDeath.png");

        }

        if (UIConstants.carrotSkillAnimation == null) {
            UIConstants.carrotSkillAnimation = ResourceLoader.readImage("images/characters/carrot/skill/carrotSkill.png");

        }
    }

    private void loadingShootingSlime() {
        if (UIConstants.shootingSlimeAttackFrontAnimation == null) {
            UIConstants.shootingSlimeAttackFrontAnimation = ResourceLoader.readImage("images/characters/apple_slime/attack/shootingSlimeShootFront.png");

        }
        if (UIConstants.shootingSlimeAttackBackAnimation == null) {
            UIConstants.shootingSlimeAttackBackAnimation = ResourceLoader.readImage("images/characters/apple_slime/attack/shootingSlimeShootBack.png");

        }
        if (UIConstants.shootingSlimeAttackRightAnimation == null) {
            UIConstants.shootingSlimeAttackRightAnimation = ResourceLoader.readImage("images/characters/apple_slime/attack/shootingSlimeShootRight.png");

        }
        if (UIConstants.shootingSlimeAttackLeftAnimation == null) {
            UIConstants.shootingSlimeAttackLeftAnimation = ResourceLoader.readImage("images/characters/apple_slime/attack/shootingSlimeShootLeft.png");

        }

        if (UIConstants.shootingSlimeMoveDownAnimation == null) {
            UIConstants.shootingSlimeMoveDownAnimation = ResourceLoader.readImage("images/characters/apple_slime/move/shootingSlimeMoveBack.png");

        }
        if (UIConstants.shootingSlimeMoveUpAnimation == null) {
            UIConstants.shootingSlimeMoveUpAnimation = ResourceLoader.readImage("images/characters/apple_slime/move/shootingSlimeMoveFront.png");

        }
        if (UIConstants.shootingSlimeMoveLeftAnimation == null) {
            UIConstants.shootingSlimeMoveLeftAnimation = ResourceLoader.readImage("images/characters/apple_slime/move/shootingSlimeMoveLeft.png");

        }
        if (UIConstants.shootingSlimeMoveRightAnimation == null) {
            UIConstants.shootingSlimeMoveRightAnimation = ResourceLoader.readImage("images/characters/apple_slime/move/shootingSlimeMoveRight.png");

        }

        if (UIConstants.shootingSlimeSkillDownProjectile == null) {
            UIConstants.shootingSlimeSkillDownProjectile = ResourceLoader.readImage("images/characters/apple_slime/skill/shootingSlimeSkillDown.png");

        }
        if (UIConstants.shootingSlimeSkillUpProjectile == null) {
            UIConstants.shootingSlimeSkillUpProjectile = ResourceLoader.readImage("images/characters/apple_slime/skill/shootingSlimeSkillUp.png");
        }
        if (UIConstants.shootingSlimeSkillLeftProjectile == null) {
            UIConstants.shootingSlimeSkillLeftProjectile = ResourceLoader.readImage("images/characters/apple_slime/skill/shootingSlimeSkillLeft.png");
        }
        if (UIConstants.shootingSlimeSkillRightProjectile == null) {
            UIConstants.shootingSlimeSkillRightProjectile = ResourceLoader.readImage("images/characters/apple_slime/skill/shootingSlimeSkillRight.png");
        }

        if (UIConstants.shootingSlimeAttackProjectile == null) {
            UIConstants.shootingSlimeAttackProjectile = ResourceLoader.readImage("images/characters/apple_slime/projectile/projectile.png");
        }

        if (UIConstants.shootingSlimeDeathAnimation == null) {
            UIConstants.shootingSlimeDeathAnimation = ResourceLoader.readImage("images/characters/apple_slime/death/shootingSlimeDeath.png");
        }
    }

    private void loadingPlayer() {
        if (playerMoveUpAnimation == null) {
            SpriteSheet playerMoveUpSpriteSheet = new SpriteSheet(ResourceLoader.readImage("images/characters/player/move/characterOneMoveUp.png"));
            PLAYER_SPRITESHEET = playerMoveUpSpriteSheet.grabImage(1, 1, 80, 80);
            playerMoveUpAnimation = new Animation(playerImages(playerMoveUpSpriteSheet), 10);
        }
        if (playerMoveDownAnimation == null) {
            SpriteSheet playerMoveDownSpriteSheet = new SpriteSheet(ResourceLoader.readImage("images/characters/player/move/characterOneMoveDown.png"));
            playerMoveDownAnimation = new Animation(playerImages(playerMoveDownSpriteSheet), 10);
        }
        if (playerMoveLeftAnimation == null) {
            SpriteSheet playerMoveLeftSpriteSheet = new SpriteSheet(ResourceLoader.readImage("images/characters/player/move/characterOneMoveLeft.png"));
            playerMoveLeftAnimation = new Animation(playerImages(playerMoveLeftSpriteSheet), 10);
        }
        if (playerMoveRightAnimation == null) {
            SpriteSheet playerMoveRightSpriteSheet = new SpriteSheet(ResourceLoader.readImage("images/characters/player/move/characterOneMoveRight.png"));
            playerMoveRightAnimation = new Animation(playerImages(playerMoveRightSpriteSheet), 10);
        }
        if (playerBoringAnimation == null) {
            SpriteSheet playerBoringSpriteSheet = new SpriteSheet(ResourceLoader.readImage("images/characters/player/idle/characterOneBoring.png"));
            BufferedImage[] playerBoringAnimationImages = {
                    playerBoringSpriteSheet.grabImage(1, 1, 80, 80),
                    playerBoringSpriteSheet.grabImage(2, 1, 80, 80),
                    playerBoringSpriteSheet.grabImage(3, 1, 80, 80),
                    playerBoringSpriteSheet.grabImage(4, 1, 80, 80),
            };
            playerBoringAnimation = new Animation(playerBoringAnimationImages, 10);
        }
    }

    private BufferedImage[] playerImages(SpriteSheet spriteSheet) {
        return new BufferedImage[]{
                spriteSheet.grabImage(1, 1, 80, 80),
                spriteSheet.grabImage(2, 1, 80, 80),
                spriteSheet.grabImage(3, 1, 80, 80),
                spriteSheet.grabImage(4, 1, 80, 80),
                spriteSheet.grabImage(5, 1, 80, 80),
                spriteSheet.grabImage(6, 1, 80, 80),
                spriteSheet.grabImage(7, 1, 80, 80),
                spriteSheet.grabImage(8, 1, 80, 80)
        };
    }

    private void startAnimations() {
        teamfightTacticsLogic.setPlayer(new Player(80, 150, PLAYER_SPRITESHEET));
        teamfightTacticsLogic.getPlayer().setAnimation(playerMoveDownAnimation);
        teamfightTacticsLogic.getPlayer().setMoveUpAnimation(playerMoveUpAnimation);
        teamfightTacticsLogic.getPlayer().setMoveDownAnimation(playerMoveDownAnimation);
        teamfightTacticsLogic.getPlayer().setMoveLeftAnimation(playerMoveLeftAnimation);
        teamfightTacticsLogic.getPlayer().setMoveRightAnimation(playerMoveRightAnimation);
        teamfightTacticsLogic.getPlayer().setBoringAnimation(playerBoringAnimation);
        teamfightTacticsLogic.getPlayer().getAnimation().start();
        waterFountainAnimation.start();
        treeAnimation.start();
        flameAnimation.start();
        dogAnimation.start();
        mushroomAnimation.start();
        mushroomRopesAnimation.start();
        houseSmokeAnimation.start();
        shrineAnimation.start();
        fishingMushroom.start();
    }

    private void createCardPanel() {

        cardPanelShapeList.add(new Rectangle2D.Double(255, 810, 120, 50));

        Key<Integer, Integer> key = new Key<>(255, 810);
        cardPanelMap.put(key, UIConstants.REFRESH_CARD);

        cardSellShape = new Rectangle2D.Double(250, 810, 755, 105);
        key = new Key<>(250, 810);
        cardPanelMap.put(key, UIConstants.SELL_CARD);

        cardPanelShapeList.add(new Rectangle2D.Double(255, 865, 120, 50));
        key = new Key<>(255, 865);
        cardPanelMap.put(key, UIConstants.LEVELING_CARD);

        int[] x2 = {5, 10, 60, 65};
        int[] y2 = {25, 5, 5, 25};

        for (int i = 0; i < x2.length; ++i) {
            x2[i] += 590;
            y2[i] += 780;
        }

        cardPlayerGoldPolygon = new Polygon(x2, y2, x2.length);

        cardLockShape = new Rectangle2D.Double(295, 785, 40, 20);
        key = new Key<>(295, 785);
        cardPanelMap.put(key, UIConstants.LOCK_CARD);

        for (int x = 380; x <= 880; x += 125) {
            cardPanelShapeList.add(new Rectangle2D.Double(x, 810, GameConstants.CHARACTER_CARD_SIZE_W, GameConstants.CHARACTER_CARD_SIZE_H));
            key = new Key<>(x, 810);
            cardPanelMap.put(key, UIConstants.CHARACTER_CARD);
        }
    }

    private void createCharacterWaitingPanel() {
        for (int x = 270; x <= 910; x += GameConstants.CARD_SIZE + 10) {
            Key<Integer, Integer> key = new Key<>(x, 700);
            characterWaitingMap.put(key, FieldState.EMPTY);
            characterWaitingShapeList.add(new Rectangle2D.Double(x, 700, GameConstants.CARD_SIZE, GameConstants.CARD_SIZE));
        }
    }

    private void createEnemyPlayingPanel() {

        for (int x = 290; x <= 860; x += GameConstants.CARD_SIZE + 10) {
            for (int y = 160; y <= 340; y += GameConstants.CARD_SIZE + 10) {
                if (y != 160 + GameConstants.CARD_SIZE + 10) {
                    Key<Integer, Integer> key = new Key<>(x, y);
                    enemyPlayingMap.put(key, FieldState.EMPTY);
                }
            }
        }

        for (int x = 340; x <= 910; x += GameConstants.CARD_SIZE + 10) {
            Key<Integer, Integer> key = new Key<>(x, 250);
            enemyPlayingMap.put(key, FieldState.EMPTY);
        }
    }

    private void createCharacterPlayingPanel() {

        for (int x = 340; x <= 910; x += GameConstants.CARD_SIZE + 10) {
            for (int y = 430; y <= 610; y += GameConstants.CARD_SIZE + 10) {
                if (y != 430 + GameConstants.CARD_SIZE + 10) {
                    Key<Integer, Integer> key = new Key<>(x, y);
                    characterPlayingMap.put(key, FieldState.EMPTY);
                    characterPlayingShapeList.add(new Ellipse2D.Double(x, y, GameConstants.CARD_SIZE, GameConstants.CARD_SIZE));
                }
            }
        }

        for (int x = 290; x <= 860; x += GameConstants.CARD_SIZE + 10) {
            Key<Integer, Integer> key = new Key<>(x, 520);
            characterPlayingMap.put(key, FieldState.EMPTY);
            characterPlayingShapeList.add(new Ellipse2D.Double(x, 520, GameConstants.CARD_SIZE, GameConstants.CARD_SIZE));
        }
    }

    public synchronized void loadingGame() {
        progressBar.setVisible(true);

        int i = 0;
        while (i < progressBar.getMaximum()) {
            if (!loadingTextures) {
                loadingTextures();
                loadingTextures = true;
                i += 16;
                progressBar.setValue(i);
            }
            if (!loadingCharacters) {
                loadingCharacters();
                loadingCharacters = true;
                i += 71;
                progressBar.setValue(i);
            }

            if (!creatingMap) {
                createCardPanel();
                createCharacterPlayingPanel();
                createCharacterWaitingPanel();
                createEnemyPlayingPanel();
                teamfightTacticsLogic.creatingRandomCharacters();
                startAnimations();
                creatingMap = true;
                i += 13;
                progressBar.setValue(i);
            }
        }

        teamfightTacticsLogic.setGameState(GameState.GAME_STATE);
        TeamfightTacticsLogic.isPlanningTime.set(true);

        progressBar.setVisible(false);

        teamfightTacticsLogic.setPlanningClock(new Stopwatch());
        teamfightTacticsLogic.setFightClock(new Stopwatch());
        teamfightTacticsLogic.getPlanningClock().setMaxTime(PLANNING_TIME);
        teamfightTacticsLogic.getFightClock().setMaxTime(FIGHT_TIME);
        teamfightTacticsLogic.getFightClock().stop();
    }
}
