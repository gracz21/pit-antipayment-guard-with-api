package com.antypaymentguard.models;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author Kamil Walkowiak
 */
public class BankAccountTransaction extends SugarRecord implements Serializable {
    private String title;
    private Date date;
    private Double amount;
    private String party;
    private String partyIban;
    private String kind;

    private BankAccount bankAccount;

    public BankAccountTransaction() {
        super();
    }

    public BankAccountTransaction(String title, Date date, Double amount, String party, String partyIban, String kind, BankAccount bankAccount) {
        super();
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.party = party;
        this.partyIban = partyIban;
        this.kind = kind;
        this.bankAccount = bankAccount;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    public String getParty() {
        return party;
    }

    public String getPartyIban() {
        return partyIban;
    }

    public String getKind() {
        return kind;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public String getAmountWithCurrencyName() {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        return df.format(amount) + " " + bankAccount.getCurrencyName();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void setPartyIban(String partyIban) {
        this.partyIban = partyIban;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
