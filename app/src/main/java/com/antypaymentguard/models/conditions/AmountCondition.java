package com.antypaymentguard.models.conditions;

import java.text.DecimalFormat;

/**
 * @author Kamil Walkowiak
 */
public class AmountCondition extends Condition {
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

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        return df.format(amountOfTransactions);
    }
}
