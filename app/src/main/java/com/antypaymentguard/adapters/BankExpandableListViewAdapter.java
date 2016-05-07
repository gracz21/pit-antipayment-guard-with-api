package com.antypaymentguard.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.antypaymentguard.R;
import com.antypaymentguard.models.Bank;
import com.antypaymentguard.models.BankAccount;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class BankExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<BankAccount>> listDataChild;

    private static class ChildViewHolder {
        TextView nameTextView;
        TextView ibanTextView;
        TextView balanceTextView;
    }

    private static class GroupViewHolder {
        TextView bankNameTextView;
        Button removeBankButton;
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
            convertView = layoutInflater.inflate(R.layout.item_child, parent, false);
            childViewHolder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            childViewHolder.ibanTextView = (TextView) convertView.findViewById(R.id.noTextView);
            childViewHolder.balanceTextView = (TextView) convertView.findViewById(R.id.balanceTextView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        childViewHolder.nameTextView.setText(bankAccount.getName());
        childViewHolder.ibanTextView.setText(bankAccount.getIban());

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        childViewHolder.balanceTextView.setText(df.format(bankAccount.getBalance()));
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
            convertView = layoutInflater.inflate(R.layout.item_group, parent, false);
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
            (new Select().from(Bank.class).where("Name = ?", bankName[0]).executeSingle()).delete();
            return null;
        }
    }
}


