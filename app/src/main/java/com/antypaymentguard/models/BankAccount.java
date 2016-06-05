package com.antypaymentguard.models;


import com.antypaymentguard.models.conditions.AmountCondition;
import com.antypaymentguard.models.conditions.Condition;
import com.antypaymentguard.models.conditions.NumberCondition;
import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
@Table
public class BankAccount implements Serializable {
    private Long id;
    private String name;
    private String iban;
    private String currencyName;
    @SerializedName("currencyBalance")
    private double balance;
    private String owner;

    private Bank bank;
    private AmountCondition amountCondition;
    private NumberCondition numberCondition;

    @Ignore
    private ArrayList<BankAccountTransaction> currentMonthTransactions;

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public BankAccount() {
        super();
    }

    public BankAccount(String name, String iban, String currencyName, double balance, String owner, Bank bank, AmountCondition condition) {
        super();
        this.name = name;
        this.iban = iban;
        this.currencyName = currencyName;
        this.balance = balance;
        this.owner = owner;
        this.bank = bank;
        this.amountCondition = condition;
        this.numberCondition = null;
    }

    public BankAccount(String name, String iban, String currencyName, double balance, String owner, Bank bank, NumberCondition condition) {
        super();
        this.name = name;
        this.iban = iban;
        this.currencyName = currencyName;
        this.balance = balance;
        this.owner = owner;
        this.bank = bank;
        this.numberCondition = condition;
        this.amountCondition = null;
    }

    public BankAccount(String name, String iban, String currencyName, double balance, String owner, Bank bank) {
        this.name = name;
        this.iban = iban;
        this.currencyName = currencyName;
        this.balance = balance;
        this.owner = owner;
        this.bank = bank;
    }

    public Long getId() {
        return id;
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

    public boolean isConditionFulfilled() {
        if(amountCondition != null) {
            return amountCondition.checkCondition();
        } else {
            return numberCondition.checkCondition();
        }
    }

    public Condition getCondition() {
        if(amountCondition != null) {
            return amountCondition;
        } else {
            return numberCondition;
        }
    }

    public List<BankAccountTransaction> getCurrentMonthTransactions() {
        return currentMonthTransactions;
    }

    public String getBalanceWithCurrencyName() {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        return df.format(balance) + " " + currencyName;
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

    public void setAmountCondition(AmountCondition amountCondition) {
        this.amountCondition = amountCondition;
        this.numberCondition = null;
    }

    public void setNumberCondition(NumberCondition numberCondition) {
        this.numberCondition = numberCondition;
        this.amountCondition = null;
    }

    public void loadTransactions() {
        this.currentMonthTransactions = new ArrayList<>(BankAccountTransaction.find(BankAccountTransaction.class, "bank_account = ?", getId().toString()));
    }

    public void loadCurrentMonthTransactions() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        calendar.set(Calendar.DATE, 1);
        this.currentMonthTransactions = new ArrayList<>(BankAccountTransaction.find(BankAccountTransaction.class, "bank_account = ? and date >= ?",
                getId().toString(), Long.toString(calendar.getTimeInMillis())));
        if(this.amountCondition != null) {
            this.amountCondition.countStatus(this.currentMonthTransactions);
        } else {
            this.numberCondition.countStatus(this.currentMonthTransactions);
        }
    }
}
