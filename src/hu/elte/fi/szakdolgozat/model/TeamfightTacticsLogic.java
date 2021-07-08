package hu.elte.fi.szakdolgozat.model;

import hu.elte.fi.szakdolgozat.gui.Menu;
import hu.elte.fi.szakdolgozat.gui.TeamfightTacticsPanel;
import hu.elte.fi.szakdolgozat.gui.sprite.characters.*;
import hu.elte.fi.szakdolgozat.gui.sprite.other.Bullet;
import hu.elte.fi.szakdolgozat.gui.sprite.other.Gold;
import hu.elte.fi.szakdolgozat.gui.sprite.player.Player;
import hu.elte.fi.szakdolgozat.model.map.Key;
import hu.elte.fi.szakdolgozat.model.map.LoadGame;
import hu.elte.fi.szakdolgozat.persistence.dao.DataDao;
import hu.elte.fi.szakdolgozat.persistence.entity.DataScore;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import static hu.elte.fi.szakdolgozat.model.GameConstants.*;

public class TeamfightTacticsLogic {

    public boolean startGame = false;
    public static final AtomicBoolean isOver = new AtomicBoolean(false);
    public static final AtomicBoolean isPlanningTime = new AtomicBoolean(false);

    private GameState gameState = GameState.MENU_STATE;
    private volatile Character selectedCharacter;
    private Character swapSpotCharacter;
    private Stopwatch fightClock;
    private Stopwatch planningClock;
    private Player player;
    private final Menu menu;
    private final DataDao dataDao;
    private final Timer logic;
    private TeamfightTacticsPanel myPanel;
    private LoadGame loadGame;
    private DataScore dataScore;
    private static final Random rnd = new Random();
    private int sellCost;

    private static final List<Character> alliances = new ArrayList<>();
    private static final List<Character> alliancesWaiting = new ArrayList<>();
    private static final List<Character> randomCardCharacters = new ArrayList<>();
    private static final List<Character> enemies = new ArrayList<>();
    private static final List<Character> fightingEnemies = Collections.synchronizedList(new ArrayList<>());
    private static final List<Character> fightingAlliances = Collections.synchronizedList(new ArrayList<>());
    private static final List<Gold> droppedGold = new ArrayList<>();
    private static final List<Bullet> charactersBullet = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    private static int deaths = 0;
    private int spentGold = 0;
    private int gameLevel = 1;
    private static int kills = 0;
    private boolean cardsLock = false;
    private long fpsTimer = System.currentTimeMillis();
    private int frames = 0;
    private int framePerSecond = 0;
    private final boolean dbRunning;

    public int getSellCost() {
        return sellCost;
    }

    public void setSellCost(int sellCost) {
        this.sellCost = sellCost;
    }

    public boolean isDbRunning() {
        return dbRunning;
    }

