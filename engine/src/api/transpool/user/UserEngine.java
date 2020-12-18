package api.transpool.user;

import api.transpool.user.account.TransPoolUserAccount;

import java.util.Map;
import java.util.Set;

public interface UserEngine {
    void addUser(TransPoolUserAccount username);
    void removeUser(TransPoolUserAccount username);
    Map<String, TransPoolUserAccount> getUsers();
    boolean isUserExists(String username);
    TransPoolUserAccount getUserAccount(String username);
}
