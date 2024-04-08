package com.example.and103_thanghtph31577_lab5.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.and103_thanghtph31577_lab5.HomeUser;
import com.example.and103_thanghtph31577_lab5.MainActivity;
import  com.example.and103_thanghtph31577_lab5.databinding.ActivityLoginBinding;
import com.example.and103_thanghtph31577_lab5.model.Response;
import com.example.and103_thanghtph31577_lab5.model.User;
import com.example.and103_thanghtph31577_lab5.services.HttpRequest;

import retrofit2.Call;
import retrofit2.Callback;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        httpRequest = new HttpRequest();
        userListener();
        binding.txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void userListener() {
        binding.btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                String _username = binding.edtEmailDn.getText().toString().trim();
                String _password = binding.edtPassDn.getText().toString().trim();
                boolean check = false;
                if (_username.isEmpty()){
                    binding.tilEmailDn.setError("Vui lòng nhập tài khoản!");
                    check = true;
                } else {
                    binding.tilEmailDn.setError(null);
                }
                if (_password.isEmpty()){
                    binding.tilPassDn.setError("Vui lòng nhập mật khẩu!");
                    check = true;
                } else {
                    binding.tilPassDn.setError(null);
                }
                if (check) {
                    return;
                }
                user.setUsername(_username);
                user.setPassword(_password);
                httpRequest.callAPI().login(user).enqueue(responseUser);
                if (_username.equals("quytueur")) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(LoginActivity.this, HomeUser.class));
                }
                finish();
            }
        });
    }

    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    SharedPreferences sharedPreferences = getSharedPreferences("INFO",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", response.body().getToken());
                    editor.putString("refreshToken", response.body().getRefreshToken());
                    editor.putString("id", response.body().getData().get_id());
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            t.getMessage();
        }
    };
}