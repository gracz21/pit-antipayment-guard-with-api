package com.antypaymentguard.models.conditions;

import com.antypaymentguard.models.BankAccountTransaction;

import java.io.Serializable;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public interface Condition extends Serializable {
    public void countStatus(List<BankAccountTransaction> transactions);
    public String getStatusString();
    public boolean checkCondition();
}
