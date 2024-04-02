package com.example.cashbookproject.views.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cashbookproject.R;
import com.example.cashbookproject.adapter.TransactionAdapter;
import com.example.cashbookproject.databinding.ActivityMainBinding;
import com.example.cashbookproject.models.Transaction;
import com.example.cashbookproject.util.Constants;
import com.example.cashbookproject.util.Helper;
import com.example.cashbookproject.views.fragment.AddTransactionFragment;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolBarHeader);
        getSupportActionBar().setTitle("Transaction");

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        Constants.setCategories();

        binding.floatBtnAdd.setOnClickListener(listener -> {
            new AddTransactionFragment().show(getSupportFragmentManager(), null);
        });

        calendar = Calendar.getInstance();
        updateDate();
        binding.rightArrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendar.add(Calendar.DATE, 1);
                        updateDate();
                    }
                }
        );

        binding.leftArrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendar.add(Calendar.DATE, -1);
                        updateDate();
                    }
                }
        );

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(
                new Transaction(1l, "Income", "Business", "Cash", "Why",  new Date(), 500)
        );
        transactions.add(
                new Transaction(2l, "Income", "Business", "Cash", "Why",  new Date(), 500)
        );
        transactions.add(
                new Transaction(3l, "Expense", "Salary", "Bank", "Why",  new Date(), 500)
        );

        TransactionAdapter adapter = new TransactionAdapter(this, transactions);
        binding.transactionList.setLayoutManager(new LinearLayoutManager(this));
        binding.transactionList.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    void updateDate(){
       binding.date.setText(Helper.formatDate(calendar.getTime()));
    }



}


