package com.antypaymentguard.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.antypaymentguard.R;
import com.antypaymentguard.databaseHelper.BankAccountDatabaseHelper;
import com.antypaymentguard.databaseHelper.BankDatabaseHelper;
import com.antypaymentguard.databaseHelper.ConditionDatabaseHelper;
import com.antypaymentguard.databaseHelper.DatabaseHelper;
import com.antypaymentguard.model.Bank;
import com.antypaymentguard.model.BankAccount;
import com.antypaymentguard.model.condition.Condition;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private void setupMock() {
        BankDatabaseHelper bankDatabaseHelper = new BankDatabaseHelper(getApplicationContext());
        BankAccountDatabaseHelper bankAccountDatabaseHelper = new BankAccountDatabaseHelper(getApplicationContext());
        ConditionDatabaseHelper conditionDatabaseHelper = new ConditionDatabaseHelper(getApplicationContext());

        Bank bank1 = new Bank("Test1", "test", "test");
        bank1.setId(1);
        Bank bank2 = new Bank("Test2", "test", "test");
        bank2.setId(2);

        bankDatabaseHelper.createBank(bank1);
        bankDatabaseHelper.createBank(bank2);

        Condition condition = conditionDatabaseHelper.getOrCreateNumberCondition(10);

        bankAccountDatabaseHelper.createBankAccount(new BankAccount("TestA1", "1", "PLN", 20.0, "Test", bank1, condition));
        bankAccountDatabaseHelper.createBankAccount(new BankAccount("TestA2", "2", "PLN", 20.0, "Test", bank2, condition));
        bankAccountDatabaseHelper.createBankAccount(new BankAccount("TestA3", "3", "PLN", 20.0, "Test", bank1, condition));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setupMock();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignInToBank.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        (new FetchBanksTask()).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FetchBanksTask extends AsyncTask<Void, Void, List<Bank>> {
        @Override
        protected List<Bank> doInBackground(Void... params) {
            BankDatabaseHelper bankDatabaseHelper = new BankDatabaseHelper(getApplicationContext());
            BankAccountDatabaseHelper bankAccountDatabaseHelper = new BankAccountDatabaseHelper(getApplicationContext());
            List<Bank> banks = bankDatabaseHelper.getAllBanks();

            for(Bank bank: banks) {
                bank.setBankAccounts(bankAccountDatabaseHelper.getBankAccountsByBank(bank));
            }
            return banks;
        }

        @Override
        protected void onPostExecute(List<Bank> banks) {
            for(Bank bank: banks) {
                Log.d("Bank: ", bank.getName() + ", " + bank.getSessionId() + ", " + bank.getSessionIdSignature());
                for(BankAccount bankAccount: bank.getBankAccounts()) {
                    Log.d("Bank Account: ", bankAccount.getName() + ", " + bankAccount.getIban() + ", " + bankAccount.getCurrencyName());
                    Log.d("Condition: ", bankAccount.getCondition().getClass().getSimpleName());
                }
            }
        }
    }
}
