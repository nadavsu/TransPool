package data.transpool.user;

public abstract class TransPoolUserAccount {
    protected int ID;
    protected String username;

    public TransPoolUserAccount(String username) {
        this.username = username;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return username;
    }
}
