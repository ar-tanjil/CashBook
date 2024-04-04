package com.example.cashbookproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cashbookproject.R;
import com.example.cashbookproject.databinding.AccountRowLayoutBinding;
import com.example.cashbookproject.models.Account;
import com.example.cashbookproject.models.Transaction;
import com.example.cashbookproject.util.Constants;

import java.util.ArrayList;

public class AccountAdapterTwo extends RecyclerView.Adapter<AccountAdapterTwo.AccountTwoViewHoleder> {


    Context context;
    ArrayList<Account> accountArrayList;

    public AccountAdapterTwo(Context context, ArrayList<Account> accountArrayList) {
        this.context = context;
        this.accountArrayList = accountArrayList;
    }

    @NonNull
    @Override
    public AccountTwoViewHoleder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountTwoViewHoleder(LayoutInflater.from(context)
                .inflate(R.layout.account_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountTwoViewHoleder holder, int position) {
        Account transaction = accountArrayList.get(position);
        holder.binding.rowAmount.setText(
                String.valueOf(transaction.getAmount())
        );

        holder.binding.rowCategory.setText(transaction.getName());

        holder.binding.rowIcon.setImageResource(Constants.getAccountImage(transaction.getName()));
//        holder.binding.rowIcon.setBackgroundTintList(Constants.getAccountImage(transaction.getName()));


    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class AccountTwoViewHoleder extends RecyclerView.ViewHolder {

        AccountRowLayoutBinding binding;

        public AccountTwoViewHoleder(@NonNull View itemView) {
            super(itemView);
            binding = AccountRowLayoutBinding.bind(itemView);
        }
    }

}
