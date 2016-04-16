package com.antypaymentguard.model;

import com.antypaymentguard.model.condition.Condition;

/**
 * @author Kamil Walkowiak
 */
public class BankAccount {
    private String name;
    private String iban;
    private String currencyName;
    private double balance;
    private String owner;
    private Bank bank;
    private Condition condition;

    public BankAccount(String name, String iban, String currencyName, double balance, String owner, Condition condition) {
        this.name = name;
        this.iban = iban;
        this.currencyName = currencyName;
        this.balance = balance;
        this.owner = owner;
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

    public Condition getCondition() {
        return condition;
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
