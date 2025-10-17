package io.jona.framework;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class JonaDb {
    static String url;
    static String user;
    static String password;

    public static void init(String url, String user, String password) {
        JonaDb.url = url;
        JonaDb.user = user;
        JonaDb.password = password;
    }

    public static boolean insert(Table table) {
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            PreparedStatement stmt = conn.prepareStatement(table.getInsert());
            Object[] values = table.getValues();
            for(int i = 0, j = 1; i < values.length; i++, j++) {
                if (values[i] instanceof Integer)
                    stmt.setInt(j, ((Integer) values[i]));
                else
                    stmt.setString(j, values[i].toString());
            }
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new client was inserted successfully!");
                return true;
            }
        } catch (SQLException e) {
            log.error("Insert fail " + e);
        }
        return false;
    }

    public static boolean delete(Table table) {
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            PreparedStatement stmt = conn.prepareStatement(table.getDelete((Integer) table.getValues()[0]));
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Client deleted successfully!");
                return true;
            }
        } catch (SQLException e) {
            log.error("Delete fail " + e);
        }
        return false;
    }

    public static <T extends Table> T selectSingle(String query, Function<ResultSet, T> rowMapper, String... queryMappings) {

    }

    public static <T extends Table> List<T> selectList(String query, Function<ResultSet, T> rowMapper, String ... queryMappings) {

    }
}
