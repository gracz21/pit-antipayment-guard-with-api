package com.antypaymentguard.models.conditions;

import com.activeandroid.Model;

/**
 * @author Kamil Walkowiak
 */
public abstract class Condition extends Model {
    public Condition() {
        super();
    }

    public abstract boolean checkCondition();
}
