package api.transpool.user.account;

import api.transpool.time.TimeEngineBase;
import api.transpool.time.component.TimeDay;
import api.transpool.user.component.balance.Balance;
import api.transpool.user.component.balance.Transaction;
import api.transpool.user.dto.TransPoolUserAccountDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * The base class of a TransPool user. Contains user data and balance.
 */

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


    //User functions-------------------------------------------------------------------------
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String getUsername() {
        return username;
    }

    //Balance functions-------------------------------------------------------------------------
    @Override
    public double getBalance() {
        return balance;
    }

    /**
     * This function is called only when a user transfers from one to this balance. Adds the amount to the balance
     * and creates the relevant transaction which is added to the transaction history.
     * @param amount - Amount to receive.
     * @param timeReceived - Time of receival.
     */
    @Override
    public void receiveCredit(double amount, TimeDay timeReceived) {
        //Order matters
        this.balance += amount;
        this.transactionHistory.add(new Transaction(timeReceived, Transaction.Type.RECEIVE, amount, balance));
    }

    /**
     * Adds amount into the balance and creates a new transaction for the transaction history.
     * @param amount - Amount to deposit
     */
    @Override
    public void depositCredit(double amount) {
        this.transactionHistory.add(new Transaction(TimeEngineBase.currentTime, Transaction.Type.CREDIT_CHARGE, amount, balance));
        this.balance += amount;
    }

    /**
     * Transfers credit from this balance into the 'other' balance. Creates a transaction of the relevant type.
     * @param amount - Amount to transfer from one balance to the other
     * @param other - The other balance who will get the amount.
     * @param timeTransferred - The time of the transaction.
     */
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

    //-------------------------------------------------------------------------
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
