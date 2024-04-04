package com.example.cashbookproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cashbookproject.models.Category;
import com.example.cashbookproject.R;
import com.example.cashbookproject.databinding.CategoryItemBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public interface CategoryClickListener{
        void onCategoryClicked(Category category);
    }
   Context context;
   ArrayList<Category> categories;

   CategoryClickListener clickListener;


    public CategoryAdapter(Context context, ArrayList<Category> categories, CategoryClickListener clickListener) {
        this.context = context;
        this.categories = categories;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.catagoryTitle.setText(category.getName());
        holder.binding.categoryIcon.setImageResource(category.getImage());
        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(category.getColor()));

        holder.itemView.setOnClickListener(c -> {
            clickListener.onCategoryClicked(category);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        CategoryItemBinding binding;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CategoryItemBinding.bind(itemView);
        }
    }



}
