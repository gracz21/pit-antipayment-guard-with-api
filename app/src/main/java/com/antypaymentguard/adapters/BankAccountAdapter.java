package com.antypaymentguard.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.antypaymentguard.R;
import com.antypaymentguard.models.BankAccount;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class BankAccountAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<BankAccount>> listDataChild;

    private static class ViewHolder {
        TextView nameTextView;
        TextView ibanTextView;
        TextView balanceTextView;
    }

    public BankAccountAdapter(Context context, List<String> listDataHeader, HashMap<String, List<BankAccount>> listChildData) {
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

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_child, parent, false);
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            viewHolder.ibanTextView = (TextView) convertView.findViewById(R.id.noTextView);
            viewHolder.balanceTextView = (TextView) convertView.findViewById(R.id.balanceTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nameTextView.setText(bankAccount.getName());
        viewHolder.ibanTextView.setText(bankAccount.getIban());

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        viewHolder.balanceTextView.setText(df.format(bankAccount.getBalance()));
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_group, parent, false);
        }

        TextView textViewHeader = (TextView) convertView.findViewById(R.id.textView);
        textViewHeader.setTypeface(null, Typeface.BOLD);
        textViewHeader.setText(headerTitle);

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
}


