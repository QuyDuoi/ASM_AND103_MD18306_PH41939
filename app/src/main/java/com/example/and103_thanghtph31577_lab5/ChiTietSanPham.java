package com.example.and103_thanghtph31577_lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.and103_thanghtph31577_lab5.adapter.ImageAdapter;
import com.example.and103_thanghtph31577_lab5.databinding.ActivityChiTietSanPhamBinding;
import com.example.and103_thanghtph31577_lab5.databinding.ActivityFoodDetailBinding;
import com.example.and103_thanghtph31577_lab5.model.Food;

public class ChiTietSanPham extends AppCompatActivity {
    ActivityChiTietSanPhamBinding binding;
    Food food;
    private ImageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChiTietSanPhamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showData();
        binding.backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void showData() {
        //get data object intent
        Intent intent = getIntent();
        food = (Food) intent.getSerializableExtra("sanpham");

        binding.tvTen.setText("" + food.getTenMon());
        binding.tvGia.setText("" + food.getGia() + " VND");
        binding.tvMoTa.setText("" + food.getMoTa());
        binding.tvSoLuong.setText("" + food.getSoLuong());
        binding.tvTrangThai.setText("" + (Integer.parseInt(food.getTrangThai()) == 0 ? "Còn hàng" : "Hết hàng"));

        adapter = new ImageAdapter(this, food.getImage());
        binding.rcvImgCt.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rcvImgCt.setAdapter(adapter);
    }
}