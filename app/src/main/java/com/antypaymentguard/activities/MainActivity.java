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
import com.antypaymentguard.models.Bank;
import com.antypaymentguard.models.BankAccount;
import com.antypaymentguard.models.BankAccountTransaction;
import com.antypaymentguard.models.conditions.NumberCondition;
import com.orm.SugarRecord;
import com.orm.query.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            for(Bank bank: banks) {
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
        }
    }

    private void setupMock() {

        Bank bank1 = new Bank("Test", "Test", "Test");
        bank1.save();
        Bank bank2 = new Bank("Test2", "test", "test");
        bank2.save();

        NumberCondition condition = new NumberCondition(10);
        condition.save();

        BankAccount bankAccount = new BankAccount("TestA1", "1", "PLN", 20.0, "Test", bank1, condition);
        SugarRecord.save(bankAccount);
        SugarRecord.save(new BankAccount("TestA2", "2", "PLN", 20.0, "Test", bank2, condition));
        SugarRecord.save(new BankAccount("TestA3", "3", "PLN", 20.0, "Test", bank1, condition));

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            (new BankAccountTransaction("Usługi medyczne za okres 01/04/2016 do 31/04/2016",
                    format.parse("22-05-2016"), -127.32, "Medicover Sp. z o.o. Al. Jerozolimskie 96 00-807 Warszawa, Polska NIP: 525-15-77-627",
                    null, "PRZELEW ZEWNĘTRZNY", bankAccount)).save();
            (new BankAccountTransaction("Składka ZUS, deklaracja nr.: 203721",
                    format.parse("05-05-2016"), -696.00, "Zakład Ubezpieczeń Społecznych",
                    "PL83101010230000261395100000", "PRZELEW ZUS", bankAccount)).save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
