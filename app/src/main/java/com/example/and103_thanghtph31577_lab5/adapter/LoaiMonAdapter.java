package com.example.and103_thanghtph31577_lab5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and103_thanghtph31577_lab5.databinding.ItemLoaiMonBinding;
import com.example.and103_thanghtph31577_lab5.model.LoaiMon;

import java.util.ArrayList;

public class LoaiMonAdapter extends RecyclerView.Adapter<LoaiMonAdapter.ViewHolder> {
    private ArrayList<LoaiMon> list;
    private Context context;
    private LoaiMonClick loaiMonClick;

    public LoaiMonAdapter(ArrayList<LoaiMon> list, Context context, LoaiMonClick loaiMonClick) {
        this.list = list;
        this.context = context;
        this.loaiMonClick = loaiMonClick;
    }

    public interface LoaiMonClick {
        void delete(LoaiMon loaiMon);
        void edit(LoaiMon loaiMon);
    }

    @NonNull
    @Override
    public LoaiMonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLoaiMonBinding binding = ItemLoaiMonBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiMonAdapter.ViewHolder holder, int position) {
        LoaiMon loaiMon = list.get(position);
        holder.binding.tvName.setText("Tên loại: " + loaiMon.getName());
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiMonClick.delete(loaiMon);
            }
        });

        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiMonClick.edit(loaiMon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemLoaiMonBinding binding;
        public ViewHolder(ItemLoaiMonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
