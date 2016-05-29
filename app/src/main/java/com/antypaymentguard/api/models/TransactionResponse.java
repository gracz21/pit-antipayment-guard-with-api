package com.antypaymentguard.api.models;

import com.antypaymentguard.models.BankAccountTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Kozłowski on 29.05.16.
 */
public class TransactionResponse {
    public List<BankAccountTransaction> getTransactions() {
        return reply.command.result.moneyTransactions.moneyTransaction;
    }

    public Reply reply;

    public class Command {
        public String id;
        public String state;
        public String name;
        public Progress progress;
        public Result result;
    }

    public class MoneyTransactions {
        public List<BankAccountTransaction> moneyTransaction = new ArrayList<>();
    }

    public class Progress {
        public String value;
    }

    public class Reply {
        public String status;
        public Command command;
    }

    public class Result {
        public MoneyTransactions moneyTransactions;
    }
}
