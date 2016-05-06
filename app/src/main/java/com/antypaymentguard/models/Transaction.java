package com.antypaymentguard.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * @author Kamil Walkowiak
 */
@Table(name = "Transactions")
public class Transaction extends Model {
    @Column(name = "Date")
    private Date date;
    @Column(name = "Amount")
    private Double amount;
    @Column(name = "Description")
    private String description;
    @Column(name = "BankAccount", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private BankAccount bankAccount;

    public Transaction() {
        super();
    }

    public Transaction(Date date, Double amount, String place, String description) {
        super();
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }


    public String getDescription() {
        return description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
