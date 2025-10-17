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
public class Video extends Table {
    private int id;
    private String name;
    private String link;
    private String path;
    private int durationSeconds;
    private String videoState;
    private int clientId;

    public String getDelete(int id) {
        return "delete from video where id = " + id;
    }

    public String getInsert() {
        return "insert into video (id, name, link, path, duration_seconds, video_state, client_id) values(?,?,?,?,?,?,?)";
    }

    public Object[] getValues() {
        return new Object[] {id, name, link, path, durationSeconds, videoState, clientId};
    }

    public static String getById(int id) {
        return "select id, name, link, path, duration_seconds, video_state, client_id from video where id = " + id;
    }

    public static Function<ResultSet, Video> getFullMapping() {
        return resulSet -> {
            try {
                new Video(
                        resulSet.getInt(1),
                        resulSet.getString(2),
                        resulSet.getString(3),
                        resulSet.getString(4),
                        resulSet.getInt(5),
                        resulSet.getString(6),
                        resulSet.getInt(7)
                );
            } catch (SQLException e) {
                log.error("Exception in getFullMapping() " + e);
            }
            return null;
        };
    }
}
