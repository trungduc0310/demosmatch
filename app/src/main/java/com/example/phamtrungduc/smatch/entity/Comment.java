package com.example.phamtrungduc.smatch.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {
    public Comment(String noidung, String thoigian, String tennguoidung, String anhdaidien, String email, String id_binhluan) {
        this.noidung = noidung;
        this.thoigian = thoigian;
        this.tennguoidung = tennguoidung;
        this.anhdaidien = anhdaidien;
        this.email = email;
        this.id_binhluan = id_binhluan;
    }

    @SerializedName("noidung")
    @Expose
    private String noidung;
    @SerializedName("thoigian")
    @Expose
    private String thoigian;
    @SerializedName("tennguoidung")
    @Expose
    private String tennguoidung;
    @SerializedName("anhdaidien")
    @Expose
    private String anhdaidien;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id_binhluan")
    @Expose
    private String id_binhluan;

    public String getId_binhluan() {
        return id_binhluan;
    }

    public void setId_binhluan(String id_binhluan) {
        this.id_binhluan = id_binhluan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getTennguoidung() {
        return tennguoidung;
    }

    public void setTennguoidung(String tennguoidung) {
        this.tennguoidung = tennguoidung;
    }

    public String getAnhdaidien() {
        return anhdaidien;
    }

    public void setAnhdaidien(String anhdaidien) {
        this.anhdaidien = anhdaidien;
    }
//    private String xulyngay(String ngay) {
//        String[] xuly = ngay.split("\\-");
//        ngay = xuly[2] + "/" + xuly[1] + "/" + xuly[0];
//        return ngay;
//    }

}