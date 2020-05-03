package data.transpool.user;

public class TransPoolDriver extends TransPoolUserAccount {

    private static int IDGenerator = 30000;

    public TransPoolDriver(String username) {
        super(username);
        this.ID = IDGenerator++;
    }
}
