package data.transpool.user;

import data.transpool.user.account.TransPoolUserAccount;

import java.util.Set;

public interface UserEngine {
    void addUser(TransPoolUserAccount username);
    void removeUser(TransPoolUserAccount username);
    Set<TransPoolUserAccount> getUsers();
    boolean isUserExists(String username);
    TransPoolUserAccount getUserAccount(String username);
}
