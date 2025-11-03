package io.jona.memestore.dto;

import io.jona.framework.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@Slf4j
@AllArgsConstructor
public class Tag extends Table {
    @Getter
    private long id = nextId();
    @Getter @Setter
    private String name;
    private long clientId;

    public Tag(String name, long clientId) {
        this.name = name;
        this.clientId = clientId;
    }

    public String getInsert() {
        return "insert into tag (id, name, client_id) values(?,?,?)";
    }

    public String getUpdate() {
        return "update tag set id = ?, name = ?, client_id = ? where id = " + id;
    }

    public String getDelete() {
        return "delete from tag where id = " + id;
    }

    public Object[] getValues() {
        return new Object[] {id, name, clientId};
    }

    public static String getById(long id) {
        return "select id, name, client_id from tag where id = " + id;
    }

    public static ThrowingFunction<ResultSet, Tag, SQLException> getFullMapping() {
        return rs -> new Tag(
                rs.getLong(1),
                rs.getString(2),
                rs.getLong(3)
        );
    }

    @Override
    public String toString() {
        return "tag id = " + id +
                ", name = " + name +
                ", client_id = " + clientId;
    }
}
