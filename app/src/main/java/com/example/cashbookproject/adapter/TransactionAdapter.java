package com.example.cashbookproject.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cashbookproject.R;
import com.example.cashbookproject.databinding.RowLayoutBinding;
import com.example.cashbookproject.models.Category;
import com.example.cashbookproject.models.Transaction;
import com.example.cashbookproject.util.Constants;
import com.example.cashbookproject.util.Helper;
import com.example.cashbookproject.views.activities.MainActivity;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    Context context;
    ArrayList<Transaction> arrayList;

    public TransactionAdapter(Context context, ArrayList<Transaction> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {

        Transaction transaction = arrayList.get(position);

        holder.binding.rowAmount.setText(
                String.valueOf(transaction.getAmount())
        );
        holder.binding.rowCategory.setText(transaction.getCategory());
        holder.binding.rowType.setText(transaction.getAccount());
        holder.binding.rowDate.setText(Helper.formatDate(transaction.getDate()));

        Category transtionCategory = Constants.getCategoryDetails(transaction.getCategory());
        holder.binding.rowIcon.setImageResource(transtionCategory.getImage());
        holder.binding.rowIcon.setBackgroundTintList(context.getColorStateList(transtionCategory.getColor()));

        holder.binding.rowType.setBackgroundTintList(context.getColorStateList(Constants.getAccountColor(transaction.getAccount())));

        if (transaction.getType().equalsIgnoreCase(Constants.INCOME)){
            holder.binding.rowAmount.setTextColor(context.getColor(R.color.green));
        } else if (transaction.getType().equalsIgnoreCase(Constants.EXPENSE)){
            holder.binding.rowAmount.setTextColor(context.getColor(R.color.red));
        }

        holder.itemView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog deleteDialog = new AlertDialog
                                .Builder(context).create();
                        deleteDialog.setTitle("Delete Transaction");
                        deleteDialog.setMessage("Are you sure?");
                        deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                                (dialog, which) -> {
                                    ((MainActivity)context).viewModel.deleteTransaction(transaction);
                                });

                        deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                                (dialog, which) -> deleteDialog.dismiss());
                        deleteDialog.show();
                        return false;
                    }
                }
        );


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder{

        RowLayoutBinding binding;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
           binding = RowLayoutBinding.bind(itemView);
        }
    }

}
