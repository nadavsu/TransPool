package data.transpool.user.component.balance;

import data.transpool.time.component.TimeDay;

public class Transaction {

    public enum Type {
        CREDIT_CHARGE,
        PAY,
        RECEIVE
    }

    private TimeDay date;
    private Type type;
    private double amount;

    public Transaction(TimeDay date, Type type, double sum) {
        this.date = date;
        this.type = type;
        this.amount = sum;
    }

    public TimeDay getDate() {
        return date;
    }

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

}
