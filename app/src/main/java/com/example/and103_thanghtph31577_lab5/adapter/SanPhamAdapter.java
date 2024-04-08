package com.example.and103_thanghtph31577_lab5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.and103_thanghtph31577_lab5.R;
import com.example.and103_thanghtph31577_lab5.databinding.ItemFoodBinding;
import com.example.and103_thanghtph31577_lab5.databinding.ItemSanPhamBinding;
import com.example.and103_thanghtph31577_lab5.model.Food;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Food> list;
    private AddCart addCart;
    public SanPhamAdapter (Context context, ArrayList<Food> list, AddCart addCart) {
        this.context = context;
        this.list = list;
        this.addCart = addCart;
    }
    public interface AddCart {
        void addToCart (Food food);
        void showDetail (Food food);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSanPhamBinding binding = ItemSanPhamBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = list.get(position);
        holder.binding.tvSanPham.setText("Tên món: " + food.getTenMon());
        holder.binding.tvGia.setText("Giá: " + food.getGia() + " VND");
        holder.binding.tvSoLuong.setText("Số lượng: " + food.getSoLuong());
        holder.binding.tvMoTa.setText("Mô tả: " + food.getMoTa());
        String url = food.getImage().get(0);
        String newUrl = url.replace("localhost", "10.0.2.2");
        Glide.with(context)
                .load(newUrl)
                .thumbnail(Glide.with(context).load(R.drawable.fruit))
                .into(holder.binding.imgFood);
        holder.binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart.addToCart(food);
            }
        });
        holder.binding.tvSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart.showDetail(food);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSanPhamBinding binding;
        public ViewHolder(ItemSanPhamBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
