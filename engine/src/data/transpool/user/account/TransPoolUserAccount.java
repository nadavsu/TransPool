package data.transpool.user.account;

import data.transpool.time.TimeEngine;
import data.transpool.time.TimeEngineBase;
import data.transpool.user.component.transaction.Balance;
import data.transpool.user.component.transaction.Transaction;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class TransPoolUserAccount implements Balance {

    protected int ID;
    protected String username;
    protected double balance;
    protected List<Transaction> transactionHistory;

    public TransPoolUserAccount(String username) {
        this.transactionHistory = new ArrayList<>();
        this.balance = 0;
        this.username = username;
    }

    public int getID() {
        return ID;
    }

    protected void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void receive(double amount) {
        this.balance += amount;
        this.transactionHistory.add(new Transaction(TimeEngineBase.currentTime, Transaction.Type.RECEIVE, amount));
    }

    @Override
    public void deposit(double amount) {
        this.balance += amount;
        this.transactionHistory.add(new Transaction(TimeEngineBase.currentTime, Transaction.Type.CREDIT_CHARGE, amount));
    }

    @Override
    public void transfer(double amount, Balance other) {
        this.balance -= amount;
        this.transactionHistory.add(new Transaction(TimeEngineBase.currentTime, Transaction.Type.PAY, amount));
        other.receive(amount);
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
}
