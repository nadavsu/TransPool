package data.transpool.user.component.transaction;

import data.transpool.time.component.TimeDay;

public class Transaction {
    private TimeDay date;
    private TransactionType transactionType;
    private double sum;

    public Transaction(TimeDay date, TransactionType transactionType, double sum) {
        this.date = date;
        this.transactionType = transactionType;
        this.sum = sum;
    }

    public TimeDay getDate() {
        return date;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getSum() {
        return sum;
    }
}
