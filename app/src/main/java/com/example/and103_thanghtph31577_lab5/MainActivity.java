package com.example.and103_thanghtph31577_lab5;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.and103_thanghtph31577_lab5.adapter.LoaiMonAdapter;
import com.example.and103_thanghtph31577_lab5.databinding.ActivityMainBinding;
import com.example.and103_thanghtph31577_lab5.databinding.DialogAddBinding;
import com.example.and103_thanghtph31577_lab5.model.LoaiMon;
import com.example.and103_thanghtph31577_lab5.model.Response;
import com.example.and103_thanghtph31577_lab5.services.HttpRequest;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class MainActivity extends AppCompatActivity implements LoaiMonAdapter.LoaiMonClick {
    private ActivityMainBinding binding;
    private HttpRequest httpRequest;
    private ArrayList<LoaiMon> list = new ArrayList<>();
    private LoaiMonAdapter adapter;
    private static final String TAG = "MainActivity";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        fetchAPI();
        userListener();
    }
    private void fetchAPI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        httpRequest = new HttpRequest();
        httpRequest.callAPI()
                .getListLoaiMon()
                .enqueue(getLoaiMonAPI);

    }

    private void userListener() {
        binding.edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String key = binding.edSearch.getText().toString().trim();
                    httpRequest.callAPI()
                            .searchLoaiMon(key)
                            .enqueue(getLoaiMonAPI);
                    Log.d(TAG, "onEditorAction: " + key);
                    return true;
                }
                return false;
            }
        });

        binding.backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

    }
    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm loại món");
        DialogAddBinding binding1 = DialogAddBinding.inflate(LayoutInflater.from(this));
        builder.setView(binding1.getRoot());
        AlertDialog alertDialog = builder.create();
        binding1.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding1.etName.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên loại món!", Toast.LENGTH_SHORT).show();
                }   else {
                    LoaiMon loaiMon = new LoaiMon();
                    loaiMon.setName(name);
                    httpRequest.callAPI()
                            .addLoaiMon(loaiMon)
                            .enqueue(responseLoaiMonAPI);
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }


    private void getData() {
        adapter = new LoaiMonAdapter(list, this,this );
        binding.rcvDistributor.setAdapter(adapter);
        progressDialog.dismiss();
    }

    Callback<Response<ArrayList<LoaiMon>>> getLoaiMonAPI = new Callback<Response<ArrayList<LoaiMon>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<LoaiMon>>> call, retrofit2.Response<Response<ArrayList<LoaiMon>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    list = response.body().getData();
                    getData();
                    Log.d(TAG, "onResponse: "+ list.size());
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<LoaiMon>>> call, Throwable t) {
            Log.e(TAG, "onFailure: "+ t.getMessage() );
        }

    };
    Callback<Response<LoaiMon>> responseLoaiMonAPI  = new Callback<Response<LoaiMon>>() {
        @Override
        public void onResponse(Call<Response<LoaiMon>> call, retrofit2.Response<Response<LoaiMon>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                   httpRequest.callAPI()
                           .getListLoaiMon()
                           .enqueue(getLoaiMonAPI);
                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<LoaiMon>> call, Throwable t) {
            Log.e(TAG, "onFailure: "+t.getMessage() );
        }
    };

    private void showDialogEdit(LoaiMon loaiMon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật loại món");
        DialogAddBinding binding1 = DialogAddBinding.inflate(LayoutInflater.from(this));
        builder.setView(binding1.getRoot());
        AlertDialog alertDialog = builder.create();

        binding1.etName.setText(loaiMon.getName());

        binding1.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = loaiMon.getName();

                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên loại món!", Toast.LENGTH_SHORT).show();
                }   else {

                    LoaiMon distributor1 = new LoaiMon();
                    distributor1.setName(binding1.etName.getText().toString().trim());
                    httpRequest.callAPI()
                            .updateLoaiMon(loaiMon.getId(),distributor1)
                            .enqueue(responseLoaiMonAPI);
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }
    @Override
    public void delete(LoaiMon loaiMon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có muốn xóa loại món này?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            httpRequest.callAPI()
                    .deleteLoaiMon(loaiMon.getId())
                    .enqueue(responseLoaiMonAPI);
        });
        builder.setNegativeButton("Không", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }

    @Override
    public void edit(LoaiMon loaiMon) {
        showDialogEdit(loaiMon);
    }
}

