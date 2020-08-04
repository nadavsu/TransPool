package api.transpool.user.account;

import api.transpool.user.component.balance.Transaction;

import java.util.List;

public class TransPoolUserAccountDTO {
    private int ID;
    private String username;
    private double balance;
    private List<Transaction> transactionHistory;

    public TransPoolUserAccountDTO(TransPoolUserAccount account) {
        this.ID = account.getID();
        this.transactionHistory = account.getTransactionHistory();
        this.balance = account.getBalance();
        this.username = account.getUsername();
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}
