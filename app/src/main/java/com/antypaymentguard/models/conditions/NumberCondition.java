package com.antypaymentguard.models.conditions;

import com.antypaymentguard.models.BankAccountTransaction;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class NumberCondition extends SugarRecord implements Condition {
    private int numberOfTransactions;
    @Ignore
    private int conditionStatus;

    public NumberCondition() {

    }

    public NumberCondition(int numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
        this.conditionStatus = 0;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    @Override
    public void countStatus(List<BankAccountTransaction> transactions) {
        for(BankAccountTransaction transaction: transactions) {
            if(transaction.getAmount() < 0) {
                conditionStatus++;
            }
        }
    }

    @Override
    public String getStatusString() {
        return Integer.toString(conditionStatus) + "/" + Integer.toString(numberOfTransactions);
    }

    @Override
    public boolean checkCondition() {
        return conditionStatus >= numberOfTransactions;
    }
}
