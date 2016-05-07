package com.antypaymentguard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.antypaymentguard.R;
import com.antypaymentguard.models.BankAccount;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class BankAccountListViewAdapter extends ArrayAdapter<BankAccount> {
    private Context context;

    private static class ViewHolder {
        TextView bankAccountNameTextView;
        TextView bankAccountIbanTextView;
        TextView bankAccountBalanceTextView;
        TextView bankAccountOwnerTextView;
    }

    public BankAccountListViewAdapter(Context context, List<BankAccount> objects) {
        super(context, R.layout.bank_account_item, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BankAccount bankAccount = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.bank_account_item, parent, false);
            viewHolder.bankAccountNameTextView = (TextView) convertView.findViewById(R.id.bankAccountNameTextView);
            viewHolder.bankAccountIbanTextView = (TextView) convertView.findViewById(R.id.bankAccountIbanTextView);
            viewHolder.bankAccountBalanceTextView = (TextView) convertView.findViewById(R.id.bankAccountBalanceTextView);
            viewHolder.bankAccountOwnerTextView = (TextView) convertView.findViewById(R.id.bankAccountOwnerTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bankAccountNameTextView.setText(bankAccount.getName());

        String iban = context.getString(R.string.iban) + ": " + bankAccount.getIban();
        viewHolder.bankAccountIbanTextView.setText(iban);

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        String balance = context.getString(R.string.balance) + ": " +
                df.format(bankAccount.getBalance()) + " " + bankAccount.getCurrencyName();
        viewHolder.bankAccountBalanceTextView.setText(balance);

        String owner = context.getString(R.string.owner) + ": " + bankAccount.getOwner();
        viewHolder.bankAccountOwnerTextView.setText(owner);
        return convertView;
    }


}
