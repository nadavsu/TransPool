package api.transpool.user.account;

import api.transpool.time.TimeEngineBase;
import api.transpool.time.component.TimeDay;
import api.transpool.user.component.balance.Balance;
import api.transpool.user.component.balance.Transaction;
import api.transpool.user.dto.TransPoolUserAccountDTO;

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
    public void receiveCredit(double amount, TimeDay timeReceived) {
        //Order matters
        this.balance += amount;
        this.transactionHistory.add(new Transaction(timeReceived, Transaction.Type.RECEIVE, amount, balance));
    }

    @Override
    public void depositCredit(double amount) {
        this.transactionHistory.add(new Transaction(TimeEngineBase.currentTime, Transaction.Type.CREDIT_CHARGE, amount, balance));
        this.balance += amount;
    }

    @Override
    public void transferCredit(double amount, Balance other, TimeDay timeTransferred) {
        this.transactionHistory.add(new Transaction(timeTransferred, Transaction.Type.PAY, amount, balance));
        this.balance -= amount;
        other.receiveCredit(amount, timeTransferred);
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

    public TransPoolUserAccountDTO getDetails() {
        return new TransPoolUserAccountDTO(this);
    }

    protected void setID(int ID) {
        this.ID = ID;
    }
}
