package api.transpool.user;

import api.transpool.user.account.TransPoolUserAccount;

import java.util.*;

public class UserEngineBase implements UserEngine {

    private Map<String, TransPoolUserAccount> userAccounts;

    public UserEngineBase() {
        userAccounts = new HashMap<>();
    }

    @Override
    public synchronized void addUser(TransPoolUserAccount user) {
        userAccounts.put(user.getUsername(), user);
    }

    @Override
    public synchronized void removeUser(TransPoolUserAccount user) {
        userAccounts.remove(user.getUsername());
    }

    @Override
    public synchronized Map<String, TransPoolUserAccount> getUsers() {
        return Collections.unmodifiableMap(userAccounts);
    }

    @Override
    public boolean isUserExists(String username) {
        return userAccounts.containsKey(username);
    }

    @Override
    public TransPoolUserAccount getUserAccount(String username) {
        return userAccounts.get(username);
    }
}
