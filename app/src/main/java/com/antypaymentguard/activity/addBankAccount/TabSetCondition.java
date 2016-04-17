package com.antypaymentguard.activity.addBankAccount;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antypaymentguard.R;

/**
 * @author Kamil Walkowiak
 */
public class TabSetCondition extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_set_condition_add_bank_account, container, false);
    }
}
