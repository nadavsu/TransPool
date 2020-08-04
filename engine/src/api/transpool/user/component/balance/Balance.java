package api.transpool.user.component.balance;

import api.transpool.time.component.TimeDay;

import java.util.Collection;

public interface Balance {
    double getBalance();

    void receiveCredit(double amount, TimeDay timeReceived);
    void transferCredit(double amount, Balance other, TimeDay timeTransferred);
    void depositCredit(double amount);

    Collection<Transaction> getTransactionHistory();
    Collection<Transaction> getLastThreeTransactions();
}
