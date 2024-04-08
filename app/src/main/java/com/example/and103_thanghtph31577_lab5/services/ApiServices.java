package com.example.and103_thanghtph31577_lab5.services;

import com.example.and103_thanghtph31577_lab5.model.LoaiMon;
import com.example.and103_thanghtph31577_lab5.model.Food;
import com.example.and103_thanghtph31577_lab5.model.Page;
import com.example.and103_thanghtph31577_lab5.model.Response;
import com.example.and103_thanghtph31577_lab5.model.User;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiServices {
    public static String BASE_URL = "http://10.24.12.128:3000/api/";

    @GET("get-list-loai-mon")
        Call<Response<ArrayList<LoaiMon>>> getListLoaiMon();

    @GET("search-loai-mon")
        Call<Response<ArrayList<LoaiMon>>> searchLoaiMon(@Query("key") String key);

    @POST("add-loai-mon")
    Call<Response<LoaiMon>> addLoaiMon(@Body LoaiMon distributor);

    @PUT("update-loai-mon-by-id/{id}")
    Call<Response<LoaiMon>> updateLoaiMon(@Path("id") String id, @Body LoaiMon loaiMon);

    @DELETE("destroy-loai-mon-by-id/{id}")
    Call<Response<LoaiMon>> deleteLoaiMon (@Path("id") String id);

    @Multipart
    @POST("register-send-email")
    Call<Response<User>> register(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("email") RequestBody email,
            @Part("name") RequestBody name,
            @Part MultipartBody.Part avartar
            );

    @POST("login")
    Call<Response<User>> login (@Body User user);

    @GET("get-list-food")
    Call<Response<ArrayList<Food>>> getListFood(@Header("Authorization")String token);

    @Multipart
    @POST("add-food-with-file-image")
    Call<Response<Food>> addFoodWithFileImage(@PartMap Map<String, RequestBody> requestBodyMap,
                                               @Part ArrayList<MultipartBody.Part> ds_hinh
                                                );


    @GET("get-page-food")
    Call<Response<Page<ArrayList<Food>>>> getPageFood (@QueryMap Map<String, String> stringMap);


    @Multipart
    @PUT("update-food-by-id/{id}")
    Call<Response<Food>> updateFoodWithFileImage(@PartMap Map<String, RequestBody> requestBodyMap,
                                                  @Path("id") String id,
                                                  @Part ArrayList<MultipartBody.Part> ds_hinh
    );

    @DELETE("destroy-food-by-id/{id}")
    Call<Response<Food>> deleteFoods(@Path("id") String id);

    @GET("get-food-by-id/{id}")
    Call<Response<Food>> getFoodById (@Path("id") String id);




}


