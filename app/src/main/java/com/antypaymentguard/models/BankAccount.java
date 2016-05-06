package com.antypaymentguard.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.antypaymentguard.models.conditions.Condition;

import java.util.List;

/**
 * @author Kamil Walkowiak
 */
@Table(name = "BankAccounts")
public class BankAccount extends Model {
    @Column(name = "Name")
    private String name;
    @Column(name = "Iban")
    private String iban;
    @Column(name = "CurrencyName")
    private String currencyName;
    @Column(name = "Balance")
    private double balance;
    @Column(name = "Owner")
    private String owner;
    @Column(name = "Bank", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private Bank bank;
    @Column(name = "Condition", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private Condition condition;

    public BankAccount() {
        super();
    }

    public BankAccount(String name, String iban, String currencyName, double balance, String owner, Bank bank, Condition condition) {
        super();
        this.name = name;
        this.iban = iban;
        this.currencyName = currencyName;
        this.balance = balance;
        this.owner = owner;
        this.bank = bank;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public String getIban() {
        return iban;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    public Bank getBank() {
        return bank;
    }

    public Condition getCondition() {
        return condition;
    }

    public List<Transaction> getTransactions() {
        return getMany(Transaction.class, "BankAccount");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
