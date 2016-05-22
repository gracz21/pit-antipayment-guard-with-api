package com.antypaymentguard.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.antypaymentguard.R;
import com.antypaymentguard.fragments.BankAccountDetailsFragment;
import com.antypaymentguard.fragments.TransactionsListFragment;
import com.antypaymentguard.models.BankAccount;
import com.antypaymentguard.models.Transaction;

import java.util.ArrayList;

/**
 * @author Kamil Walkowiak
 */
public class BankAccountSectionAdapter extends FragmentPagerAdapter {
    final int pageCount = 2;
    private Context context;
    private BankAccount bankAccount;
    private ArrayList<Transaction> transactions;

    public BankAccountSectionAdapter(FragmentManager fm, Context context, BankAccount bankAccount, ArrayList<Transaction> transactions) {
        super(fm);
        this.context = context;
        this.bankAccount = bankAccount;
        this.transactions = transactions;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return BankAccountDetailsFragment.newInstance(bankAccount);
            case 1:
                return TransactionsListFragment.newInstance(transactions);
        }
        return null;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.title_fragment_pay_card_details);
            case 1:
                return context.getString(R.string.title_fragment_transactions_list);
        }
        return null;
    }
}
