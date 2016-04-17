package com.antypaymentguard.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.antypaymentguard.R;
import com.antypaymentguard.activity.addBankAccount.TabAuthenticate;
import com.antypaymentguard.activity.addBankAccount.TabSetAccounts;
import com.antypaymentguard.activity.addBankAccount.TabSetCondition;

/**
 * @author Kamil Walkowiak
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabAuthenticate();
            case 1:
                return new TabSetAccounts();
            case 2:
                return new TabSetCondition();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Auth";
            case 1:
                return "Account";
            case 2:
                return "Condition";
        }
        return null;
    }
}