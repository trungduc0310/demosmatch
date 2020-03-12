package com.example.phamtrungduc.demogiaodien.entity;

public class Sanbong {
    String tensan;
    String diachi;
    String sdt;
    String hinhanh;


    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public Sanbong(String tensan, String diachi, String sdt, String hinhanh) {

        this.tensan = tensan;
        this.diachi = diachi;
        this.sdt = sdt;
        this.hinhanh = hinhanh;
    }

    public Sanbong() {

    }

    public String getTensan() {

        return tensan;
    }

    public void setTensan(String tensan) {
        this.tensan = tensan;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
