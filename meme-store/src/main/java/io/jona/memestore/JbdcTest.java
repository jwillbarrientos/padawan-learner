package io.jona.memestore;

import io.jona.framework.JonaDb;
import io.jona.memestore.dto.Client;
import io.jona.memestore.dto.Video;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

@Slf4j
public class JbdcTest {
    public static void main(String[] args) throws SQLException, IOException {
//        class Table {
//            abstract string getdelete();
//            abstract string getInsert();
//            abstract Object[] getValues();
//            abstract <T extends Table> Function<ResultSet, T> getFullMapping();
//        }
//        class Cliente extends Table {
//            int id;
//            string email;
//            string password;
//
//            public getDelete() {
//                return "delete from cliente where id = "+ id;
//            }
//            public string getInsert() {
//                return "insert into cliente (id, email, password) values(?,?,?");
//            }
//            public Object[] getValues() {
//                return new Object[] {id, email, password};
//            }
//            public static string getById(int id) {
//                return "select id, email, password from cliente where id = " + id;
//            }
//            public static Function<ResultSet, Cliente> getFullMapping() {
//                return resulSet-> new Client(
//                        resulSet.getInt(1),
//                        resulSet.getString(2),
//                        resulSet.getString(3)
//                );
//            }
//        }
//        Cliente c = new Cliente(
//                asdf,
//                asdfasdf,
//                asdf
//        )
//        boolean success = DbUtil.insert(c); // c.getInsert(), c.getValues()
//        c = DbUtil.selectSingle(Cliente.getById(1), Cliente.getFullMapping());
//        boolean success = DbUtil.delete(c); // c.getgDelete()
//
//        // public static <T extends Table> T selectSingle(String query, Function<ResultSet, T> rowMapper, String ... queryMappings);
//        Client clientes = DbUtil.selectSingle("select id, email, password from client where email = ?", Cliente.getFullMapping(), "jona@gmail.com");
//
//        // public static <T extends Table> List<T> selectList(String query, Function<ResultSet, T> rowMapper, String ... queryMappings);
//        List<Video> videos = DbUtil.selectList("select id, titulo, path from video where user_id = ?", resulSet->
//                        new Video(
//                                resulSet.getInt(1),
//                                resulSet.getString(2),
//                                resulSet.getString(3)
//                        ),
//                "1"
//
//        );
        Client client = new Client(1, "barrientosjonah@gmail.com", "1234");
        JonaDb.init(AppProps.getJdbcUrl(), AppProps.getDbUser(), AppProps.getDbPassword());
        boolean successInsert = JonaDb.insert(client);
        System.out.println(successInsert);
        Client.getFullMapping();
        Client client1 = JonaDb.selectSingle("select  id, email, password from client where email = ?",
                Client.getFullMapping(),
                "barrientosjonah@gmail.com");
        System.out.println(client1);
        Video video = new Video(1,
                "tremendoProgramador",
                "https://www.tiktok.com/@gertu0k/video/7258798530066713862",
                "C:/Users/barri/IdeaProjects/padawan-learner/meme-store/downloads/EL REY DE LOS CURRÍCULUMS Gracias @desayunosinformales12 por invitarm....mp4",
                18,
                "downloaded",
                1);
        boolean successInsertVideo = JonaDb.insert(video);
        System.out.println(successInsertVideo);
        List<Video> videos = JonaDb.selectList("select id, name, link, path, duration_seconds, video_state, client_id from video where client_id = ?",
                Video.getFullMapping(), "1");
        System.out.println(videos);
        System.out.println(successInsert);
        boolean successDelete = JonaDb.delete(client);
        System.out.println(successDelete);
    }
}
