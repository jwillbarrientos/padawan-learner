package io.jona.memestore;

import io.jona.framework.JonaDb;
import io.jona.memestore.dto.Client;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

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
//        c = DbUtil.selectSingle(Cliente.byId(1), Cliente.getFullMapping());
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
        System.out.println(AppProps.getJdbcUrl());
        boolean successInsert = JonaDb.insert(client);
        System.out.println(successInsert);
    }
}
