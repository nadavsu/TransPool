package data.transpool.user;

public class TransPoolDriver extends TransPoolUserAccount {

    private static int IDGenerator = 30000;

    public TransPoolDriver(String username) {
        super(username);
        this.setID(IDGenerator);
        IDGenerator++;
    }

    public TransPoolDriver(TransPoolDriver other) {
        super(other.username.get());
        this.setID(other.getID());
    }
}
