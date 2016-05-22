package com.antypaymentguard.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.antypaymentguard.adapters.BankAccountSectionAdapter;

import com.antypaymentguard.R;
import com.antypaymentguard.models.BankAccount;
import com.antypaymentguard.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BankAccountActivity extends AppCompatActivity {
    private BankAccountSectionAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BankAccount bankAccount = (BankAccount) getIntent().getSerializableExtra("bankAccount");
        ArrayList<Transaction> transactions = (ArrayList<Transaction>) getIntent().getSerializableExtra("transactions");
        mSectionsPagerAdapter = new BankAccountSectionAdapter(getSupportFragmentManager(), this, bankAccount, transactions);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bank_account_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
