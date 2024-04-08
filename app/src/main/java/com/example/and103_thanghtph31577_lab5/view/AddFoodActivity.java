package com.example.and103_thanghtph31577_lab5.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.and103_thanghtph31577_lab5.R;
import com.example.and103_thanghtph31577_lab5.databinding.ActivityAddFruitBinding;
import com.example.and103_thanghtph31577_lab5.model.LoaiMon;
import com.example.and103_thanghtph31577_lab5.model.Food;
import com.example.and103_thanghtph31577_lab5.model.Response;
import com.example.and103_thanghtph31577_lab5.services.HttpRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AddFoodActivity extends AppCompatActivity {
    ActivityAddFruitBinding binding;
    private HttpRequest httpRequest;
    private String id_LoaiMon;
    private ArrayList<LoaiMon> loaiMonArrayList;
    private ArrayList<File> ds_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAddFruitBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        ds_image = new ArrayList<>();
        httpRequest = new HttpRequest();
        configSpinner();
        userListener();

    }
    private void userListener() {
        binding.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
    });
    binding.btnRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Map<String , RequestBody> mapRequestBody = new HashMap<>();
            String _name = binding.edName.getText().toString().trim();
            String _quantity = binding.edQuantity.getText().toString().trim();
            String _price = binding.edPrice.getText().toString().trim();
            String _status = binding.edStatus.getText().toString().trim();
            String _description = binding.edDescription.getText().toString().trim();

            boolean check = true;

            if (TextUtils.isEmpty(_name)) {
                binding.tilTen.setError("Vui lòng nhập tên món!");
                check = false;
            } else {
                binding.tilTen.setError(null);
            }

            if (TextUtils.isEmpty(_quantity)) {
                binding.tilSoLuong.setError("Vui lòng nhập số lượng sản phẩm!");
                check = false;
            } else {
                binding.tilSoLuong.setError(null);
            }

            if (TextUtils.isEmpty(_price)) {
                binding.tilGia.setError("Vui lòng nhập giá sản phẩm!");
                check = false;
            } else {
                binding.tilGia.setError(null);
            }

            if (TextUtils.isEmpty(_status)) {
                binding.tilStatus.setError("Vui lòng nhập trạng thái món ăn!");
                check = false;
            } else if (!_status.equals("0") && !_status.equals("1")) {
                binding.tilStatus.setError("Trạng thái chỉ được là 0 (Còn hàng) hoặc 1 (Hết hàng)!");
                check = false;
            } else {
                binding.tilStatus.setError(null);
            }

            if (TextUtils.isEmpty(_description)){
                binding.tilMoTa.setError("Vui lòng nhập mô tả cho món ăn!");
                check = false;
            } else {
                binding.tilMoTa.setError(null);
            }

            if (!check) {
                return;
            }

            mapRequestBody.put("tenMon", getRequestBody(_name));
            mapRequestBody.put("soLuong", getRequestBody(_quantity));
            mapRequestBody.put("gia", getRequestBody(_price));
            mapRequestBody.put("trangThai", getRequestBody(_status));
            mapRequestBody.put("moTa", getRequestBody(_description));
            mapRequestBody.put("id_loaiMon", getRequestBody(id_LoaiMon));
            ArrayList<MultipartBody.Part> _ds_image = new ArrayList<>();
            ds_image.forEach(file1 -> {
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"),file1);
                MultipartBody.Part multipartBodyPart = MultipartBody.Part.createFormData("image", file1.getName(),requestFile);
                _ds_image.add(multipartBodyPart);
            });
            httpRequest.callAPI().addFoodWithFileImage(mapRequestBody, _ds_image).enqueue(responseFood);

        }
    });
    }

    Callback<Response<Food>> responseFood = new Callback<Response<Food>>() {
        @Override
        public void onResponse(Call<Response<Food>> call, retrofit2.Response<Response<Food>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(AddFoodActivity.this, "Thêm món mới thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Food>> call, Throwable t) {
            Toast.makeText(AddFoodActivity.this, "Thêm món mới thất bại", Toast.LENGTH_SHORT).show();
            Log.e("zzzzzzzzzz", "onFailure: "+ t.getMessage());
        }
    };

    private RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"),value);
    }
    private void chooseImage() {
        Log.d("123123", "chooseAvatar: " +123123);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        getImage.launch(intent);
    }

    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        ds_image.clear();
                        Intent data = o.getData();
                        if (data.getClipData() != null) {
                            int count = data.getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                File file = createFileFormUri(imageUri, "image" + i);
                                ds_image.add(file);
                            }
                        } else if (data.getData() != null) {
                            // Trường hợp chỉ chọn một hình ảnh
                            Uri imageUri = data.getData();
                            // Thực hiện các xử lý với imageUri
                            File file = createFileFormUri(imageUri, "image" );
                            ds_image.add(file);

                        }
                        Glide.with(AddFoodActivity.this)
                                .load(ds_image.get(0))
                                .thumbnail(Glide.with(AddFoodActivity.this).load(R.drawable.fruit))
                                .centerCrop()
                                .circleCrop()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(binding.avatar);
                    }
                }
            });

    private File createFileFormUri (Uri path, String name) {
        File _file = new File(AddFoodActivity.this.getCacheDir(), name + ".png");
        try {
            InputStream in = AddFoodActivity.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) >0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            Log.d("123123", "createFileFormUri: " +_file);
            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void configSpinner() {
        httpRequest.callAPI().getListLoaiMon().enqueue(getLoaiMonAPI);
        binding.spLoaiMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_LoaiMon = loaiMonArrayList.get(position).getId();
                Log.d("123123", "onItemSelected: " + id_LoaiMon);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spLoaiMon.setSelection(0);
    }

    Callback<Response<ArrayList<LoaiMon>>> getLoaiMonAPI = new Callback<Response<ArrayList<LoaiMon>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<LoaiMon>>> call, retrofit2.Response<Response<ArrayList<LoaiMon>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    loaiMonArrayList = response.body().getData();
                    String[] items = new String[loaiMonArrayList.size()];

                    for (int i = 0; i< loaiMonArrayList.size(); i++) {
                        items[i] = loaiMonArrayList.get(i).getName();
                    }
                    ArrayAdapter<String> adapterSpin = new ArrayAdapter<>(AddFoodActivity.this, android.R.layout.simple_spinner_item, items);
                    adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spLoaiMon.setAdapter(adapterSpin);
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<LoaiMon>>> call, Throwable t) {
            t.getMessage();
        }

    };
    }