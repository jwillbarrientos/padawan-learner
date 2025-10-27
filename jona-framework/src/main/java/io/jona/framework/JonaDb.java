package io.jona.framework;

import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.ArrayList;
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
            int index = 1;
            for(Object mapping : table.getValues()) {
                if (mapping instanceof Long)
                    stmt.setLong(index++, ((Long) mapping));
                else if (mapping instanceof Integer)
                    stmt.setInt(index++, (Integer) mapping);
                else if (mapping instanceof Date)
                    stmt.setDate(index++, (Date) mapping);
                else
                    stmt.setString(index++, mapping.toString());
            }
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                log.info("A new element in table was inserted successfully!");
                return true;
            }
        } catch (SQLException e) {
            log.error("Insert fail " + e);
        }
        return false;
    }

    public static boolean update(Table table) {
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            PreparedStatement stmt = conn.prepareStatement(table.getUpdate());
            int index = 1;
            for(Object mapping : table.getValues()) {
                if (mapping instanceof Long)
                    stmt.setLong(index++, ((Long) mapping));
                else if (mapping instanceof Integer)
                    stmt.setInt(index++, (Integer) mapping);
                else if (mapping instanceof Date)
                    stmt.setDate(index++, (Date) mapping);
                else
                    stmt.setString(index++, mapping.toString());
            }
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                log.info("Element in table updated successfully");
                return true;
            }
        } catch (SQLException e) {
            log.error("Update fail " + e);
        }
        return false;
    }

    public static boolean delete(Table table) {
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            PreparedStatement stmt = conn.prepareStatement(table.getDelete());
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                log.info("Element in table deleted successfully!");
                return true;
            }
        } catch (SQLException e) {
            log.error("Delete fail " + e);
        }
        return false;
    }

    public static <T extends Table> T selectSingle(String query, Function<ResultSet, T> rowMapper, Object... queryMappings) {
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            PreparedStatement stmt = conn.prepareStatement(query);
            int index = 1;
            for(Object mapping : queryMappings) {
                if (mapping instanceof Long)
                    stmt.setLong(index++, ((Long) mapping));
                else if (mapping instanceof Integer)
                    stmt.setInt(index++, (Integer) mapping);
                else if (mapping instanceof Date)
                    stmt.setDate(index++, (Date) mapping);
                else
                    stmt.setString(index++, mapping.toString());
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rowMapper.apply(rs);
        } catch (SQLException e) {
            log.error("Select single fail " + e);
        }
        return null;
    }

    public static <T extends Table> List<T> selectList(String query, Function<ResultSet, T> rowMapper, Object... queryMappings) {
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
           PreparedStatement stmt = conn.prepareStatement(query);
           int index = 1;
           for(Object mapping : queryMappings) {
               if (mapping instanceof Long)
                   stmt.setLong(index++, (Long) mapping);
               else if (mapping instanceof Integer)
                   stmt.setInt(index++, (Integer) mapping);
               else if (mapping instanceof Date)
                   stmt.setDate(index++, (Date) mapping);
               else
                   stmt.setString(index++, mapping.toString());
           }
           ResultSet rs = stmt.executeQuery();
           List<T> list = new ArrayList<>();
           while(rs.next())
               list.add(rowMapper.apply(rs));
           return list;
        } catch (SQLException e) {
            log.error("Select list fail " + e);
        }
        return null;
    }
}
