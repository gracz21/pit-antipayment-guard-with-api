package com.antypaymentguard.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.antypaymentguard.R;
import com.antypaymentguard.adapters.TransactionListViewAdapter;
import com.antypaymentguard.models.BankAccountTransaction;

import java.util.ArrayList;
/**
 * @author Kamil Walkowiak
 */
public class TransactionsListFragment extends Fragment {
    private static final String transactionsListArgKey = "transactionsList";
    private ArrayList<BankAccountTransaction> bankAccountTransactions;

    public static TransactionsListFragment newInstance(ArrayList<BankAccountTransaction> bankAccountTransactions) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(transactionsListArgKey, bankAccountTransactions);
        TransactionsListFragment transactionsListFragment = new TransactionsListFragment();
        transactionsListFragment.setArguments(bundle);
        return transactionsListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bankAccountTransactions = (ArrayList<BankAccountTransaction>) getArguments().getSerializable(transactionsListArgKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions_list, container, false);
        TransactionListViewAdapter adapter = new TransactionListViewAdapter(getContext(), bankAccountTransactions);
        ListView transactionListView = (ListView) view.findViewById(R.id.transactionListView);
        transactionListView.setAdapter(adapter);
        return view;
    }
}
