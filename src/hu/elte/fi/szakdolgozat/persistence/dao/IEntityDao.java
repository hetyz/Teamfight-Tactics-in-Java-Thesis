package hu.elte.fi.szakdolgozat.persistence.dao;

import hu.elte.fi.szakdolgozat.persistence.entity.DataScore;
import hu.elte.fi.szakdolgozat.persistence.entity.Identifiable;

import java.io.Serializable;
import java.sql.SQLException;

public interface IEntityDao<E extends Identifiable<Long> & Serializable> {

    DataScore getDatas() throws SQLException;

    void updateKills(int kills) throws SQLException;
    void updateDeath(int death) throws SQLException;
    void updateWins() throws SQLException;
    void updateLoses() throws SQLException;
    void updateGolds(int gold) throws SQLException;
    void updateSpentGolds(int spentGold) throws SQLException;
    void resetData() throws SQLException;
    void createTable() throws SQLException;
    void createDatabase() throws SQLException;
    void addFirstData() throws SQLException;
}
