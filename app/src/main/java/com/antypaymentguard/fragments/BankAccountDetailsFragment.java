package com.antypaymentguard.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antypaymentguard.R;
import com.antypaymentguard.models.BankAccount;

import java.text.DecimalFormat;

/**
 * @author Kamil Walkowiak
 */
public class BankAccountDetailsFragment extends Fragment {
    private static String bankAccountArgKey = "bankAccount";
    private BankAccount bankAccount;

    public static BankAccountDetailsFragment newInstance(BankAccount bankAccount) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(bankAccountArgKey, bankAccount);
        BankAccountDetailsFragment bankAccountDetailsFragment = new BankAccountDetailsFragment();
        bankAccountDetailsFragment.setArguments(bundle);
        return bankAccountDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bankAccount = (BankAccount) getArguments().get(bankAccountArgKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        String balance = df.format(bankAccount.getBalance()) + " " + bankAccount.getCurrencyName();

        View view = inflater.inflate(R.layout.fragment_bank_account_details, container, false);
        ((TextView) view.findViewById(R.id.nameTextView)).setText(bankAccount.getName());
        ((TextView) view.findViewById(R.id.balanceTextView)).setText(balance);
        ((TextView) view.findViewById(R.id.ibanTextView)).setText(bankAccount.getIban());
        ((TextView) view.findViewById(R.id.bankNameTextView)).setText(bankAccount.getBank().getName());
        ((TextView) view.findViewById(R.id.ownerTextView)).setText(bankAccount.getOwner());
        return view;
    }
}
