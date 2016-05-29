package com.antypaymentguard.models;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.io.Serializable;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class Bank extends SugarRecord implements Serializable {
    private String name;
    private String sessionId;
    private String sessionIdSignature;

    public Bank() {
        super();
    }

    public Bank(String name, String sessionId, String sessionIdSignature) {
        super();
        this.name = name;
        this.sessionId = sessionId;
        this.sessionIdSignature = sessionIdSignature;
    }

    public String getName() {
        return name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getSessionIdSignature() {
        return sessionIdSignature;
    }

    public List<BankAccount> getBankAccounts() {
        return Select.from(BankAccount.class).where(Condition.prop("bank").eq(getId())).list();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setSessionIdSignature(String sessionIdSignature) {
        this.sessionIdSignature = sessionIdSignature;
    }
}
