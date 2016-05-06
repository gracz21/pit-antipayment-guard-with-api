package com.antypaymentguard.models;

import com.antypaymentguard.models.conditions.Condition;

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


    public BankAccount(String name, String iban, double balance) {
        this.name = name;
        this.iban = iban;
        this.balance = balance;
    }

    public BankAccount(String name, String iban, String currencyName, double balance, String owner, Bank bank, Condition condition) {
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
