package com.antypaymentguard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.antypaymentguard.R;
import com.antypaymentguard.adapters.BankAccountListViewAdapter;
import com.antypaymentguard.api.Loader;
import com.antypaymentguard.models.Bank;
import com.antypaymentguard.models.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class AddBankAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String bankName = getIntent().getExtras().getString("bankName");
        //Here goes bankAccounts read from XML ;D
        Bank bank = new Select().from(Bank.class).where("Name = ?", bankName).executeSingle();
        List<BankAccount> bankAccounts = Loader.getAccounts();

        BankAccountListViewAdapter bankAccountListViewAdapter = new BankAccountListViewAdapter(this, bankAccounts);
        ListView bankAccountsListView = (ListView) findViewById(R.id.bankAccountsListView);
        bankAccountsListView.setAdapter(bankAccountListViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_bank_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_done) {
            startActivity(new Intent(AddBankAccountActivity.this, MainActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
