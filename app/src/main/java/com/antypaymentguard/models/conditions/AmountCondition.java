package com.antypaymentguard.models.conditions;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author Kamil Walkowiak
 */
@Table(name = "AmountCondition")
public class AmountCondition extends Condition {
    @Column(name = "AmountOfTransactions")
    private double amountOfTransactions;

    public AmountCondition() {
        super();
    }

    public AmountCondition(double amountOfTransactions) {
        super();
        this.amountOfTransactions = amountOfTransactions;
    }

    @Override
    public boolean checkCondition() {
        return false;
    }
}
