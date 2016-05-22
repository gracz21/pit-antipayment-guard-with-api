package com.antypaymentguard.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author Kamil Walkowiak
 */
@Table(name = "Transactions")
public class Transaction extends Model implements Serializable {
    @Column(name = "Title")
    private String title;
    @Column(name = "Date")
    private Date date;
    @Column(name = "Amount")
    private Double amount;
    @Column(name = "Party")
    private String party;
    @Column(name = "PartyIban")
    private String partyIban;
    @Column(name = "Kind")
    private String kind;

    @Column(name = "BankAccount", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private BankAccount bankAccount;

    public Transaction() {
        super();
    }

    public Transaction(String title, Date date, Double amount, String party, String partyIban, String kind, BankAccount bankAccount) {
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
