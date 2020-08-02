package data.transpool.user.component.balance;

import data.transpool.time.component.TimeDay;
//add
public class Transaction {

    private TimeDay date;
    private Type type;
    private double transactionAmount;
    private double amountBefore;
    private double amountAfter;

    public Transaction(TimeDay date, Type type, double transactionAmount, double currentBalance) {
        this.date = date;
        this.type = type;
        this.transactionAmount = transactionAmount;

        this.amountBefore = currentBalance;
        this.amountAfter = currentBalance + transactionAmount;
    }

    public TimeDay getDate() {
        return date;
    }

    public Type getType() {
        return type;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public double getAmountBefore() {
        return amountBefore;
    }

    public double getAmountAfter() {
        return amountAfter;
    }

    public enum Type {
        CREDIT_CHARGE, PAY, RECEIVE
    }

}