    public void setSelectedCharacter(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Character getSwapSpotCharacter() {
        return swapSpotCharacter;
    }

    public void setSwapSpotCharacter(Character swapSpotCharacter) {
        this.swapSpotCharacter = swapSpotCharacter;
    }

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setPlanningClock(Stopwatch planningClock) {
        this.planningClock = planningClock;
    }

    public void setFightClock(Stopwatch fightClock) {
        this.fightClock = fightClock;
    }

    public Stopwatch getPlanningClock() {
        return planningClock;
    }

    public Stopwatch getFightClock() {
        return fightClock;
    }

    public static int getDeaths() {
        return deaths;
    }

    public static int getKills() {
        return kills;
    }

    public int getSpentGold() {
        return spentGold;
    }

    public void setSpentGold(int spentGold) {
        this.spentGold = spentGold;
    }

    public static void setDeaths(int deaths) {
        TeamfightTacticsLogic.deaths = deaths;
    }

    public static void setKills(int kills) {
        TeamfightTacticsLogic.kills = kills;
    }

    public static List<Gold> getDroppedGold() {
        return droppedGold;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static List<Bullet> getCharactersBullet() {
        return charactersBullet;
    }

    public Menu getMenu() {
        return menu;
    }

    private static final List<Thread> threads = new ArrayList<>();

    public boolean isCardsLock() {
        return cardsLock;
    }

    public void setCardsLock(boolean cardsLock) {
        this.cardsLock = cardsLock;
    }

    public static List<Character> getRandomCardCharacters() {
        return randomCardCharacters;
    }

    public static List<Character> getAlliances() {
        return alliances;
    }

    public static List<Character> getAlliancesWaiting() {
        return alliancesWaiting;
    }

    public Player getPlayer() {
        return player;
    }

    public static List<Character> getEnemies() {
        return enemies;
    }

    public static List<Character> getFightingEnemies() {
        return fightingEnemies;
    }

    public static List<Character> getFightingAlliances() {
        return fightingAlliances;
    }

    public void setMyPanel(TeamfightTacticsPanel myPanel) {
        this.myPanel = myPanel;
    }

    public void setLoadGame(LoadGame loadGame) {
        this.loadGame = loadGame;
    }

    public int getFramePerSecond() {
        return framePerSecond;
    }

    public DataScore getDataScore() {
        return dataScore;
    }

    public TeamfightTacticsLogic() {
        dataDao = new DataDao();
        menu = new Menu(this);

        try {
            dbRunning = isProcessRunning();
            if (dbRunning) {
                dataDao.createDatabase();
                dataDao.createTable();
                dataDao.addFirstData();
                dataScore = dataDao.getDatas();
            }
        } catch (Exception throwable) {
            throw new IllegalStateException("Failed to get the data from the database!", throwable);
        }

        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                if (gameState == GameState.GAME_STATE) {
                    if (getPlanningClock() != null && getPlanningClock().elapsedTime() == 0) {

                        getPlanningClock().stop();
                        getFightClock().resetTimer();
                        new Thread(() -> startGame()).start();

                    } else if (!isPlanningTime.get() && isOver.get() || getFightClock() != null && !isOver.get() && getFightClock().elapsedTime() == 0) {

                        reset();
                        getPlanningClock().resetTimer();
                        getFightClock().stop();
                    }
                    if (player.getHealth() <= 0) {
                        logic.cancel();
                    }

                    if (!isPlanningTime.get()) {
                        for (int i = fightingAlliances.size() - 1; i >= 0; i--) {
                            fightingAlliances.get(i).getAnimation().update();
                            if (fightingAlliances.get(i).getSkillAnimation() != null) {
                                fightingAlliances.get(i).getSkillAnimation().update();
                            }
                        }
                        for (int i = fightingEnemies.size() - 1; i >= 0; i--) {
                            fightingEnemies.get(i).getAnimation().update();
                            if (fightingEnemies.get(i).getSkillAnimation() != null) {
                                fightingEnemies.get(i).getSkillAnimation().update();
                            }
                        }
                        for (int i = charactersBullet.size() - 1; i >= 0; i--) {
                            charactersBullet.get(i).getAnimation().update();
                        }
                    } else {
                        for (int i = alliances.size() - 1; i >= 0; i--) {
                            alliances.get(i).getAnimation().update();
                        }
                    }

                    for (int i = alliancesWaiting.size() - 1; i >= 0; i--) {
                        alliancesWaiting.get(i).getAnimation().update();
                    }

                    if (!droppedGold.isEmpty()) {
                        for (int i = droppedGold.size() - 1; i >= 0; i--) {
                            droppedGold.get(i).getAnimation().update();
                        }
                    }

                    loadGame.getTreeAnimation().update();
                    loadGame.getWaterFountainAnimation().update();
                    loadGame.getFlameAnimation().update();
                    loadGame.getDogAnimation().update();
                    loadGame.getMushroomAnimation().update();
                    loadGame.getMushroomRopesAnimation().update();
                    loadGame.getHouseSmokeAnimation().update();
                    loadGame.getShrineAnimation().update();
                    loadGame.getFishingMushroom().update();

                    player.getAnimation().update();
                } else {
                    menu.getMenuAnimation().update();
                    if (gameState == GameState.LOADING_STATE) {
                        loadGame.loadingGame();
                        try {
                            if (dbRunning) {
                                dataDao.updateGolds(player.getGold());
                            }
                        } catch (SQLException ex) {
                            throw new IllegalStateException("Failed to update the start gold to the database!");
                        }
                    } else if (gameState == GameState.OPTION_STATE && menu.isDataReseted()) {
                        try {
                            if (isDbRunning()) {
                                dataDao.resetData();
                                dataScore = dataDao.getDatas();
                            }
                        } catch (SQLException ex) {
                            throw new IllegalStateException("Failed to reset the database!");
                        }
                    }
                }

                frames++;

                if (System.currentTimeMillis() - fpsTimer > 1000) {
                    fpsTimer += 1000;
                    framePerSecond = frames;
                    frames = 0;
                }
                if (myPanel != null)
                    myPanel.repaint();
            }
        };

        logic = new Timer("Logic Thread");
        logic.scheduleAtFixedRate(timerTask, FPS, PERIOD);
    }

