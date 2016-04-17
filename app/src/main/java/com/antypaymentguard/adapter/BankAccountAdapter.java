package com.antypaymentguard.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.antypaymentguard.R;
import com.antypaymentguard.model.BankAccount;

import java.util.HashMap;
import java.util.List;

public class BankAccountAdapter extends BaseExpandableListAdapter {
    public BankAccountAdapter(Context context, List<String> listDataHeader, HashMap<String, List<BankAccount>> listChildData) {
        _context = context;
        _listDataHeader = listDataHeader;
        _listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return _listDataChild.get(_listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final BankAccount bankAccount = (BankAccount) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_child, null);
        }

        final TextView textViewName = (TextView) convertView.findViewById(R.id.textViewName);
        final TextView textViewIban = (TextView) convertView.findViewById(R.id.textViewIban);
        final TextView textViewBalance = (TextView) convertView.findViewById(R.id.textViewBalance);
        textViewName.setText(bankAccount.getName());
        textViewIban.setText(bankAccount.getIban());
        textViewBalance.setText(String.valueOf(bankAccount.getBalance()));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return _listDataChild.get(_listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return _listDataHeader.size();
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
            LayoutInflater layoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_group, null);
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

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<BankAccount>> _listDataChild;
}


