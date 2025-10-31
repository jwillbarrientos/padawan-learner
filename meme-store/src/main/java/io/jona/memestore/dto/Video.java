package io.jona.memestore.dto;

import io.jona.framework.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Video extends Table {
    public enum State {
        SUBMITTED, DOWNLOADED, ERROR_DOWNLOADING
    }
    private final Date dateSetter = new Date();

    @Setter @Getter
    private long id = nextId();
    @Setter
    private String name;
    @Setter @Getter
    private String link;
    @Setter @Getter
    private String path;
    @Setter
    private int durationSeconds;
    @Setter
    private State videoState;
    @Setter
    private Timestamp date;
    @Setter
    private long clientId;

    public Video(String name,
                 String link,
                 String path,
                 int durationSeconds,
                 State videoState,
                 long clientId) {
        this.name = name;
        this.link = link;
        this.path = path;
        this.durationSeconds = durationSeconds;
        this.videoState = videoState;
        this.date = getDate();
        this.clientId = clientId;
    }

    public Video(String link,  State videoState, long clientId) {
        this.name = "";
        this.link = link;
        this.path = "";
        this.durationSeconds = 0;
        this.videoState = videoState;
        this.date = getDate();
        this.clientId = clientId;
    }

    public Timestamp getDate() {
        return new java.sql.Timestamp(dateSetter.getTime());
    }

    public String getInsert() {
        return "insert into video (id, name, link, path, duration_seconds, video_state, date, client_id) values(?,?,?,?,?,?,?,?)";
    }

    public String getUpdate() {
        return "update video set id = ?, name = ?, link = ?, path = ?, duration_seconds = ?, video_state = ?, date = ?, client_id = ? where id = " + id;
    }

    public String getDelete() {
        return "delete from video where id = " + id;
    }

    public Object[] getValues() {
        return new Object[] {id, name, link, path, durationSeconds, videoState.name(), date, clientId};
    }

    public static String getById(long id) {
        return "select id, name, link, path, duration_seconds, video_state, date, client_id from video where id = " + id;
    }

    public static Function<ResultSet, Video> getFullMapping() {
        return resulSet -> {
            try {
                return new Video(
                        resulSet.getLong(1),
                        resulSet.getString(2),
                        resulSet.getString(3),
                        resulSet.getString(4),
                        resulSet.getInt(5),
                        State.valueOf(resulSet.getString(6)),
                        resulSet.getTimestamp(7),
                        resulSet.getLong(8)
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
