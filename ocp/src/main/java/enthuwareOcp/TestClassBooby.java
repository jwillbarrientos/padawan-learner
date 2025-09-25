package enthuwareOcp;

import java.util.List;

class Booby {}
class Dooby extends Booby {}
class Tooby extends Dooby {}

public class TestClassBooby {
    Booby b = new Booby();
    Tooby t = new Tooby();

    public void addData1(List<? super Dooby> dataList) {
        dataList.add(t);
    }

    public void addData2(List<? extends Dooby> dataList) {
        b = dataList.get(0);
    }
}
