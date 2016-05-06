package com.antypaymentguard.models;

import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class Bank {
    private long id;
    private String name;
    private String sessionId;
    private String sessionIdSignature;
    private List<BankAccount> bankAccounts;

    public Bank(String name, String sessionId, String sessionIdSignature) {
        this.name = name;
        this.sessionId = sessionId;
        this.sessionIdSignature = sessionIdSignature;
    }

    public long getId() {
        return id;
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
        return bankAccounts;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
}
