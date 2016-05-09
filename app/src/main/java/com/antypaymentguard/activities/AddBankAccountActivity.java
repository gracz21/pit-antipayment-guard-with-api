package com.antypaymentguard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.antypaymentguard.R;
import com.antypaymentguard.adapters.BankAccountListViewAdapter;
import com.antypaymentguard.api.Loader;
import com.antypaymentguard.dialogs.ConditionDialog;
import com.antypaymentguard.dialogs.DialogBuilder;
import com.antypaymentguard.models.Bank;
import com.antypaymentguard.models.BankAccount;

import java.util.List;

public class AddBankAccountActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private BankAccountListViewAdapter adapter;
    private Bank bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String bankName = getIntent().getExtras().getString("bankName");
        //Here goes bankAccounts read from XML ;D
        bank = new Select().from(Bank.class).where("Name = ?", bankName).executeSingle();
        List<BankAccount> bankAccounts = Loader.getAccounts();

        adapter = new BankAccountListViewAdapter(this, bankAccounts);
        ListView bankAccountsListView = (ListView) findViewById(R.id.bankAccountsListView);
        bankAccountsListView.setAdapter(adapter);
        bankAccountsListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_bank_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            final Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        adapter.getItem(position).setBank(bank);
        ConditionDialog.show(this, adapter.getItem(position));
    }
}
