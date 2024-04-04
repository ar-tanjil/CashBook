package com.example.cashbookproject.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cashbookproject.models.Transaction;
import com.example.cashbookproject.repository.DatabaseHelper;
import com.example.cashbookproject.util.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

   public MutableLiveData<List<Transaction>> transactionList = new MutableLiveData<>();
   public MutableLiveData<List<Transaction>> categoryTransaction = new MutableLiveData<>();

   public MutableLiveData<List<Transaction>> allTransaction = new MutableLiveData<>();


   public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
   public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
   public MutableLiveData<Double> totalAmount = new MutableLiveData<>();

    DatabaseHelper database;

    Calendar calender;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = DatabaseHelper.getDb(application);

    }

    public void getTransaction(){

            List<Transaction> transactions = database.transactionDao()
                    .getAll();

            allTransaction.setValue(transactions);

    }




    public void getTransaction(Calendar calendar, String type){
        this.calender = calendar;

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (Constants.SELECTED_STATS == Constants.DAILY){

            Date start = new Date(calendar.getTime().getTime());
            Date end = new Date(calendar.getTime().getTime() + (24*60*60*1000));

            List<Transaction> transactions = database.transactionDao()
                    .getAllByDateAndCategory(start,end, type);

            categoryTransaction.setValue(transactions);

        } else if (Constants.SELECTED_STATS == Constants.MONTHLY){

            calender.set(Calendar.DAY_OF_MONTH, 0);
            Date start = new Date(calendar.getTime().getTime());

            calendar.add(Calendar.MONTH, 1);
            Date end = new Date(calendar.getTime().getTime());


            List<Transaction> transactions = database.transactionDao()
                    .getAllByDateAndCategory(start,end, type);

            categoryTransaction.setValue(transactions);

        }



    }



    public void getTransaction(Calendar calendar){
        this.calender = calendar;
        double income = 0;
        double expense = 0;
        double balance = 0;

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (Constants.SELECTED_TAB == Constants.DAILY){

            Date start = new Date(calendar.getTime().getTime());
            Date end = new Date(calendar.getTime().getTime() + (24*60*60*1000));

            List<Transaction> transactions = database.transactionDao()
                    .getAllByDate(start,end);

             income = database.transactionDao()
                    .getIncomeByDate(start, end);

            expense = database.transactionDao()
                    .getExpenseByDate(start,end);

            balance = income - expense;
            transactionList.setValue(transactions);

        } else if (Constants.SELECTED_TAB == Constants.MONTHLY){

            calender.set(Calendar.DAY_OF_MONTH, 0);
            Date start = new Date(calendar.getTime().getTime());

            calendar.add(Calendar.MONTH, 1);
            Date end = new Date(calendar.getTime().getTime());


            List<Transaction> transactions = database.transactionDao()
                    .getAllByDate(start,end);

            income = database.transactionDao()
                    .getIncomeByDate(start, end);

            expense = database.transactionDao()
                    .getExpenseByDate(start,end);

            balance = income - expense;
            transactionList.setValue(transactions);

        }

        totalIncome.setValue(income);
        totalExpense.setValue(expense);
        totalAmount.setValue(balance);

    }


    public void addTransaction(Transaction transaction){
        database.transactionDao().add(transaction);
    }

    public void deleteTransaction(Transaction transaction){
        database.transactionDao().delete(transaction);
        getTransaction(calender);
    }

    @NonNull
    private ArrayList<Transaction> saveInitialTransaction() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        Date date = new Date();

        transactions.add(
                new Transaction( "Income", "Business", "Cash", "Why",  date, 500)
        );
        transactions.add(
                new Transaction( "Income", "Business", "Cash", "Why",  date, 500)
        );
        transactions.add(
                new Transaction( "Expense", "Salary", "Bank", "Why",  date, 500)
        );

        for (Transaction trans: transactions){
            database.transactionDao().add(trans);
        }
        return transactions;
    }


}
