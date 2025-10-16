package io.jona.memestore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.function.Function;

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
//        // public static <T extends Table> List<T> selectSingle(String query, Function<ResultSet, T> rowMapper, String ... queryMappings);
//        List<Video> videos = DbUtil.selectList("select id, titulo, path from video where user_id = ?", resulSet->
//                        new Video(
//                                resulSet.getInt(1),
//                                resulSet.getString(2),
//                                resulSet.getString(3)
//                        ),
//                "1"
//
//        );
//
//
//
//
//
//
//        String url = AppProps.jdbcUrl();//"jdbc:h2:tcp://localhost/C:/Users/barri/IdeaProjects/padawan-learner/meme-store/db/memedb";
        String url = "jdbc:h2:tcp://localhost/C:/Users/barri/IdeaProjects/padawan-learner/meme-store/db/memedb";
        try(Connection conn = DriverManager.getConnection(url, "sa", "sa")) {
            Statement stmt = conn.createStatement();
            dropAndCreateDb(stmt);
            ResultSet rs = stmt.executeQuery("select count(*) from CLIENT");
            String sql = Files.readString(Paths.get("C:/Users/barri/IdeaProjects/padawan-learner/meme-store/db/init.sql"));
            if(rs.next()) {
                if(rs.getInt(1) == 0)
                    for (String query : sql.split(";")) {
                        if (query.trim().contains("insert into client")) {
                            stmt.executeUpdate(query);
                        }
                    }
            }
            rs = stmt.executeQuery("select EMAIL from CLIENT");
            queryClients(rs);
            dropAndCreateDb(stmt);
            rs = stmt.executeQuery("select EMAIL from CLIENT");
            queryClients(rs);
            for(String query : sql.split(";")) {
                if(query.trim().contains("insert into client")) {
                    stmt.executeUpdate(query);
                }
            }
            rs = stmt.executeQuery("select EMAIL from CLIENT");
            queryClients(rs);
        }
    }

    public static void dropAndCreateDb(Statement stmt) throws SQLException {
        System.out.println(stmt.executeUpdate("delete from CLIENT"));
    }

    public static void queryClients(ResultSet rs) throws SQLException {
        while(rs.next())
            System.out.println(rs.getString(1));
    }
}
