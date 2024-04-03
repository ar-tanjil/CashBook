package com.example.cashbookproject.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "transaction_model")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "account")
    private String account;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "amount")
    private double amount;

    @Ignore
    public Transaction() {
    }

    public Transaction(Long id, String type, String category, String account, String note, Date date, double amount) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.account = account;
        this.note = note;
        this.date = date;
        this.amount = amount;
    }

    @Ignore
    public Transaction(String type, String category, String account, String note, Date date, double amount) {
        this.type = type;
        this.category = category;
        this.account = account;
        this.note = note;
        this.date = date;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", account='" + account + '\'' +
                ", note='" + note + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