    private boolean isProcessRunning() throws Exception {
        final Process process = Runtime.getRuntime().exec("sc query \"MariaDB\"");
        final Scanner reader = new Scanner(process.getInputStream(), StandardCharsets.UTF_8);
        while (reader.hasNextLine())
            if (reader.nextLine().contains("RUNNING"))
                return true;
        return false;
    }

    public void creatingRandomCharacters() {
        randomCardCharacters.clear();
        int[] random = new int[5];
        int[] x = new int[5];
        for (int i = 0; i < 5; i++) {
            random[i] = creatingRandomNumbers();
            x[i] = i * 125;
        }

        for (int i = 0; i < random.length; i++) {
            if (random[i] == 0) {
                randomCardCharacters.add(new Carrot(380 + x[i], (int) loadGame.getCardPanelShapeList().get(0).getBounds().getY()));
            } else if (random[i] == 1) {
                randomCardCharacters.add(new RedSlime(380 + x[i], (int) loadGame.getCardPanelShapeList().get(0).getBounds().getY()));
            } else if (random[i] == 2) {
                randomCardCharacters.add(new Saint(380 + x[i], (int) loadGame.getCardPanelShapeList().get(0).getBounds().getY()));
            } else if (random[i] == 3) {
                randomCardCharacters.add(new Wise(380 + x[i], (int) loadGame.getCardPanelShapeList().get(0).getBounds().getY()));
            } else if (random[i] == 4) {
                randomCardCharacters.add(new SpikedSlime(380 + x[i], (int) loadGame.getCardPanelShapeList().get(0).getBounds().getY()));
            } else if (random[i] == 5) {
                randomCardCharacters.add(new AppleSlime(380 + x[i], (int) loadGame.getCardPanelShapeList().get(0).getBounds().getY()));
            }
        }
    }

    private synchronized int creatingRandomNumbers() {
        return rnd.nextInt(6);
    }

