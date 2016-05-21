package com.antypaymentguard.models.conditions;

import com.activeandroid.Model;

import java.io.Serializable;

/**
 * @author Kamil Walkowiak
 */
public abstract class Condition extends Model implements Serializable {
    public Condition() {
        super();
    }

    public abstract boolean checkCondition();
}
