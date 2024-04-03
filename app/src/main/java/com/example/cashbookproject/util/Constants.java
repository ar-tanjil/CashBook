package com.example.cashbookproject.util;

import com.example.cashbookproject.R;
import com.example.cashbookproject.models.Category;

import java.util.ArrayList;

public class Constants {

    public static String INCOME = "INCOME";
    public static String EXPENSE = "EXPENSE";

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


    public static int getAccountColor(String name) {
        switch (name) {
            case "Bank":
                return R.color.red;
            case "Cash":
                return R.color.green;
            default:
                return R.color.other;
        }
    }

}
