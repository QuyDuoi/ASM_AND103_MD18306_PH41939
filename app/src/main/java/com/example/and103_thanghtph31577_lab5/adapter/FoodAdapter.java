package com.example.and103_thanghtph31577_lab5.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.and103_thanghtph31577_lab5.R;
import com.example.and103_thanghtph31577_lab5.databinding.ItemFoodBinding;
import com.example.and103_thanghtph31577_lab5.model.Food;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Food> list;
    private FoodClick foodClick;

    public FoodAdapter(Context context, ArrayList<Food> list, FoodClick foodClick) {
        this.context = context;
        this.list = list;
        this.foodClick = foodClick;
    }

    public interface FoodClick {
        void delete(Food food);
        void edit(Food food);
        void showDetail(Food food);
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        Food food = list.get(position);
        holder.binding.tvName.setText("Tên món: " + food.getTenMon());
        holder.binding.tvPriceQuantity.setText("Giá: " + food.getGia() + " VND");
        holder.binding.tvQuantiny.setText("Số lượng: " + food.getSoLuong());
        holder.binding.tvDes.setText("Mô tả: " + food.getMoTa());
        String url = food.getImage().get(0);
        String newUrl = url.replace("localhost", "10.0.2.2");
        Glide.with(context)
                .load(newUrl)
                .thumbnail(Glide.with(context).load(R.drawable.fruit))
                .into(holder.binding.img);

        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodClick.edit(food);
            }
        });
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodClick.delete(food);
            }
        });
        holder.binding.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodClick.showDetail(food);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFoodBinding binding;
        public ViewHolder(ItemFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
