package hu.elte.fi.szakdolgozat.persistence.entity;

public class DataScore extends hu.elte.fi.szakdolgozat.persistence.entity.AbstractEntity {

    private int kills;
    private int death;
    private int wins;
    private int loses;
    private int golds;
    private int spent_gold;

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getGolds() {
        return golds;
    }

    public void setGolds(int golds) {
        this.golds = golds;
    }

    public int getSpent_gold() {
        return spent_gold;
    }

    public void setSpent_gold(int spent_gold) {
        this.spent_gold = spent_gold;
    }
}
