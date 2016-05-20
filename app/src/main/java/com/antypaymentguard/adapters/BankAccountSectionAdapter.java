package com.antypaymentguard.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.antypaymentguard.R;
import com.antypaymentguard.fragments.BankAccountDetailsFragment;
import com.antypaymentguard.fragments.TransactionsListFragment;

/**
 * @author Kamil Walkowiak
 */
public class BankAccountSectionAdapter extends FragmentPagerAdapter {
    final int pageCount = 2;
    private Context context;

    public BankAccountSectionAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BankAccountDetailsFragment();
            case 1:
                return new TransactionsListFragment();
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
