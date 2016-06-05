package com.antypaymentguard.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.antypaymentguard.R;
import com.antypaymentguard.models.BankAccountTransaction;

import java.text.DateFormat;

public class BankAccountTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BankAccountTransaction transaction = (BankAccountTransaction) getIntent().getSerializableExtra("transaction");
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

        ((TextView) findViewById(R.id.nameTextView)).setText(transaction.getTitle());
        ((TextView) findViewById(R.id.bankAccountTextView)).setText(transaction.getBankAccount().getName());
        ((TextView) findViewById(R.id.amountTextView)).setText(transaction.getAmountWithCurrencyName());
        ((TextView) findViewById(R.id.dateTextView)).setText(dateFormat.format(transaction.getDate()));
        ((TextView) findViewById(R.id.partyTextView)).setText(transaction.getParty());
        ((TextView) findViewById(R.id.partyIbanTextView)).setText(transaction.getPartyIban());
        ((TextView) findViewById(R.id.kindTextView)).setText(transaction.getKind());
    }

}
