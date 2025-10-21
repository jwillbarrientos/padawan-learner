package io.jona.memestore.dto;

import io.jona.framework.Table;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@AllArgsConstructor
public class Video extends Table {
    private long id;
    private String name;
    private String link;
    private String path;
    private int durationSeconds;
    private String videoState;
    private Date date;
    private int clientId;

    public Video(String name, String link, String path, int durationSeconds, String videoState, Date date, int clientId) {
        this.id = super.getId().get();
        this.name = name;
        this.link = link;
        this.path = path;
        this.durationSeconds = durationSeconds;
        this.videoState = videoState;
        this.date = date;
        this.clientId = clientId;
    }

    public String getDelete(int id) {
        return "delete from video where id = " + id;
    }

    public String getInsert() {
        return "insert into video (id, name, link, path, duration_seconds, video_state, date, client_id) values(?,?,?,?,?,?,?)";
    }

    public Object[] getValues() {
        return new Object[] {id, name, link, path, durationSeconds, videoState, date, clientId};
    }

    public static String getById(int id) {
        return "select id, name, link, path, duration_seconds, video_state, date, client_id from video where id = " + id;
    }

    public static Function<ResultSet, Video> getFullMapping() {
        return resulSet -> {
            try {
                return new Video(
                        resulSet.getInt(1),
                        resulSet.getString(2),
                        resulSet.getString(3),
                        resulSet.getString(4),
                        resulSet.getInt(5),
                        resulSet.getString(6),
                        resulSet.getDate(7),
                        resulSet.getInt(8)
                );
            } catch (SQLException e) {
                log.error("Exception in getFullMapping() " + e);
                return null;
            }
        };
    }

    @Override
    public String toString() {
        return "Video id = " + id +
                ", name = " + name +
                ", link = " + link +
                ", path = " + path +
                ", duration_seconds = " + durationSeconds +
                ", video_state = " + videoState +
                ", date = " + date +
                ", client_id + " + clientId;
    }
}
