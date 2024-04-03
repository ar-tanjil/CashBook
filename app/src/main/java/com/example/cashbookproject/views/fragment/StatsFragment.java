package com.example.cashbookproject.views.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.cashbookproject.R;
import com.example.cashbookproject.databinding.FragmentStatsBinding;
import com.example.cashbookproject.models.Transaction;
import com.example.cashbookproject.util.Constants;
import com.example.cashbookproject.util.Helper;
import com.example.cashbookproject.viewModel.MainViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatsFragment extends Fragment {

    FragmentStatsBinding binding;
    Calendar calendar;
    public MainViewModel viewModel;

    public StatsFragment() {
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
        binding = FragmentStatsBinding.inflate(inflater);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        calendar = Calendar.getInstance();
        updateDate();
        binding.rightArrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Constants.SELECTED_STATS == Constants.DAILY){
                            calendar.add(Calendar.DATE, 1);
                        } else if (Constants.SELECTED_STATS == Constants.MONTHLY) {
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
                        if (Constants.SELECTED_STATS == Constants.DAILY){
                            calendar.add(Calendar.DATE, -1);

                        } else if (Constants.SELECTED_STATS == Constants.MONTHLY) {
                            calendar.add(Calendar.MONTH, -1);
                        }
                        updateDate();

                    }
                }
        );

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



        binding.incomeBtn.setOnClickListener( listener -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.incomeBtn));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.defaultBtn));

           Constants.SELECTED_STATS_TYPE = Constants.INCOME;
           updateDate();
        });

        binding.expenseBtn.setOnClickListener(listener -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.defaultBtn));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.expenseBtn));

            Constants.SELECTED_STATS_TYPE = Constants.EXPENSE;
            updateDate();
        });

        Pie pie = AnyChart.pie();
        viewModel.categoryTransaction.observe(getViewLifecycleOwner(),
                new Observer<List<Transaction>>() {
                    @Override
                    public void onChanged(List<Transaction> transactions) {


                        if (transactions.size() > 0){
                            List<DataEntry> data = new ArrayList<>();
                            binding.emptyState.setVisibility(View.GONE);
                            binding.chart.setVisibility(View.VISIBLE);

                            Map<String, Double> categoryMap = new HashMap<>();

                            for (Transaction trans: transactions){
                                String category = trans.getCategory();
                                double amount = trans.getAmount();

                                if (categoryMap.containsKey(category)){
                                    double currentTotal = categoryMap
                                            .get(category).doubleValue();
                                    currentTotal += amount;
                                    categoryMap.put(category, currentTotal);
                                } else {
                                    categoryMap.put(category, amount);
                                }
                            }

                            for(Map.Entry<String, Double> entry: categoryMap.entrySet()){
                                data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));
                            }
                            pie.data(data);

                        } else{
                            binding.emptyState.setVisibility(View.VISIBLE);
                            binding.chart.setVisibility(View.GONE);
                        }



                    }
                });

        viewModel.getTransaction(calendar, Constants.SELECTED_STATS_TYPE);



//        pie.title("Fruits imported in 2015 (in kg)");

        pie.labels().position("outside");

//        pie.legend().title().enabled(true);
//        pie.legend().title()
//                .text("Retail channels")
//                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        binding.chart.setChart(pie);

        return binding.getRoot();
    }

    void updateDate(){

        if (Constants.SELECTED_STATS == Constants.DAILY){
            binding.date.setText(Helper.formatDate(calendar.getTime()));
        } else if (Constants.SELECTED_STATS == Constants.MONTHLY) {
            binding.date.setText(Helper.formatDateByMonth(calendar.getTime()));
        }
        viewModel.getTransaction(calendar, Constants.SELECTED_STATS_TYPE);

    }

    private void changeTab(TabLayout.Tab tab){
        String name = tab.getText().toString();

        switch (name){
            case "Daily":
                Constants.SELECTED_STATS = Constants.DAILY;
                calendar = Calendar.getInstance();
                break;
            case "Monthly":
                Constants.SELECTED_STATS = Constants.MONTHLY;
                break;
        }
    }


}