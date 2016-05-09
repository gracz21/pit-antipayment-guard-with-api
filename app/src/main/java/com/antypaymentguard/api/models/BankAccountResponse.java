package com.antypaymentguard.api.models;

import com.antypaymentguard.models.BankAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Kozłowski on 09.05.16.
 */
public class BankAccountResponse {
    public List<BankAccount> getAccounts() {
        return reply.command.result.accounts.account;
    }

    public Reply reply;

    public class Accounts {
        public List<BankAccount> account = new ArrayList<>();
    }

    private class Command {
        public String Id;
        public String State;
        public String Name;
        public Progress progress;
        public Result result;
    }

    private class Progress {
        public String value;
    }

    private class Reply {
        public String Status;
        public Command command;
    }

    private class Result {
        public Accounts accounts;
    }
}
