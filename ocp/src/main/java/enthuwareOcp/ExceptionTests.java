package enthuwareOcp;

import java.io.IOException;
import java.sql.SQLException;

public class ExceptionTests {
    interface I1 {
        void m1() throws IOException;
    }

    interface I2 {
        void m1() throws SQLException, IOException;
    }

    abstract class C2 {
        abstract void m1() throws IOException, SQLException;
    }

    abstract class C1 {
        abstract void m1() throws SQLException;
    }

    class ImplementingInterfaces implements I1, I2 {
        public void m1() throws IOException {}
    }

    class ExtendingClasses extends C2 implements I2 {
        public void m1() throws IOException {}
    }

    interface Measurement{
        public static int getLength(){
            return 0;
        }
        public static int getBreadth(){
            return 0;
        }
    }

    interface Size extends Measurement{
        public static final int UNIT = 100;
        public default int getLength(){
            return 10;
        }
    }
}
