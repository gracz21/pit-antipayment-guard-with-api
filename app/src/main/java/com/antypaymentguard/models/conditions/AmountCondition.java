package com.antypaymentguard.models.conditions;

import com.antypaymentguard.models.BankAccountTransaction;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class AmountCondition extends SugarRecord implements Condition {
    private double amountOfTransactions;
    @Ignore
    private double conditionStatus;

    public AmountCondition() {

    }

    public AmountCondition(double amountOfTransactions) {
        this.amountOfTransactions = amountOfTransactions;
        this.conditionStatus = 0.0;
    }

    @Override
    public void countStatus(List<BankAccountTransaction> transactions) {
        for(BankAccountTransaction transaction: transactions) {
            if(transaction.getAmount() < 0) {
                conditionStatus -= transaction.getAmount();
            }
        }
    }

    @Override
    public String getStatusString() {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        return df.format(conditionStatus) + "/" + df.format(amountOfTransactions);
    }

    @Override
    public boolean checkCondition() {
        return conditionStatus >= amountOfTransactions;
    }
}
