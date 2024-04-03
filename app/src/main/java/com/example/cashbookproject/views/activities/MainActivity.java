package com.example.cashbookproject.views.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cashbookproject.R;
import com.example.cashbookproject.adapter.TransactionAdapter;
import com.example.cashbookproject.databinding.ActivityMainBinding;
import com.example.cashbookproject.models.Transaction;
import com.example.cashbookproject.repository.DatabaseHelper;
import com.example.cashbookproject.util.Constants;
import com.example.cashbookproject.util.Helper;
import com.example.cashbookproject.viewModel.MainViewModel;
import com.example.cashbookproject.views.fragment.AddTransactionFragment;
import com.example.cashbookproject.views.fragment.StatsFragment;
import com.example.cashbookproject.views.fragment.TransactionFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Calendar calendar;

  public MainViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setSupportActionBar(binding.toolBarHeader);
        getSupportActionBar().setTitle("Transaction");

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        Constants.setCategories();
        Constants.setAccountLabel();
        calendar = Calendar.getInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.transContent, new TransactionFragment());
        transaction.commit();

        binding.bottomNavigationView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        if (menuItem.getItemId() == R.id.menuTransaction){
                            transaction.replace(R.id.transContent, new TransactionFragment());
                            getSupportActionBar().setTitle("Transaction");
                        } else if (menuItem.getItemId() == R.id.menuStats) {
                            transaction.replace(R.id.transContent, new StatsFragment());
                            getSupportActionBar().setTitle("Stats");
                        }
                        transaction.commit();
                        return  true;
                    }
                }
        );


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    public void refreshTransaction(){
        viewModel.getTransaction(calendar);
    }





}


