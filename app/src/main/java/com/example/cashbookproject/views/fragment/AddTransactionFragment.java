package com.example.cashbookproject.views.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cashbookproject.adapter.AccountAdapter;
import com.example.cashbookproject.models.Account;
import com.example.cashbookproject.models.Category;
import com.example.cashbookproject.R;
import com.example.cashbookproject.adapter.CategoryAdapter;
import com.example.cashbookproject.databinding.FragmentAddTransactionBinding;
import com.example.cashbookproject.databinding.ListDialogeBinding;
import com.example.cashbookproject.models.Transaction;
import com.example.cashbookproject.repository.DatabaseHelper;
import com.example.cashbookproject.util.Constants;
import com.example.cashbookproject.views.activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class AddTransactionFragment extends BottomSheetDialogFragment {


    FragmentAddTransactionBinding binding;
    Transaction transaction;

    DatabaseHelper database;


    public AddTransactionFragment() {
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
        binding = FragmentAddTransactionBinding.inflate(inflater);
        transaction = new Transaction();

        binding.incomeBtn.setOnClickListener( listener -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.incomeBtn));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.defaultBtn));

            transaction.setType(Constants.INCOME);
        });

        binding.expenseBtn.setOnClickListener(listener -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.defaultBtn));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.expenseBtn));

            transaction.setType(Constants.EXPENSE);
        });


        binding.inputDate.setOnClickListener(listener -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, view.getDayOfMonth());
                calendar.set(Calendar.MONTH, view.getMonth());
                calendar.set(Calendar.YEAR, view.getYear());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
                String dateToShow = dateFormat.format(calendar.getTime());
                binding.inputDate.setText(dateToShow);
                transaction.setDate(calendar.getTime());
            });
            datePickerDialog.show();
        });

        binding.inputCategory.setOnClickListener(catList -> {
            ListDialogeBinding dialogeBinding = ListDialogeBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogeBinding.getRoot());


            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categories,
                    new CategoryAdapter.CategoryClickListener() {
                        @Override
                        public void onCategoryClicked(Category category) {
                            binding.inputCategory.setText(category.getName());
                            transaction.setCategory(category.getName());
                            categoryDialog.dismiss();
                        }
                    });

            dialogeBinding.rvListDialoge.setLayoutManager(new GridLayoutManager(getContext(), 3));
            dialogeBinding.rvListDialoge.setAdapter(categoryAdapter);

            categoryDialog.show();
        });

        binding.inputAccount.setOnClickListener(acList -> {
            ListDialogeBinding dialogeBinding = ListDialogeBinding.inflate(inflater);
            AlertDialog accountDialog = new AlertDialog.Builder(getContext()).create();
            accountDialog.setView(dialogeBinding.getRoot());



            AccountAdapter adapter = new AccountAdapter(getContext(),
                    Constants.accountsLabel,
                    new AccountAdapter.AccountClickListener() {
                        @Override
                        public void onAccountSelected(Account account) {
                            binding.inputAccount.setText(account.getName());
                            transaction.setAccount(account.getName());
                            accountDialog.dismiss();
                        }
                    });
            dialogeBinding.rvListDialoge.setLayoutManager(new LinearLayoutManager(getContext()));
            dialogeBinding.rvListDialoge.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

            dialogeBinding.rvListDialoge.setAdapter(adapter);

            accountDialog.show();

        });




        binding.saveBtn.setOnClickListener(
                v -> {
                    double amount = Double.parseDouble(binding
                            .inputAmount.getText().toString());
                    String note = binding.inuputNote.getText().toString();

//                    if (transaction.getType().equalsIgnoreCase(Constants.EXPENSE)){
//                        amount = amount * -1;
//                    }

                    transaction.setAmount(amount);
                    transaction.setNote(note);

                    ((MainActivity) requireActivity()).viewModel.addTransaction(transaction);
                    ((MainActivity) requireActivity()).refreshTransaction();
                    dismiss();
                }
        );

        return binding.getRoot();
    }
}