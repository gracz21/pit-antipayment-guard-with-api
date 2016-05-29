package com.antypaymentguard.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antypaymentguard.R;
import com.antypaymentguard.models.BankAccount;
import com.antypaymentguard.models.conditions.AmountCondition;

/**
 * @author Kamil Walkowiak
 */
public class BankAccountDetailsFragment extends Fragment {
    private static final String bankAccountArgKey = "bankAccount";
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
        View view = inflater.inflate(R.layout.fragment_bank_account_details, container, false);
        Context context = view.getContext();

        TextView conditionTypeTextView = (TextView) view.findViewById(R.id.conditionTypeTextView);
        if(bankAccount.getCondition().getClass() == AmountCondition.class) {
            conditionTypeTextView.setText(context.getString(R.string.condition_transactions_amount));
        } else {
            conditionTypeTextView.setText(context.getString(R.string.condition_transactions_number));
        }

        TextView conditionStatusTextView = (TextView) view.findViewById(R.id.conditionStatusTextView);
        ImageView conditionStatusIcon = (ImageView) view.findViewById(R.id.conditionStatusIconImageView);
        String status = bankAccount.getConditionStatus() + "/" + bankAccount.getCondition().toString();
        if(bankAccount.getCondition().getClass() == AmountCondition.class) {
            status += " " + bankAccount.getCurrencyName();
        } else {
            status += " " + context.getString(R.string.transactions);
        }
        if(bankAccount.isConditionFulfilled()) {
            conditionStatusIcon.setImageResource(R.drawable.ic_condition_fulfilled_24dp);
            conditionStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
            status = context.getString(R.string.condition_fulfilled);
        } else {
            conditionStatusIcon.setImageResource(R.drawable.ic_condition_not_fulfilled_24dp);
            conditionStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        conditionStatusTextView.setText(status);

        ((TextView) view.findViewById(R.id.nameTextView)).setText(bankAccount.getName());
        ((TextView) view.findViewById(R.id.balanceTextView)).setText(bankAccount.getBalanceWithCurrencyName());
        ((TextView) view.findViewById(R.id.ibanTextView)).setText(bankAccount.getIban());
        ((TextView) view.findViewById(R.id.bankNameTextView)).setText(bankAccount.getBank().getName());
        ((TextView) view.findViewById(R.id.ownerTextView)).setText(bankAccount.getOwner());
        return view;
    }
}
