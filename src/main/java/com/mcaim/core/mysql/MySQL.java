package com.mcaim.core.mysql;

import com.mcaim.core.Core;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.UUID;

public class MySQL {
    private static final FileConfiguration config = Core.getInstance().getConfig();

    private static final String host = config.getString("MySQL.host");
    private static final String port = config.getString("MySQL.port");
    private static final String database = config.getString("MySQL.database");
    private static final String username = config.getString("MySQL.username");
    private static final String password = config.getString("MySQL.password");

    private static Connection connection;

    public static boolean isConnected() {
        return connection != null;
    }

    public static void connect() throws SQLException {
        if (!isConnected())
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
    }

    public static void disconnect() throws SQLException {
        if (isConnected())
            connection.close();
    }

    public static Connection getConnection() { return connection; }

    public static PreparedStatement getStatement(String statement) throws SQLException {
        return getConnection().prepareStatement(statement);
    }

    public static void addUuid(UUID uuid, String table) {
        try {
            if (!uuidExists(uuid, table)) {
                PreparedStatement ps = getConnection().prepareStatement("INSERT IGNORE INTO " + table + " (UUID) VALUES (?)");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean uuidExists(UUID uuid, String table) {
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
