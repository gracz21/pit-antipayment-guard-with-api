package com.antypaymentguard.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.antypaymentguard.R;
import com.antypaymentguard.adapters.BankAccountAdapter;
import com.antypaymentguard.databaseHelpers.BankAccountDatabaseHelper;
import com.antypaymentguard.databaseHelpers.BankDatabaseHelper;
import com.antypaymentguard.databaseHelpers.ConditionDatabaseHelper;
import com.antypaymentguard.models.Bank;
import com.antypaymentguard.models.BankAccount;
import com.antypaymentguard.models.conditions.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ExpandableListView.OnChildClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setupMock();

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        assert floatingActionButton != null;
        floatingActionButton.setOnClickListener(this);

        prepareListData();
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        BankAccountAdapter adapter = new BankAccountAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchBanksTask().execute();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, SignInToBank.class));
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        Snackbar.make(view, "Great! You selected bank account ;D", Snackbar.LENGTH_SHORT).show();
        return false;
    }

    private class FetchBanksTask extends AsyncTask<Void, Void, List<Bank>> {
        @Override
        protected List<Bank> doInBackground(Void... params) {
            BankDatabaseHelper bankDatabaseHelper = new BankDatabaseHelper(getApplicationContext());
            BankAccountDatabaseHelper bankAccountDatabaseHelper = new BankAccountDatabaseHelper(getApplicationContext());
            List<Bank> banks = bankDatabaseHelper.getAllBanks();

            for (Bank bank : banks) {
                bank.setBankAccounts(bankAccountDatabaseHelper.getBankAccountsByBank(bank));
            }
            return banks;
        }

        @Override
        protected void onPostExecute(List<Bank> banks) {
            for (Bank bank : banks) {
                Log.d("Bank: ", bank.getName() + ", " + bank.getSessionId() + ", " + bank.getSessionIdSignature());
                for (BankAccount bankAccount : bank.getBankAccounts()) {
                    Log.d("Bank Account: ", bankAccount.getName() + ", " + bankAccount.getIban() + ", " + bankAccount.getCurrencyName());
                    Log.d("Condition: ", bankAccount.getCondition().getClass().getSimpleName());
                }
            }
        }
    }

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

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeader.add("Bank 1");
        listDataHeader.add("Bank 2");
        listDataHeader.add("Bank 3");

        List<BankAccount> top250 = new ArrayList<>();
        top250.add(new BankAccount("Konto 1", "1234 1234 1234 1234", 123));
        top250.add(new BankAccount("Konto 2", "1234 1234 1234 1234", 56154));


        List<BankAccount> nowShowing = new ArrayList<>();
        nowShowing.add(new BankAccount("Konto 1", "1234 1234 1234 1234", 165));
        nowShowing.add(new BankAccount("Konto 2", "1234 1234 1234 1234", 45564));
        nowShowing.add(new BankAccount("Konto 3", "1234 1234 1234 1234", 4354));

        listDataChild.put(listDataHeader.get(0), top250);
        listDataChild.put(listDataHeader.get(1), nowShowing);
    }

    private List<String> listDataHeader = new ArrayList<>();
    private HashMap<String, List<BankAccount>> listDataChild = new HashMap<>();
}
