package com.example.cashbookproject.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cashbookproject.models.Transaction;

@Database(entities = {Transaction.class}, exportSchema = false, version = 1)
@TypeConverters(Converters.class)
public abstract class DatabaseHelper extends RoomDatabase {

    private static final String DB_NAME = "cashbook";
    private static DatabaseHelper instance;


    public static synchronized DatabaseHelper getDb(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context,
                    DatabaseHelper.class,
                    DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract TransactionDao transactionDao();


}