    private void calculateCharactersBehaviour() {
        if (!isPlanningTime.get()) {
            threads.clear();
            fightingAlliances.clear();
            fightingAlliances.addAll(alliances);
            fightingEnemies.clear();
            fightingEnemies.addAll(enemies);

            if (selectedCharacter != null && fightingAlliances.contains(selectedCharacter)) {
                selectedCharacter.setX(selectedCharacter.getOriginalPosX());
                selectedCharacter.setY(selectedCharacter.getOriginalPosY());
                selectedCharacter = null;
            }

            for (Character enemy : fightingEnemies) {
                if (enemy.getAnimation() != null) enemy.getAnimation().start();
                threads.add(new Thread(() -> enemy.behaviour(enemy)));
            }

            for (Character hero : fightingAlliances) {
                threads.add(new Thread(() -> hero.behaviour(hero)));
            }

            threads.forEach(Thread::start);
            threads.forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void characterLevelingUp(Character character) {
        int count = 0;
        int countThree = 3;
        int countHigherTwo = 0;
        if (alliancesWaiting.contains(character) || alliances.contains(character)) {
            for (int j = alliancesWaiting.size() - 1; j >= 0; j--) {
                if (alliancesWaiting.get(j).getName().equals(character.getName())
                        && alliancesWaiting.get(j).getLevel() == character.getLevel()
                        && character.getLevel() < 3) {
                    count++;
                } else if (alliancesWaiting.get(j).getName().equals(character.getName())
                        && alliancesWaiting.get(j).getLevel() == 2
                        && character.getLevel() < 3) {
                    countHigherTwo++;
                }
            }
            for (int j = alliances.size() - 1; j >= 0; j--) {
                if (alliances.get(j).getName().equals(character.getName())
                        && alliances.get(j).getLevel() == character.getLevel()
                        && character.getLevel() < 3
                        && isPlanningTime.get()) {
                    count++;
                } else if (alliances.get(j).getName().equals(character.getName())
                        && alliances.get(j).getLevel() == 2
                        && character.getLevel() < 3
                        && isPlanningTime.get()) {
                    countHigherTwo++;
                }
            }

            if (count >= countThree) {
                boolean wasAlliance = false;
                Iterator<Character> itr2 = getAlliances().iterator();
                while (itr2.hasNext() && countThree != 0) {
                    Character c = itr2.next();
                    if (c.getName().equals(character.getName())
                            && c.getLevel() == character.getLevel()
                            && isPlanningTime.get()) {
                        characterLevelingUpIterators(character, itr2, c);
                        wasAlliance = true;
                        countThree--;
                    }
                }

                Iterator<Character> itr = getAlliancesWaiting().iterator();
                while (itr.hasNext() && countThree != 0) {
                    Character c = itr.next();
                    if (c.getName().equals(character.getName())
                            && c.getLevel() == character.getLevel()) {
                        Key<Integer, Integer> key = new Key<>(c.getX() - 20, c.getY() - 20);
                        Key<Integer, Integer> key2 = new Key<>(character.getX() - 20, character.getY() - 20);
                        loadGame.getCharacterPlayingMap().replace(key2, FieldState.EMPTY);
                        loadGame.getCharacterWaitingMap().replace(key, FieldState.EMPTY);
                        itr.remove();
                        countThree--;
                    }
                }

                Key<Integer, Integer> key = new Key<>(character.getX() - 20, character.getY() - 20);
                loadGame.getCharacterPlayingMap().replace(key, FieldState.OCCUPIED);
                character.getAnimation().stop();
                if (wasAlliance) {
                    alliancesWaiting.remove(character);
                    alliances.add(character);
                    character.setOriginalPos();
                } else {
                    loadGame.getCharacterWaitingMap().replace(key, FieldState.OCCUPIED);
                    alliancesWaiting.add(character);
                }

                character.setLevel(character.getLevel() + 1);
                character.getAnimation().start();
                if (character.getLevel() < 3 && countHigherTwo >= 2) {
                    Character characterUpToThree = null;
                    for (int j = alliancesWaiting.size() - 1; j >= 0; j--) {
                        if (alliancesWaiting.get(j).getName().equals(character.getName())
                                && alliancesWaiting.get(j).getLevel() == 2) {
                            characterUpToThree = alliancesWaiting.get(j);
                            break;
                        }
                    }
                    characterLevelingUp(characterUpToThree);
                }
            }
        }
    }

    private void characterLevelingUpIterators(Character character, Iterator<Character> itr2, Character c) {
        Key<Integer, Integer> key = new Key<>(c.getX() - 20, c.getY() - 20);
        loadGame.getCharacterPlayingMap().replace(key, FieldState.EMPTY);
        Key<Integer, Integer> key2 = new Key<>(character.getX() - 20, character.getY() - 20);
        loadGame.getCharacterWaitingMap().replace(key2, FieldState.EMPTY);
        character.setX(c.getOriginalPosX());
        character.setY(c.getOriginalPosY());
        itr2.remove();
    }

    private void reset() {
        lock.lock();
        charactersBullet.clear();

        if ((fightingAlliances.isEmpty() || !fightingEnemies.isEmpty()) && !isPlanningTime.get()) {
            player.setHealth(player.getHealth() - (enemies.size() + gameLevel * 2));
        }
        fightingAlliances.clear();
        isPlanningTime.set(true);
        isOver.set(true);

        if (player.getHealth() <= 0) {
            isPlanningTime.set(false);
        }

        try {
            int i = 0;
            int j = 0;
            int k = 0;
            while (i < alliances.size()) {
                alliances.get(i).originalPos();
                alliances.get(i).setMaxHealth();
                alliances.get(i).setStartMana();
                alliances.get(i).setInRange(false);
                alliances.get(i).setDirection(Direction.NORTH);
                alliances.get(i).setTargetEnemyCharacter(null);
                alliances.get(i).getAnimation().restart();
                i++;
            }

            while (j < alliancesWaiting.size()) {
                characterLevelingUp(alliancesWaiting.get(j));
                j++;
            }


            if (!isCardsLock()) {
                creatingRandomCharacters();
            }

            boolean playerWin = false;
            if (fightingEnemies.isEmpty()) {
                playerWin = true;
                if (player.getWinStreak() < 5) {
                    player.setWinStreak(player.getWinStreak() + 1);
                }
                enemies.clear();
                player.setLoseStreak(0);
                gameLevel++;
            } else {
                if (player.getLoseStreak() < 5) {
                    player.setLoseStreak(player.getLoseStreak() + 1);
                }
                player.setWinStreak(0);

                while (k < enemies.size()) {
                    enemies.get(k).originalPos();
                    enemies.get(k).setMaxHealth();
                    enemies.get(k).setStartMana();
                    enemies.get(k).setDirection(Direction.SOUTH);
                    enemies.get(k).setInRange(false);
                    enemies.get(k).setTargetEnemyCharacter(null);
                    k++;
                }
            }

            fightingEnemies.clear();

            player.setLevelingLine(player.getLevelingLine() + LEVELING_LINE_VALUE_PER_ROUND);

            int playerStackingGold = player.getGold() / 10;
            if (playerStackingGold > 5) {
                playerStackingGold = 5;
            }

            if (playerWin) {
                player.setGold(player.getGold() + (int) (1.25 * player.getWinStreak()) + playerStackingGold + 2);

                if (dbRunning) {
                    dataDao.updateWins();
                    dataDao.updateGolds((int) (1.25 * player.getWinStreak()) + playerStackingGold + 2);
                }
            } else {
                player.setGold(player.getGold() + (2 * player.getLoseStreak()) + playerStackingGold + 2);

                if (dbRunning) {
                    dataDao.updateLoses();
                    dataDao.updateGolds(2 * player.getLoseStreak() + playerStackingGold + 2);
                }
            }


            if (dbRunning) {
                dataDao.updateDeath(deaths);
                dataDao.updateKills(kills);
                dataDao.updateSpentGolds(spentGold);
            }

            deaths = 0;
            kills = 0;
            spentGold = 0;

        } catch (SQLException throwables) {
            throw new IllegalStateException("Failed to save the data to the database!");
        } finally {
            lock.unlock();
        }
        int i = 0;
        while (i < alliances.size()) {
            characterLevelingUp(alliances.get(i));
            i++;
        }
    }

    private void creatingEnemyByGameLevel() {

        int enemySize = 2;
        int countFightingEnemy = 0;

        if (gameLevel >= 12) {
            enemySize = 7;
        } else if (gameLevel >= 7) {
            enemySize = 5;
        } else if (gameLevel >= 5) {
            enemySize = 4;
        } else if (gameLevel >= 3) {
            enemySize = 3;
        }

        while (countFightingEnemy < enemySize) {
            int random = creatingRandomNumbers();
            if (random == 0) {
                enemies.add(new Carrot(0, 0));
                countFightingEnemy++;
            } else if (random == 1) {
                enemies.add(new RedSlime(0, 0));
                countFightingEnemy++;
            } else if (random == 2) {
                enemies.add(new Saint(0, 0));
                countFightingEnemy++;
            } else if (random == 3) {
                enemies.add(new Wise(0, 0));
                countFightingEnemy++;
            } else if (random == 4) {
                enemies.add(new SpikedSlime(0, 0));
                countFightingEnemy++;
            } else if (random == 5) {
                enemies.add(new AppleSlime(0, 0));
                countFightingEnemy++;
            }
        }

        for (Character enemy : enemies) {
            if (gameLevel >= 15) {
                enemy.setLevel(3);
            } else if (gameLevel >= 6) {
                enemy.setLevel(2);
            }
        }

        int countOnMap = 0;

        for (Map.Entry<Key<Integer, Integer>, FieldState> entry :
                loadGame.getEnemyPlayingMap().entrySet()) {
            entry.setValue(FieldState.EMPTY);
        }

        for (Map.Entry<Key<Integer, Integer>, FieldState> entry : loadGame.getEnemyPlayingMap().entrySet()) {
            if (countOnMap < enemies.size() && entry.getValue() == FieldState.EMPTY) {
                entry.setValue(FieldState.OCCUPIED);
                enemies.get(countOnMap).setX(entry.getKey().key1 + 20);
                enemies.get(countOnMap).setY(entry.getKey().key2 + 20);
                enemies.get(countOnMap).setOriginalPos();
                countOnMap++;
            }
        }
    }

    private void imTooLazyToPlayThisGame() {
        lock.lock();
        try {
            if (alliances.size() < player.getPlayableCharacterNum()) {
                if (alliancesWaiting.isEmpty() && alliances.isEmpty()) {
                    isOver.set(true);
                } else if (!alliancesWaiting.isEmpty()) {
                    int countOnMap = alliances.size();

                    for (Character character : alliancesWaiting) {
                        if (alliances.size() < player.getPlayableCharacterNum()) {
                            alliances.add(character);
                            Key<Integer, Integer> key;
                            if (selectedCharacter != null && character == selectedCharacter) {
                                key = new Key<>(character.getOriginalPosX() - 20, character.getOriginalPosY() - 20);
                            } else {
                                key = new Key<>(character.getOriginalPosX(), character.getOriginalPosY());
                            }

                            loadGame.getCharacterWaitingMap().replace(key, FieldState.EMPTY);
                        }
                    }

                    for (Map.Entry<Key<Integer, Integer>, FieldState> entry : loadGame.getCharacterPlayingMap().entrySet()) {
                        if (countOnMap < alliances.size() && entry.getValue() == FieldState.EMPTY) {
                            entry.setValue(FieldState.OCCUPIED);
                            alliancesWaiting.remove(alliances.get(countOnMap));
                            alliances.get(countOnMap).setX(entry.getKey().key1 + 20);
                            alliances.get(countOnMap).setY(entry.getKey().key2 + 20);
                            alliances.get(countOnMap).setOriginalPos();
                            countOnMap++;
                        }
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void startGame() {
        isPlanningTime.set(false);
        isOver.set(false);

        if (enemies.isEmpty()) {
            creatingEnemyByGameLevel();
        }

        imTooLazyToPlayThisGame();
        calculateCharactersBehaviour();
    }
}
