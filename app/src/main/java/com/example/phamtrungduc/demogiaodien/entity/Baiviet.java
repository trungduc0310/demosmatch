package com.example.phamtrungduc.demogiaodien.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Baiviet implements Serializable {

    @SerializedName("idbaiviet")
    @Expose
    private String idbaiviet;
    @SerializedName("emailnguoidung")
    @Expose
    private String emailnguoidung;
    @SerializedName("tennguoidung")
    @Expose
    private String tennguoidung;
    @SerializedName("noidung")
    @Expose
    private String noidung;
    @SerializedName("thoigian")
    @Expose
    private String thoigian;
    @SerializedName("tieude")
    @Expose
    private String tieude;
    @SerializedName("hinhanh")
    @Expose
    private String hinhanh;
    @SerializedName("anhdaidien")
    @Expose
    private String anhdaidien;

    public String getIdbaiviet() {
        return idbaiviet;
    }

    public void setIdbaiviet(String idbaiviet) {
        this.idbaiviet = idbaiviet;
    }

    public String getEmailnguoidung() {
        return emailnguoidung;
    }

    public void setEmailnguoidung(String emailnguoidung) {
        this.emailnguoidung = emailnguoidung;
    }

    public String getTennguoidung() {
        return tennguoidung;
    }

    public void setTennguoidung(String tennguoidung) {
        this.tennguoidung = tennguoidung;
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

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getAnhdaidien() {
        return anhdaidien;
    }

    public void setAnhdaidien(String anhdaidien) {
        this.anhdaidien = anhdaidien;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public Baiviet(String idbaiviet, String emailnguoidung, String tennguoidung, String noidung, String thoigian, String tieude, String hinhanh, String anhdaidien) {
        this.idbaiviet = idbaiviet;
        this.emailnguoidung = emailnguoidung;
        this.tennguoidung = tennguoidung;
        this.noidung = noidung;
        this.thoigian = thoigian;
        this.tieude = tieude;
        this.hinhanh = hinhanh;
        this.anhdaidien = anhdaidien;
    }
}