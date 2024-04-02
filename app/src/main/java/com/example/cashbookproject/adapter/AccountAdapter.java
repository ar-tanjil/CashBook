package com.example.cashbookproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cashbookproject.R;
import com.example.cashbookproject.databinding.DialogeAccountBinding;
import com.example.cashbookproject.models.Account;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    public interface  AccountClickListener{
        void onAccountSelected(Account account);
    }

    Context context;
    ArrayList<Account> arrayList;

    AccountClickListener accountClickListener;

    public AccountAdapter(Context context, ArrayList<Account> arrayList, AccountClickListener accountClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.accountClickListener = accountClickListener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.dialoge_account, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
            Account account = arrayList.get(position);
            holder.binding.acountTitle.setText(account.getName());
            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            accountClickListener.onAccountSelected(account);
                        }
                    }
            );
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder{

        DialogeAccountBinding binding;
        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DialogeAccountBinding.bind(itemView);
        }
    }

}
