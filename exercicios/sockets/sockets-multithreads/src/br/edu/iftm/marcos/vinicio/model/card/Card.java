package br.edu.iftm.marcos.vinicio.model.card;

import br.edu.iftm.marcos.vinicio.model.transaction.Transaction;

import java.util.Collections;
import java.util.HashSet;

public class Card {
    private final int number;
    private final String clientName;
    private final Set<Transaction> transactions = new HashSet<>();
    private double balance;

    public Card(int number, String clientName, double balance) {
        this.number = number;
        this.clientName = clientName;
        this.balance = balance;
    }

    public void transact(Transaction transaction) {
        if (transaction.getValue() > balance) transaction.setResponseCode(51);
        this.balance -= transaction.getValue();
        this.transactions.add(transaction);
    }

    public int getNumber() {
        return number;
    }

    public String getClientName() {
        return clientName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<Transaction> getTransactions() {
        return Collections.unmodifiableSet(transactions);
    }
}
