package data.transpool.user.component.transaction;

import java.util.List;

public interface Balance {
    double getBalance();

    void receive(double amount);
    void transfer(double amount, Balance other);
    void deposit(double amount);

    List<Transaction> getTransactionHistory();
    List<Transaction> getLastThreeTransactions();
}
