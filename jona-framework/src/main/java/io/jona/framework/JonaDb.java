package io.jona.framework;

import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class JonaDb {
    private static String url;
    private static String user;
    private static String password;

    public static void init(String url, String user, String password) {
        JonaDb.url = url;
        JonaDb.user = user;
        JonaDb.password = password;
    }

    public static boolean insert(Table table) {
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            PreparedStatement stmt = conn.prepareStatement(table.getInsert());
            setMapping(stmt, table.getValues());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                log.info("A new element in table was inserted successfully!");
                return true;
            }
        } catch (SQLException e) {
            log.error("Insert fail: ", e);
        }
        return false;
    }

    public static boolean update(Table table) {
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            PreparedStatement stmt = conn.prepareStatement(table.getUpdate());
            setMapping(stmt, table.getValues());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                log.info("Element in table updated successfully");
                return true;
            }
        } catch (SQLException e) {
            log.error("Update fail: ", e);
        }
        return false;
    }

    public static boolean delete(Table table) {
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            PreparedStatement stmt = conn.prepareStatement(table.getDelete());
            int rowsDeleted = stmt.executeUpdate();
            log.info("Element in table deleted successfully!");
            return rowsDeleted > 0;
        } catch (SQLException e) {
            log.error("Delete fail: ", e);
        }
        return false;
    }

    public static <T extends Table> T selectSingle(String query, Table.ThrowingFunction<ResultSet, T, SQLException> rowMapper, Object... queryMappings) {
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            PreparedStatement stmt = conn.prepareStatement(query);
            setMapping(stmt, queryMappings);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rowMapper.apply(rs);
        } catch (SQLException e) {
            log.error("Select single fail: ", e);
        }
        return null;
    }

    public static <T extends Table> List<T> selectList(String query, Table.ThrowingFunction<ResultSet, T, SQLException> rowMapper, Object... queryMappings) {
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
           PreparedStatement stmt = conn.prepareStatement(query);
           setMapping(stmt, queryMappings);
           ResultSet rs = stmt.executeQuery();
           List<T> list = new ArrayList<>();
           while(rs.next())
               list.add(rowMapper.apply(rs));
           return list;
        } catch (SQLException e) {
            log.error("Select list fail: ", e);
        }
        return null;
    }

    private static void setMapping(PreparedStatement ps, Object... queryMappings) throws SQLException {
        int i = 1;
        for(Object mapping : queryMappings) {
            if (mapping instanceof Long)
                ps.setLong(i++, (Long) mapping);
            else if (mapping instanceof Integer)
                ps.setInt(i++, (Integer) mapping);
            else if (mapping instanceof Timestamp)
                ps.setTimestamp(i++, (Timestamp) mapping);
            else
                ps.setString(i++, mapping.toString());
        }
    }
}
