package api.transpool.user.dto;

import api.transpool.user.account.TransPoolDriver;
import api.transpool.user.account.TransPoolRider;
import api.transpool.user.account.TransPoolUserAccount;
import api.transpool.user.component.balance.Transaction;
import constants.Constants;

import java.util.List;

public class TransPoolUserAccountDTO {
    protected int ID;
    protected String username;
    protected double balance;
    protected List<Transaction> transactionHistory;

    protected String userType;

    public TransPoolUserAccountDTO(TransPoolUserAccount account) {
        this.ID = account.getID();
        this.transactionHistory = account.getTransactionHistory();
        this.balance = account.getBalance();
        this.username = account.getUsername();
        setUserType(account);
    }

    private void setUserType(TransPoolUserAccount account) {
        if (account instanceof TransPoolDriver) {
            this.userType = Constants.DRIVER;
        } else if (account instanceof TransPoolRider) {
            this.userType = Constants.RIDER;
        } else {
            this.userType = "user";
        }
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
