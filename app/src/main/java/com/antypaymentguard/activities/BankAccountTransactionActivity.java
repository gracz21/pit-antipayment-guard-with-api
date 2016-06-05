package com.antypaymentguard.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

        TextView partyTextView = ((TextView) findViewById(R.id.partyTextView));
        String party = transaction.getParty();
        if(party != null && party.length() > 0) {
            partyTextView.setText(party);
        } else {
            partyTextView.setVisibility(View.GONE);
            findViewById(R.id.partyLabelTextView).setVisibility(View.GONE);
            findViewById(R.id.partyIconImageView).setVisibility(View.GONE);
        }

        TextView partyIbanTextView = ((TextView) findViewById(R.id.partyIbanTextView));
        String partyIban = transaction.getPartyIban();
        if(partyIban != null && partyIban.length() > 0) {
           partyIbanTextView.setText(partyIban);
        } else {
            partyIbanTextView.setVisibility(View.GONE);
            findViewById(R.id.partyIbanLabelTextView).setVisibility(View.GONE);
            findViewById(R.id.partyIbanIconImageView).setVisibility(View.GONE);
        }

        TextView kindTextView = ((TextView) findViewById(R.id.kindTextView));
        String kind = transaction.getKind();
        if(kind != null && kind.length() > 0) {
            kindTextView.setText(kind);
        } else {
            kindTextView.setVisibility(View.GONE);
            findViewById(R.id.kindLabelTextView).setVisibility(View.GONE);
            findViewById(R.id.kindIconImageView).setVisibility(View.GONE);
        }
        ((TextView) findViewById(R.id.kindTextView)).setText(transaction.getKind());
    }

}
