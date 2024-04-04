package com.example.cashbookproject.util;

import com.example.cashbookproject.R;
import com.example.cashbookproject.models.Account;
import com.example.cashbookproject.models.Category;

import java.util.ArrayList;

public class Constants {

    public static String INCOME = "Income";
    public static String EXPENSE = "Expense";

    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALENDER = 2;
    public static int SUMMARY = 3;
    public static int NOTE = 4;
    public static int SELECTED_TAB = 0;
    public static int SELECTED_STATS = 0;
    public static String SELECTED_STATS_TYPE = Constants.INCOME;




    public static ArrayList<Category> categories;

    public static void setCategories() {
        categories = new ArrayList<>();
        categories.add(new Category("Salary", R.drawable.salary, R.color.salary));
        categories.add(new Category("Business", R.drawable.business, R.color.business));
        categories.add(new Category("Investment", R.drawable.investment, R.color.investment));
        categories.add(new Category("Gift", R.drawable.gift, R.color.gift));
        categories.add(new Category("Food", R.drawable.food, R.color.food));
        categories.add(new Category("Rent", R.drawable.rent, R.color.rent));
        categories.add(new Category("Tax", R.drawable.tax, R.color.tax));
        categories.add(new Category("Other", R.drawable.other, R.color.other));
    }

    public static Category getCategoryDetails(String name) {

        for (Category category : categories) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;

    }


    public static ArrayList<Account> accountsLabel;

    public static void setAccountLabel() {
        accountsLabel = new ArrayList<>();

        accountsLabel.add(new Account(500, "Cash"));
        accountsLabel.add(new Account(0, "Bank"));
        accountsLabel.add(new Account(0, "Card"));
        accountsLabel.add(new Account(0, "Other"));

    }

    public static int getAccountImage(String name){
        switch (name) {
            case "Bank":
                return R.drawable.banck_account;
            case "Cash":
                return R.drawable.cash_account;
            case "Card":
                return R.drawable.card_account;
            default:
                return R.drawable.other_account;
        }
    }


    public static int getAccountColor(String name) {
        switch (name) {
            case "Bank":
                return R.color.bank;
            case "Cash":
                return R.color.cash;
            case "Card":
                return R.color.card;
            default:
                return R.color.otherAccount;
        }
    }

}
