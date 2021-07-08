package hu.elte.fi.szakdolgozat.persistence.dao;


import hu.elte.fi.szakdolgozat.persistence.connection.DataSource;
import hu.elte.fi.szakdolgozat.persistence.entity.DataScore;

import java.sql.*;

public class DataDao implements hu.elte.fi.szakdolgozat.persistence.dao.IEntityDao<DataScore> {

    private static final String DATA_QUERY = "select * from data where id = 1";
    private static final String UPDATE_KILLS_QUERY = "update data set kills = kills + ? where id = 1";
    private static final String UPDATE_DEATH_QUERY = "update data set death = death + ? where id = 1";
    private static final String UPDATE_WINS_QUERY = "update data set wins = wins + 1 where id = 1";
    private static final String UPDATE_LOSES_QUERY = "update data set loses = loses + 1 where id = 1";
    private static final String UPDATE_GOLDS_QUERY = "update data set golds = golds + ? where id = 1";
    private static final String UPDATE_SPENT_GOLDS_QUERY = "update data set spent_gold = spent_gold + ? where id = 1";
    private static final String RESET_DATA_QUERY = "update data set kills = 0, death = 0, wins = 0, loses = 0, golds = 0, spent_gold = 0 where id = 1";
    private static final String CREATE_NEW_TABLE_QUERY = "create table if not exists data (\n"
            + "id INT PRIMARY KEY AUTO_INCREMENT,\n"
            + "kills INT,\n"
            + "death INT,\n"
            + "wins INT,\n"
            + "loses INT,\n"
            + "golds INT,\n"
            + "spent_gold INT)";
    private static final String ADD_FIRST_DATA_QUERY = "insert ignore into data (id,kills, death, wins, loses, golds, spent_gold) values (1,0,0,0,0,0,0)";

    private static final String ATTR_NAME_ID = "id";
    private static final String ATTR_NAME_KILLS = "kills";
    private static final String ATTR_NAME_DEATH = "death";
    private static final String ATTR_NAME_WINS = "wins";
    private static final String ATTR_NAME_LOSES = "loses";
    private static final String ATTR_NAME_GOLDS = "golds";
    private static final String ATTR_NAME_SPENT_GOLDS = "spent_gold";

    @Override
    public synchronized DataScore getDatas() throws SQLException {
        try (Connection connection = DataSource.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(DATA_QUERY)) {

            DataScore dataScore = new DataScore();

            while (resultSet.next()) {
                dataScore.setId(resultSet.getLong(ATTR_NAME_ID));
                dataScore.setKills(resultSet.getInt(ATTR_NAME_KILLS));
                dataScore.setDeath(resultSet.getInt(ATTR_NAME_DEATH));
                dataScore.setWins(resultSet.getInt(ATTR_NAME_WINS));
                dataScore.setLoses(resultSet.getInt(ATTR_NAME_LOSES));
                dataScore.setGolds(resultSet.getInt(ATTR_NAME_GOLDS));
                dataScore.setSpent_gold(resultSet.getInt(ATTR_NAME_SPENT_GOLDS));
            }
            return dataScore;
        }
    }

    @Override
    public synchronized void updateKills(int kills) throws SQLException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_KILLS_QUERY)) {
            preparedStatement.setInt(1, kills);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized void updateDeath(int deaths) throws SQLException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEATH_QUERY)) {
            preparedStatement.setInt(1, deaths);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized void updateWins() throws SQLException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_WINS_QUERY)) {
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized void updateLoses() throws SQLException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LOSES_QUERY)) {
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized void updateGolds(int gold) throws SQLException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GOLDS_QUERY)) {
            preparedStatement.setInt(1, gold);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized void updateSpentGolds(int spentGold) throws SQLException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SPENT_GOLDS_QUERY)) {
            preparedStatement.setInt(1, spentGold);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized void resetData() throws SQLException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(RESET_DATA_QUERY)) {
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void createDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/", "root", "bezzegh32");
             Statement stmt = connection.createStatement()) {
            String sql = "CREATE DATABASE IF NOT EXISTS szakdolgozat DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci";
            String sql2 = "CREATE USER if not exists 't4o94u'@'localhost' IDENTIFIED BY 'bezzegh32'";
            String sql3 = "GRANT ALL ON szakdolgozat.* TO 't4o94u'@'localhost'";
            stmt.addBatch(sql);
            stmt.addBatch(sql2);
            stmt.addBatch(sql3);
            stmt.executeBatch();
        }
    }

    @Override
    public void createTable() throws SQLException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_TABLE_QUERY)) {
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void addFirstData() throws SQLException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_FIRST_DATA_QUERY)) {
            preparedStatement.executeUpdate();
        }
    }
}
