package data.transpool.user.component.balance;

import java.util.Collection;

public interface Balance {
    double getBalance();

    void receiveCredit(double amount);
    void transferCredit(double amount, Balance other);
    void depositCredit(double amount);

    Collection<Transaction> getTransactionHistory();
    Collection<Transaction> getLastThreeTransactions();
}
