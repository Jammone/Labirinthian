package Tasks;

import it.labirinthian.labirinthian.Labirinthian;

public class OpenLabTask implements Runnable{
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        Labirinthian.getPrizeHandler().open();
    }
}
