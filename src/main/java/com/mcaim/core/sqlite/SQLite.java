package com.mcaim.core.sqlite;

import com.mcaim.core.Core;
import com.mcaim.core.scheduler.Async;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.*;
import java.util.Objects;
import java.util.UUID;

public class SQLite {
    private static final String TABLE = "users";
    private static Connection connection = null;
    private static File file = null;

    private static String userTable() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE +
                " ('PlayerUUID' VARCHAR(200) NOT NULL, 'Balance' INT(10), " +
                "PRIMARY KEY ('PlayerUUID'));";
    }

    private static Connection getSQLConnection() {
        try {
            if (connection != null && !connection.isClosed())
                return connection;

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void createSQLFile() {
        file = new File(Core.getInstance().getDataFolder(), TABLE + ".db");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*public static void storeCooldown(Player player) {
        String addCooldown = "REPLACE INTO" + TABLE + "(PlayerUUID, Cooldown) VALUES (?,?)";
        Connection connection = getSQLConnection();

        if (connection != null) {
            try (PreparedStatement statement = connection.prepareStatement(addCooldown)) {
                ResultSet result = statement.executeQuery(addCooldown);

                statement.setString(1, uuid.toString());
                statement.setInt(2, 750);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/

    public static void storeBalanceToDB(Player player) {
        String addFly = "REPLACE INTO " + TABLE + " (PlayerUUID, Balance) VALUES (?,?)";
        UUID uuid = player.getUniqueId();
        Connection connection = getSQLConnection();

        if (connection != null) {
            try (PreparedStatement statement = connection.prepareStatement(addFly)) {
                ResultSet result = statement.executeQuery("REPLACE INTO " + TABLE + " (PlayerUUID, Balance) VALUES (?,?)");

                statement.setString(1, uuid.toString());
                statement.setInt(2, 750);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getAndSetBalance(Player player) {
        Async.get().run(() -> {
            Connection connection = Objects.requireNonNull(getSQLConnection());

            try (Statement statement = connection.createStatement()) {
                ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE + " WHERE PlayerUUID='" + player.getUniqueId() + "'");

                while (result.next()) {
                    Integer balance = result.getInt("Balance");
                    //Balance.balances.put(player.getUniqueId(), balance);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void initSQL() {
        createSQLFile();

        try {
            Connection connection = Objects.requireNonNull(getSQLConnection());
            Statement statement = connection.createStatement();
            statement.executeUpdate(userTable());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
