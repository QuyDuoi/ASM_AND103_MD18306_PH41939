package com.example.and103_thanghtph31577_lab5.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoaiMon implements Serializable {
    @SerializedName("_id")
    private String id;
    private String tenLoai , createdAt, updatedAt;

    public LoaiMon(String id, String tenLoai, String createdAt, String updatedAt) {
        this.id = id;
        this.tenLoai = tenLoai;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LoaiMon() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return tenLoai;
    }

    public void setName(String name) {
        this.tenLoai = name;
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
