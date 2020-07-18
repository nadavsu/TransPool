package data.transpool.user;

import data.transpool.user.account.TransPoolUserAccount;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserEngineBase implements UserEngine {

    private Set<TransPoolUserAccount> userAccounts;

    public UserEngineBase() {
        userAccounts = new HashSet<>();
    }

    @Override
    public synchronized void addUser(TransPoolUserAccount user) {
        userAccounts.add(user);
    }

    @Override
    public synchronized void removeUser(TransPoolUserAccount user) {
        userAccounts.remove(user);
    }

    @Override
    public synchronized Set<TransPoolUserAccount> getUsers() {
        return Collections.unmodifiableSet(userAccounts);
    }

    @Override
    public boolean isUserExists(String username) {
        TransPoolUserAccount userAccount = getUserAccount(username);
        return userAccount != null && userAccounts.contains(userAccount);
    }

    @Override
    public TransPoolUserAccount getUserAccount(String username) {
        return userAccounts
                .stream()
                .filter(userAccount -> userAccount.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
