package io.jona.memestore.dto;

import io.jona.framework.Table;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@Slf4j
@AllArgsConstructor
public class Tag extends Table {
    int id;
    String name;
    int clientId;

    public String getDelete() {
        return "delete from CLIENT where id = " + id;
    }

    public String getInsert() {
        return "insert into CLIENT (id, name, client_id) values(?,?,?)";
    }

    public Object[] getValues() {
        return new Object[] {id, name, clientId};
    }

    public static String getById(int id) {
        return "select id, name, client_id from tag where id = " + id;
    }

    public static Function<ResultSet, Tag> getFullMapping() {
        return resulSet -> {
            try {
                new Tag(
                        resulSet.getInt(1),
                        resulSet.getString(2),
                        resulSet.getInt(3)
                );
            } catch (SQLException e) {
                log.error("Exception in getFullMapping() " + e);
            }
            return null;
        };
    }
}
