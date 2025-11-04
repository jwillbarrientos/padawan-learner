package io.jona.memestore.dto;

import io.jona.framework.JonaDb;
import io.jona.framework.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j
@NoArgsConstructor
public class Video extends Table {

    public enum State {
        SUBMITTED, DOWNLOADED, ERROR_DOWNLOADING
    }

    public static final String FULL_COLUMNS = "id, name, link, path, duration_seconds, file_size, video_state, date, client_id ";

    @Setter @Getter
    private long id;
    @Setter
    private String name;
    @Setter @Getter
    private String link;
    @Setter @Getter
    private String path;
    @Setter
    private int durationSeconds;
    @Setter @Getter
    private int fileSize;
    @Setter
    private State videoState;
    @Setter
    private Date date;
    @Setter
    private long clientId;

    public Video(String link,  State videoState, long clientId) {
        this(0, "", link, "", 0,0, videoState, new Date(), clientId);
    }

    public Video(long id,
                 String name,
                 String link,
                 String path,
                 int durationSeconds,
                 int fileSize,
                 State videoState,
                 Date date,
                 long clientId) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.path = path;
        this.durationSeconds = durationSeconds;
        this.fileSize = fileSize;
        this.videoState = videoState;
        this.date = date;
        this.clientId = clientId;
    }

    public static ThrowingFunction<ResultSet, Video, SQLException> getFullMapping() {
        return rs -> new Video(
                rs.getLong(1), // id
                rs.getString(2), // name
                rs.getString(3), // link
                rs.getString(4), // path
                rs.getInt(5), // durationSeconds
                rs.getInt(6), // fileSize
                State.valueOf(rs.getString(7)), // videoState
                rs.getTimestamp(8), // date
                rs.getLong( 9) // clientId
        );
    }

    public String getInsert() {
        setId(nextId());
        setDate(new Date());
        return "insert into video (" + FULL_COLUMNS + ") values(?,?,?,?,?,?,?,?,?)";
    }

    public String getUpdate() {
        return "update video set id = ?, name = ?, link = ?, path = ?, duration_seconds = ?, file_size = ?, video_state = ?, date = ?, client_id = ? where id = " + id;
    }

    public String getDelete() {
        return "delete from video where id = " + id;
    }

    public Object[] getValues() {
        return new Object[] {id, name, link, path, durationSeconds, fileSize, videoState.name(), new Timestamp(date.getTime()), clientId};
    }

    public static Video nextVideoToDownload() {
        return JonaDb.selectSingle(
                "select " + FULL_COLUMNS + " from video where video_state = ?",
                getFullMapping(),
                State.SUBMITTED.name()
        );
    }

    public static List<Video> videosToDownload(long clientId) {
        return JonaDb.selectList(
                "select " + FULL_COLUMNS + "from video where client_id = ? and video_state = 'DOWNLOADED' order by date desc limit " + 50,
                Video.getFullMapping(),
                clientId
        );
    }

    public static Video findById(long id) {
        return JonaDb.selectSingle("select " + FULL_COLUMNS + " from video where id = " + id, Video.getFullMapping());
    }

    @Override
    public String toString() {
        return "Video id = " + id +
                ", name = " + name +
                ", link = " + link +
                ", path = " + path +
                ", duration_seconds = " + durationSeconds +
                ", file_size = " + fileSize +
                ", video_state = " + videoState +
                ", date = " + date +
                ", client_id + " + clientId;
    }
}
