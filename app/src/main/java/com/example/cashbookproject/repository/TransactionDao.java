package com.example.cashbookproject.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cashbookproject.models.Transaction;

import java.util.Date;
import java.util.List;

@Dao
public interface TransactionDao {

    @Query("select * from transaction_model")
    List<Transaction> getAll();

    @Query("SELECT * FROM transaction_model WHERE date >= :start And date <= :end ")
    public List<Transaction> getAllByDate(Date start, Date end);

    @Query("SELECT * FROM transaction_model WHERE date >= :start And date <= :end And type = :type ")
    public List<Transaction> getAllByDateAndCategory(Date start, Date end, String type );


    @Query("SELECT sum(amount) FROM transaction_model WHERE date >= :start And date <= :end and type = 'Income' ")
    public double getIncomeByDate(Date start, Date end);

    @Query("SELECT sum(amount) FROM transaction_model WHERE date >= :start And date <= :end and type = 'Expense' ")
    public double getExpenseByDate(Date start, Date end);

    @Query("SELECT sum(amount) FROM transaction_model WHERE date >= :start And date <= :end ")
    public double getTotalByDate(Date start, Date end);

    @Insert
    void add(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

}
