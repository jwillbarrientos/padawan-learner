package io.jona.framework;

import lombok.extern.slf4j.Slf4j;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class JonaDb {
    static String url;
    static String user;
    static String password;

    public static void init(String url, String user, String password) {
        JonaDb.url = url;
        JonaDb.user = user;
        JonaDb.password = password;
        System.out.println(JonaDb.url);
        System.out.println(JonaDb.user);
        System.out.println(JonaDb.password);
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
}
