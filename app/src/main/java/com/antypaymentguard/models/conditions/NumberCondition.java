package com.antypaymentguard.models.conditions;

/**
 * @author Kamil Walkowiak
 */
public class NumberCondition extends Condition {
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

    @Override
    public String toString() {
        return Integer.toString(numberOfTransactions);
    }
}
