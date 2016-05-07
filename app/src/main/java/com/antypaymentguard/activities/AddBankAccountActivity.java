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
        List<BankAccount> bankAccounts = generateMocks(bank);

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

    private List<BankAccount> generateMocks(Bank bank) {
        List<BankAccount> result = new ArrayList<>();
        result.add(new BankAccount("Konto 1", "PL05114020170000400213015148", "PLN", 1200.84, "Jan Kowalski", bank));
        result.add(new BankAccount("Konto 2", "PL25103019447356101620901000", "PLN", -200.0, "Jan Kowalski", bank));
        result.add(new BankAccount("Konto 3", "PL03800400022007001839630001", "USD", 0.0, "Jan Kowalski", bank));

        return result;
    }
}
