package com.antypaymentguard.models.conditions;

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
}
