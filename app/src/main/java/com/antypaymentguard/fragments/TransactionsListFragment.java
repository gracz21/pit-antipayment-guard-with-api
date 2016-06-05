package com.antypaymentguard.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.antypaymentguard.R;
import com.antypaymentguard.activities.BankAccountTransactionActivity;
import com.antypaymentguard.adapters.TransactionListViewAdapter;
import com.antypaymentguard.models.BankAccount;
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
        final TransactionListViewAdapter adapter = new TransactionListViewAdapter(getContext(), bankAccountTransactions);
        ListView transactionListView = (ListView) view.findViewById(R.id.transactionListView);
        transactionListView.setAdapter(adapter);
        transactionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BankAccountTransaction selectedTransaction = adapter.getItem(position);
                Intent intent = new Intent(view.getContext(), BankAccountTransactionActivity.class);
                intent.putExtra("transaction", selectedTransaction);
                startActivity(intent);
            }
        });
        return view;
    }
}
