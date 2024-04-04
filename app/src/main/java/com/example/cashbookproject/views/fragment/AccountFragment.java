package com.example.cashbookproject.views.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.example.cashbookproject.R;
import com.example.cashbookproject.adapter.AccountAdapterTwo;
import com.example.cashbookproject.adapter.TransactionAdapter;
import com.example.cashbookproject.databinding.FragmentAccountBinding;
import com.example.cashbookproject.models.Account;
import com.example.cashbookproject.models.Transaction;
import com.example.cashbookproject.util.Constants;
import com.example.cashbookproject.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;
    public MainViewModel viewModel;

    public AccountFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        binding.transactionList.setLayoutManager(new LinearLayoutManager(getContext()));


        viewModel.allTransaction.observe(getViewLifecycleOwner(),
                new Observer<List<Transaction>>() {
                    @Override
                    public void onChanged(List<Transaction> transactions) {

                        AccountAdapterTwo adapter = new AccountAdapterTwo(getActivity(),
                                getAccount(transactions));
                        binding.transactionList.setAdapter(adapter);

                        if (transactions.size() > 0){
                            binding.emptyState.setVisibility(View.GONE);
                        } else {
                            binding.emptyState.setVisibility(View.VISIBLE);
                        }

                    }
                });

        viewModel.getTransaction();

        return  binding.getRoot();

    }



    private ArrayList<Account> getAccount(List<Transaction> transactions){

        ArrayList<Account> data = new ArrayList<>();

        Map<String, Double> categoryMap = new HashMap<>();

        for (Transaction trans: transactions){
            String account = trans.getAccount();
            double amount = trans.getAmount();
            String type = trans.getType();
            if (type.equalsIgnoreCase(Constants.EXPENSE)){
                amount = amount * -1;
            }

            if (categoryMap.containsKey(account)){
                double currentTotal = categoryMap
                        .get(account).doubleValue();
                currentTotal += amount;
                categoryMap.put(account, currentTotal);
            } else {
                categoryMap.put(account, amount);
            }
        }

        for(Map.Entry<String, Double> entry: categoryMap.entrySet()){
            data.add(new Account(entry.getValue(), entry.getKey()));
        }

        return data;

    }

}