package com.example.cashbookproject.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cashbookproject.models.TransactionModel;

import java.util.List;

@Dao
public interface TransactionDao {

    @Query("select * from transaction_model")
    List<TransactionModel> getAll();

    @Insert
    void add(TransactionModel transactionModel);

    @Update
    void update(TransactionModel transactionModel);

    @Delete
    void delete(TransactionModel transactionModel);

}
