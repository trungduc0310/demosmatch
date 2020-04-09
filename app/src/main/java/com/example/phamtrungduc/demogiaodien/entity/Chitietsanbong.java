package com.example.phamtrungduc.demogiaodien.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chitietsanbong {

@SerializedName("hinhanh")
@Expose
private String hinhanh;
@SerializedName("loaisan")
@Expose
private String loaisan;
@SerializedName("khoanggia")
@Expose
private String khoanggia;
@SerializedName("danhgia")
@Expose
private String danhgia;
@SerializedName("tenchusan")
@Expose
private String tenchusan;

public String getHinhanh() {
return hinhanh;
}

public void setHinhanh(String hinhanh) {
this.hinhanh = hinhanh;
}

public String getLoaisan() {
return loaisan;
}

public void setLoaisan(String loaisan) {
this.loaisan = loaisan;
}

public String getKhoanggia() {
return khoanggia;
}

public void setKhoanggia(String khoanggia) {
this.khoanggia = khoanggia;
}

public String getDanhgia() {
return danhgia;
}

public void setDanhgia(String danhgia) {
this.danhgia = danhgia;
}

public String getTenchusan() {
return tenchusan;
}

public void setTenchusan(String tenchusan) {
this.tenchusan = tenchusan;
}

}