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
        categories.add(new Category("Salary", R.drawable.arrow, R.color.categoryOne));
        categories.add(new Category("Business", R.drawable.arrow, R.color.categoryTwo));
        categories.add(new Category("Invenstment", R.drawable.arrow, R.color.categoryThree));
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
                return R.color.categoryOne;
            case "Cash":
                return R.color.categoryTwo;
            default:
                return R.color.categoryThree;
        }
    }

}
