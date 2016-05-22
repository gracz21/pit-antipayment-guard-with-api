package com.antypaymentguard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.antypaymentguard.R;
import com.antypaymentguard.models.Transaction;

import java.text.DateFormat;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class TransactionListViewAdapter extends ArrayAdapter<Transaction> {
    private Context context;

    private static class ViewHolder {
        ImageView transactionIconImageView;
        TextView transactionTitleTextView;
        TextView transactionAmountTextView;
        TextView transactionDateTextView;
    }

    public TransactionListViewAdapter(Context context, List<Transaction> objects) {
        super(context, R.layout.transaction_item, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Transaction transaction = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.transaction_item, parent, false);
            viewHolder.transactionIconImageView = (ImageView) convertView.findViewById(R.id.transactionIconImageView);
            viewHolder.transactionTitleTextView = (TextView) convertView.findViewById(R.id.transactionTitleTextView);
            viewHolder.transactionAmountTextView = (TextView) convertView.findViewById(R.id.transactionAmountTextVew);
            viewHolder.transactionDateTextView = (TextView) convertView.findViewById(R.id.transactionDateTextVew);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(transaction.getAmount() > 0) {
            viewHolder.transactionIconImageView.setImageResource(R.drawable.ic_transaction_in_48dp);
        } else {
            viewHolder.transactionIconImageView.setImageResource(R.drawable.ic_transaction_out_48dp);
        }

        viewHolder.transactionTitleTextView.setText(transaction.getTitle());

        String amount = context.getString(R.string.amount) + ": " + transaction.getAmountWithCurrencyName();
        viewHolder.transactionAmountTextView.setText(amount);

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context.getApplicationContext());
        String date = context.getString(R.string.transactionDate) + ": " + dateFormat.format(transaction.getDate());
        viewHolder.transactionDateTextView.setText(date);
        return convertView;
    }
}
