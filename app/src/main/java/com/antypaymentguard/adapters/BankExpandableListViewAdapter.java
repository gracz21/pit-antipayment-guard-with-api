package com.antypaymentguard.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.antypaymentguard.R;
import com.antypaymentguard.models.Bank;
import com.antypaymentguard.models.BankAccount;
import com.antypaymentguard.models.conditions.AmountCondition;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class BankExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<BankAccount>> listDataChild;

    private static class GroupViewHolder {
        TextView bankNameTextView;
        Button removeBankButton;
    }

    private static class ChildViewHolder {
        ImageView bankAccountIconImageView;
        TextView nameTextView;
        TextView ibanTextView;
        TextView balanceTextView;
        TextView remainingTextView;
    }

    public BankExpandableListViewAdapter(Context context, List<String> listDataHeader, HashMap<String, List<BankAccount>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final BankAccount bankAccount = (BankAccount) getChild(groupPosition, childPosition);

        ChildViewHolder childViewHolder;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.banks_item_child, parent, false);
            childViewHolder.bankAccountIconImageView = (ImageView) convertView.findViewById(R.id.bankAccountIconImageView);
            childViewHolder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            childViewHolder.ibanTextView = (TextView) convertView.findViewById(R.id.noTextView);
            childViewHolder.balanceTextView = (TextView) convertView.findViewById(R.id.balanceTextView);
            childViewHolder.remainingTextView = (TextView) convertView.findViewById(R.id.remainedTextView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        String status;
        if(bankAccount.isConditionFulfilled()) {
            childViewHolder.bankAccountIconImageView.setImageResource(R.drawable.ic_bank_account_fulfilled_48dp);
            childViewHolder.remainingTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
            status = context.getString(R.string.condition_fulfilled);
        } else {
            childViewHolder.bankAccountIconImageView.setImageResource(R.drawable.ic_bank_account_not_fulfilled_48dp);
            childViewHolder.remainingTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
            status = context.getString(R.string.remained) + ": " + bankAccount.getConditionStatus()
                    + "/" + bankAccount.getCondition().toString();
            if(bankAccount.getCondition().getClass() == AmountCondition.class) {
                status += " " + bankAccount.getCurrencyName();
            } else {
                status += " " + context.getString(R.string.transactions);
            }
        }
        childViewHolder.remainingTextView.setText(status);

        childViewHolder.nameTextView.setText(bankAccount.getName());

        String iban = context.getString(R.string.iban) + ": " + bankAccount.getIban();
        childViewHolder.ibanTextView.setText(iban);

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        String balance = context.getString(R.string.balance) + ": " +
                df.format(bankAccount.getBalance()) + " " + bankAccount.getCurrencyName();
        childViewHolder.balanceTextView.setText(balance);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final String headerTitle = (String) getGroup(groupPosition);

        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.banks_item_group, parent, false);
            groupViewHolder.bankNameTextView = (TextView) convertView.findViewById(R.id.bankNameGroupTextView);
            groupViewHolder.removeBankButton = (Button) convertView.findViewById(R.id.removeBankButton);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        groupViewHolder.bankNameTextView.setTypeface(null, Typeface.BOLD);
        groupViewHolder.bankNameTextView.setText(headerTitle);

        groupViewHolder.removeBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new RemoveBankTask()).execute(headerTitle);
                listDataHeader.remove(groupPosition);
                listDataChild.remove(headerTitle);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class RemoveBankTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... bankName) {
            (Select.from(Bank.class).where(Condition.prop("name").eq(bankName)).first()).delete();
            return null;
        }
    }
}


