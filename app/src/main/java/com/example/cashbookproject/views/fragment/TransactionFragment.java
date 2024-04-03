package com.example.cashbookproject.views.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cashbookproject.R;
import com.example.cashbookproject.adapter.TransactionAdapter;
import com.example.cashbookproject.databinding.FragmentAddTransactionBinding;
import com.example.cashbookproject.databinding.FragmentTransactionBinding;
import com.example.cashbookproject.models.Transaction;
import com.example.cashbookproject.util.Constants;
import com.example.cashbookproject.util.Helper;
import com.example.cashbookproject.viewModel.MainViewModel;
import com.example.cashbookproject.views.activities.MainActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TransactionFragment extends Fragment {

FragmentTransactionBinding binding;
    Calendar calendar;

    public MainViewModel viewModel;

    public TransactionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionBinding.inflate(inflater);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        binding.floatBtnAdd.setOnClickListener(listener -> {
            new AddTransactionFragment().show(getParentFragmentManager(), null);
        });

        calendar = Calendar.getInstance();
        updateDate();
        binding.rightArrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Constants.SELECTED_TAB == Constants.DAILY){
                            calendar.add(Calendar.DATE, 1);

                        } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
                            calendar.add(Calendar.MONTH, 1);

                        }
                        updateDate();

                    }
                }
        );

        binding.leftArrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Constants.SELECTED_TAB == Constants.DAILY){
                            calendar.add(Calendar.DATE, -1);
                        } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
                            calendar.add(Calendar.MONTH, -1);
                        }
                        updateDate();

                    }
                }
        );



        binding.transactionList.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.transactionList.observe(getViewLifecycleOwner(),
                new Observer<List<Transaction>>() {
                    @Override
                    public void onChanged(List<Transaction> transactions) {

                        TransactionAdapter adapter = new TransactionAdapter(getActivity(),
                                (ArrayList<Transaction>) transactions);
                        binding.transactionList.setAdapter(adapter);

                        if (transactions.size() > 0){
                            binding.emptyState.setVisibility(View.GONE);
                        } else {
                            binding.emptyState.setVisibility(View.VISIBLE);
                        }

                    }
                });

        viewModel.totalIncome.observe(getViewLifecycleOwner(),
                new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        binding.income.setText(String.valueOf(aDouble));
                    }
                });

        viewModel.totalExpense.observe(getViewLifecycleOwner(),
                new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        binding.expense.setText(String.valueOf(aDouble));
                    }
                });

        viewModel.totalAmount.observe(getViewLifecycleOwner(),
                new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        binding.total.setText(String.valueOf(aDouble));
                    }
                });

        viewModel.getTransaction(calendar);


        binding.tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        changeTab(tab);
                        updateDate();
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                }
        );



        return binding.getRoot();
    }

    void updateDate(){

        if (Constants.SELECTED_TAB == Constants.DAILY){
            binding.date.setText(Helper.formatDate(calendar.getTime()));
        } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
            binding.date.setText(Helper.formatDateByMonth(calendar.getTime()));
        }
        viewModel.getTransaction(calendar);

    }


    private void changeTab(TabLayout.Tab tab){
        String name = tab.getText().toString();

        switch (name){
            case "Daily":
                Constants.SELECTED_TAB = Constants.DAILY;
                calendar = Calendar.getInstance();
                break;
            case "Monthly":
                Constants.SELECTED_TAB = Constants.MONTHLY;
                break;
        }
    }


}