package com.antypaymentguard.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
@Table(name = "Banks")
public class Bank extends Model implements Serializable {
    @Column(name = "Name")
    private String name;
    @Column(name = "SessionId")
    private String sessionId;
    @Column(name = "SessionIdSignature")
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
        return getMany(BankAccount.class, "Bank");
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
