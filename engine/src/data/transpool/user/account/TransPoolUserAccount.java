package data.transpool.user.account;

import data.transpool.user.component.transaction.Transaction;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class TransPoolUserAccount {

    protected IntegerProperty ID;
    protected StringProperty username;
    protected List<Transaction> transactionHistory;
    protected double balance;

    public TransPoolUserAccount(String username) {
        this.transactionHistory = new ArrayList<>();
        this.balance = 0;
        this.username = new SimpleStringProperty(username);
        this.ID = new SimpleIntegerProperty(-1);
    }

    public int getID() {
        return ID.get();
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    @Override
    public String toString() {
        return username.get();
    }
}
