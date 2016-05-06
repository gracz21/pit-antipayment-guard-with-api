package com.antypaymentguard.models.conditions;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author Kamil Walkowiak
 */
@Table(name = "NumberCondition")
public class NumberCondition extends Condition {
    @Column(name = "NumberOfTransactions")
    private int numberOfTransactions;

    public NumberCondition() {
        super();
    }

    public NumberCondition(int numberOfTransactions) {
        super();
        this.numberOfTransactions = numberOfTransactions;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    @Override
    public boolean checkCondition() {
        return false;
    }
}
