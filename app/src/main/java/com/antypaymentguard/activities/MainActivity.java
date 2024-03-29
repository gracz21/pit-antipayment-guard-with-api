package com.antypaymentguard.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.antypaymentguard.R;
import com.antypaymentguard.adapters.BankExpandableListViewAdapter;
import com.antypaymentguard.api.Loader;
import com.antypaymentguard.models.Bank;
import com.antypaymentguard.models.BankAccount;
import com.antypaymentguard.models.BankAccountTransaction;
import com.antypaymentguard.shared.TimeSharedPreferences;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ExpandableListView.OnChildClickListener {
    private List<String> listDataHeader;
    private HashMap<String, List<BankAccount>> listDataChild;
    private BankExpandableListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        assert floatingActionButton != null;
        floatingActionButton.setOnClickListener(this);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.banksExpandableListView);
        adapter = new BankExpandableListViewAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(this);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchBanksTask().execute();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, SignInToBankActivity.class));
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        BankAccount selectedBankAccount = (BankAccount) adapter.getChild(groupPosition, childPosition);
        Intent intent = new Intent(MainActivity.this, BankAccountActivity.class);
        intent.putExtra("bankAccount", selectedBankAccount);
        startActivity(intent);
        return false;
    }

    private class FetchBanksTask extends AsyncTask<Void, Void, Map<String, List<BankAccount>>> {
        @Override
        protected Map<String, List<BankAccount>> doInBackground(Void... params) {
            Map<String, List<BankAccount>> result = new HashMap<>();
            List<Bank> banks = Select.from(Bank.class).list();
            for (Bank bank : banks) {
                List<BankAccount> bankAccounts = bank.getBankAccounts();
                result.put(bank.getName(), bankAccounts);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Map<String, List<BankAccount>> hashMap) {
            listDataHeader.clear();
            listDataChild.clear();
            adapter.notifyDataSetChanged();

            listDataHeader.addAll(hashMap.keySet());
            listDataChild.putAll(hashMap);
            adapter.notifyDataSetChanged();

            for (List<BankAccount> bankAccountList : hashMap.values()) {
                for (BankAccount bankAccount : bankAccountList) {
                    if (TimeSharedPreferences.getLastTime(bankAccount.getIban()) >= 0) {
                        final List<BankAccountTransaction> transactions = Loader.getTransactions();
                        for (BankAccountTransaction transaction : transactions) {
                            transaction.setBankAccount(bankAccount);
                            transaction.save();
                        }

                        TimeSharedPreferences.change().setKey(bankAccount.getIban()).commit();
                    }
                    bankAccount.loadCurrentMonthTransactions();
                }
            }
        }
    }
}
