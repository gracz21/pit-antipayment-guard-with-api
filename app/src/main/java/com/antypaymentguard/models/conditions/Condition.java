package com.antypaymentguard.models.conditions;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * @author Kamil Walkowiak
 */
public abstract class Condition extends SugarRecord implements Serializable {
    public Condition() {
        super();
    }

    public abstract boolean checkCondition();
}
