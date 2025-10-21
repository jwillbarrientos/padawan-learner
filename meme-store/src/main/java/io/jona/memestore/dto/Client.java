package io.jona.memestore.dto;

import io.jona.framework.Table;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@Slf4j
@AllArgsConstructor
public class Client extends Table {
    private long id;
    private String email;
    private String password;

    public Client(String email, String password) {
        this.id = super.getId().get();
        this.email = email;
        this.password = password;
    }

    public String getDelete(int id) {
        return "delete from client where id = " + id;
    }

    public String getInsert() {
        return "insert into client (id, email, password) values(?,?,?)";
    }

    public Object[] getValues() {
        return new Object[] {id, email, password};
    }

    public static String getById(int id) {
        return "select id, email, password from client where id = " + id;
    }

    public static Function<ResultSet, Client> getFullMapping() {
        return resulSet -> {
            try {
                return new Client(
                    resulSet.getLong(1),
                    resulSet.getString(2),
                    resulSet.getString(3)
                );
            } catch (SQLException e) {
                log.error("Exception in getFullMapping() " + e);
                return null;
            }
        };
    }

    @Override
    public String toString() {
        return "Client id = " + id +
                ", email = " + email +
                ", password = " + password;
    }
}
