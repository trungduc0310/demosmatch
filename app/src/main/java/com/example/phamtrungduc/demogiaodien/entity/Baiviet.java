package com.example.phamtrungduc.demogiaodien.entity;

import java.io.Serializable;

public class Baiviet implements Serializable {
    String avt;
    String username;
    String datetime;
    String tieude;
    String noidung;

    public Baiviet() {
    }

    public Baiviet(String avt, String username, String datetime, String tieude, String noidung) {
        this.avt = avt;
        this.username = username;
        this.datetime = datetime;
        this.tieude = tieude;
        this.noidung = noidung;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
