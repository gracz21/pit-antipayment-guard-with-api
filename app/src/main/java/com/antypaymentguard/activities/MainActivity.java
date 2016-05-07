package com.antypaymentguard.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.activeandroid.query.Select;
import com.antypaymentguard.R;
import com.antypaymentguard.adapters.BankExpandableListViewAdapter;
import com.antypaymentguard.models.Bank;
import com.antypaymentguard.models.BankAccount;
import com.antypaymentguard.models.conditions.NumberCondition;

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

        //setupMock();

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        assert floatingActionButton != null;
        floatingActionButton.setOnClickListener(this);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.banksExpandableListView);
        adapter = new BankExpandableListViewAdapter(this, listDataHeader, listDataChild);
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
        startActivity(new Intent(MainActivity.this, SignInToBankActivity.class));
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        Snackbar.make(view, "Great! You selected bank account ;D", Snackbar.LENGTH_SHORT).show();
        return false;
    }

    private class FetchBanksTask extends AsyncTask<Void, Void, Map<String, List<BankAccount>>> {
        @Override
        protected Map<String, List<BankAccount>> doInBackground(Void... params) {
            Map<String, List<BankAccount>> result = new HashMap<>();
            List<Bank> banks = new Select().from(Bank.class).execute();
            for(Bank bank: banks) {
                result.put(bank.getName(), bank.getBankAccounts());
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
        }
    }

    private void setupMock() {

        Bank bank1 = new Bank("Test", "Test", "Test");
        bank1.save();
        Bank bank2 = new Bank("Test2", "test", "test");
        bank2.save();

        NumberCondition condition = new NumberCondition(10);
        condition.save();

        (new BankAccount("TestA1", "1", "PLN", 20.0, "Test", bank1, condition)).save();
        (new BankAccount("TestA2", "2", "PLN", 20.0, "Test", bank2, condition)).save();
        (new BankAccount("TestA3", "3", "PLN", 20.0, "Test", bank1, condition)).save();
    }
}
