package com.antypaymentguard.models.conditions;

/**
 * @author Kamil Walkowiak
 */
public abstract class Condition {
    protected long id;

    protected Condition(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public boolean checkCondition(){
        return false;
    }
}
