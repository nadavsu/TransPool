package data.transpool.user.account;

import data.transpool.time.TimeEngineBase;
import data.transpool.user.component.balance.Balance;
import data.transpool.user.component.balance.Transaction;

import java.util.ArrayList;
import java.util.List;

public abstract class TransPoolUserAccount implements User, Balance {

    protected int ID;
    protected String username;
    protected double balance;
    protected List<Transaction> transactionHistory;

    public TransPoolUserAccount(String username) {
        this.transactionHistory = new ArrayList<>();
        this.balance = 0;
        this.username = username;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void receiveCredit(double amount) {
        this.balance += amount;
        this.transactionHistory.add(new Transaction(TimeEngineBase.currentTime, Transaction.Type.RECEIVE, amount));
    }

    @Override
    public void depositCredit(double amount) {
        this.balance += amount;
        this.transactionHistory.add(new Transaction(TimeEngineBase.currentTime, Transaction.Type.CREDIT_CHARGE, amount));
    }

    @Override
    public void transferCredit(double amount, Balance other) {
        this.balance -= amount;
        this.transactionHistory.add(new Transaction(TimeEngineBase.currentTime, Transaction.Type.PAY, amount));
        other.receiveCredit(amount);
    }

    @Override
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    @Override
    public List<Transaction> getLastThreeTransactions() {
        return transactionHistory.subList(Math.max(transactionHistory.size() - 3, 0), transactionHistory.size());
    }

    @Override
    public String toString() {
        return username;
    }

    protected void setID(int ID) {
        this.ID = ID;
    }
}
