package com.example.and103_thanghtph31577_lab5.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Food implements Serializable {
    private String _id, tenMon , soLuong, trangThai, gia;
    private String moTa;
    private ArrayList<String> image;
    @SerializedName("id_loaiMon")
    LoaiMon loaiMon;
    private String createdAt,updatedAt;

    public Food (String _id, String tenMon, String soLuong, String trangThai, String gia, String moTa, ArrayList<String> image, LoaiMon loaiMon, String createdAt, String updatedAt) {
        this._id = _id;
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        this.gia = gia;
        this.moTa = moTa;
        this.image = image;
        this.loaiMon = loaiMon;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Food() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public LoaiMon getLoaiMon() {
        return loaiMon;
    }

    public void setLoaiMon(LoaiMon loaiMon) {
        this.loaiMon = loaiMon;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
