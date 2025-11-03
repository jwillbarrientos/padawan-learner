package io.jona.memestore.dto;

import io.jona.framework.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@Slf4j
@AllArgsConstructor
public class Client extends Table {
    @Getter
    private long id = nextId();
    @Getter
    private String email;
    private String password;

    public Client(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getInsert() {
        return "insert into client (id, email, password) values(?,?,?)";
    }

    public String getUpdate() {
        return "nothing yet";
    }

    public String getDelete() {
        return "delete from client where id = " + id;
    }

    public Object[] getValues() {
        return new Object[] {id, email, password};
    }

    public static String getById(long id) {
        return "select id, email, password from client where id = " + id;
    }

    public static ThrowingFunction<ResultSet, Client, SQLException> getFullMapping() {
        return rs -> new Client(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3)
        );
    }

    @Override
    public String toString() {
        return "Client id = " + id +
                ", email = " + email +
                ", password = " + password;
    }
}
