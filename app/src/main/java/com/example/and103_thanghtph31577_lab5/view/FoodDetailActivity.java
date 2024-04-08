package com.example.and103_thanghtph31577_lab5.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.and103_thanghtph31577_lab5.adapter.ImageAdapter;
import com.example.and103_thanghtph31577_lab5.databinding.ActivityFoodDetailBinding;
import com.example.and103_thanghtph31577_lab5.model.Food;

public class FoodDetailActivity extends AppCompatActivity {
    ActivityFoodDetailBinding binding;
    Food food;
    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setContentView(binding.getRoot());

        showData();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void showData() {
        //get data object intent
        Intent intent = getIntent();
        food = (Food) intent.getSerializableExtra("food");

        binding.tvName.setText("" + food.getTenMon());
        binding.tvPrice.setText("" + food.getGia() + " VND");
        binding.tvDescription.setText("" + food.getMoTa());
        binding.tvQuantity.setText("" + food.getSoLuong());
        binding.tvStatus.setText("" + (Integer.parseInt(food.getTrangThai()) == 0 ? "Còn hàng" : "Hết hàng"));

        adapter = new ImageAdapter(this, food.getImage());
        binding.rcvImg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rcvImg.setAdapter(adapter);
    }
}