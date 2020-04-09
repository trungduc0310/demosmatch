package com.example.phamtrungduc.demogiaodien.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sanbong {

    @SerializedName("id_sanbong")
    @Expose
    private String idSanbong;
    @SerializedName("tensanbong")
    @Expose
    private String tensanbong;
    @SerializedName("diachi")
    @Expose
    private String diachi;
    @SerializedName("sodienthoai")
    @Expose
    private String sodienthoai;
    @SerializedName("id_khuvuc")
    @Expose
    private String idKhuvuc;

    public String getIdSanbong() {
        return idSanbong;
    }

    public void setIdSanbong(String idSanbong) {
        this.idSanbong = idSanbong;
    }

    public String getTensanbong() {
        return tensanbong;
    }

    public void setTensanbong(String tensanbong) {
        this.tensanbong = tensanbong;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getIdKhuvuc() {
        return idKhuvuc;
    }

    public void setIdKhuvuc(String idKhuvuc) {
        this.idKhuvuc = idKhuvuc;
    }

    public Sanbong(String idSanbong, String tensanbong, String diachi, String sodienthoai, String idKhuvuc) {
        this.idSanbong = idSanbong;
        this.tensanbong = tensanbong;
        this.diachi = diachi;
        this.sodienthoai = sodienthoai;
        this.idKhuvuc = idKhuvuc;
    }
}