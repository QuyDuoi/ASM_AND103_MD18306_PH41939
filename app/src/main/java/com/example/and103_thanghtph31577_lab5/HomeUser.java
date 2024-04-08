package com.example.and103_thanghtph31577_lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.and103_thanghtph31577_lab5.adapter.SanPhamAdapter;
import com.example.and103_thanghtph31577_lab5.databinding.ActivityHomeBinding;
import com.example.and103_thanghtph31577_lab5.databinding.ActivityHomeUserBinding;
import com.example.and103_thanghtph31577_lab5.model.Food;
import com.example.and103_thanghtph31577_lab5.model.Page;
import com.example.and103_thanghtph31577_lab5.model.Response;
import com.example.and103_thanghtph31577_lab5.services.HttpRequest;
import com.example.and103_thanghtph31577_lab5.view.FoodDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeUser extends AppCompatActivity implements SanPhamAdapter.AddCart {
    ActivityHomeUserBinding binding;
    private HttpRequest httpRequest;
    private SharedPreferences sharedPreferences;
    private String token;
    private SanPhamAdapter adapter;
    private ArrayList<Food> ds = new ArrayList<>();
    private int page = 1;
    private int totalPage = 0;
    private String sort="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityHomeUserBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("INFO",MODE_PRIVATE);

        token = sharedPreferences.getString("token","");
        httpRequest = new HttpRequest(token);

        Map<String,String> map = getMapFilter(page, "","0","-1");
        httpRequest.callAPI().getPageFood(map)
                .enqueue(getListFruitResponse);
        config();
        adapter = new SanPhamAdapter(this, ds,this );

        userListener();
    }
    private void config() {
        binding.nestScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    if (totalPage == page) return;
                    if (binding.loadmore.getVisibility() == View.GONE) {
                        binding.loadmore.setVisibility(View.VISIBLE);
                        page++;
                        FilterFood();
                    }
                }
            }
        });

        //spiner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_price, android.R.layout.simple_spinner_item);
        binding.spinner.setAdapter(spinnerAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CharSequence value = (CharSequence) parent.getAdapter().getItem(position);
                Log.d("zzzzzz", "onItemSelected: "+value.toString());
                if (value.toString().equals("Tăng dần")){
                    sort = "1";
                } else if (value.toString().equals("Giảm dần")) {
                    sort="-1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spinner.setSelection(1);
    }
    private void userListener () {
        binding.btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                ds.clear();
                FilterFood();
            }
        });
    }

    Callback<Response<Page<ArrayList<Food>>>> getListFruitResponse = new Callback<Response<Page<ArrayList<Food>>>>() {
        @Override
        public void onResponse(Call<Response<Page<ArrayList<Food>>>> call, retrofit2.Response<Response<Page<ArrayList<Food>>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    totalPage = response.body().getData().getTotalPage();
                    ArrayList<Food> _ds = response.body().getData().getData();
                    getData(_ds);
                }
            }else {
                Log.e("OKO", "");
            }
        }

        @Override
        public void onFailure(Call<Response<Page<ArrayList<Food>>>> call, Throwable t) {

        }
    };

    private void getData (ArrayList<Food> _ds) {
//        if (binding.loadmore.getVisibility() == View.VISIBLE) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    adapter.notifyItemInserted(ds.size()-1);
//                    binding.loadmore.setVisibility(View.GONE);
//                    ds.addAll(_ds);
//                    adapter.notifyDataSetChanged();
//                }
//            },1000);
//            return;
//        }
        ds.addAll(_ds);
        Log.e("CheckDS", "DanhSach: " + ds.get(0).getTenMon());

        adapter = new SanPhamAdapter(this, ds,this );
        binding.rcvFruit.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvFruit.setAdapter(adapter);
    }

    private void FilterFood(){
        String _name = binding.edSearchName.getText().toString().equals("")? "" : binding.edSearchName.getText().toString();
        String _price = binding.edSearchMoney.getText().toString().equals("")? "0" : binding.edSearchMoney.getText().toString();
        String _sort = sort.equals("") ? "-1": sort;
        Map<String,String> map = getMapFilter(page, _name, _price, _sort);
        httpRequest.callAPI().getPageFood(map).enqueue(getListFruitResponse);

    }
    private Map<String, String> getMapFilter(int _page,String _name, String _price, String _sort){
        Map<String,String> map = new HashMap<>();
        map.put("page", String.valueOf(_page));
        map.put("tenMon", String.valueOf(_name));
        map.put("gia", String.valueOf(_price));
        map.put("sort", String.valueOf(_sort));

        return map;
    }

    Callback<Response<Food>> responseFoodAPI = new Callback<Response<Food>>() {
        @Override
        public void onResponse(Call<Response<Food>> call, retrofit2.Response<Response<Food>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    page = 1;
                    ds.clear();
                    FilterFood();
                    Toast.makeText(HomeUser.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Food>> call, Throwable t) {
            Log.e("zzzzzzzz", "onFailure: "+t.getMessage() );
        }
    };

    @Override
    public void showDetail (Food food) {
        Intent intent = new Intent(HomeUser.this, ChiTietSanPham.class);
        intent.putExtra("sanpham", food);
        startActivity(intent);

    }

    @Override
    public void addToCart (Food food) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("loadddddd", "onResume: ");
        page = 1;
        ds.clear();
        FilterFood();
    }
}