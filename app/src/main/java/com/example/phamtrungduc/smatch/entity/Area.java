package com.example.phamtrungduc.smatch.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Area {

@SerializedName("id_khuvuc")
@Expose
private String idKhuvuc;
@SerializedName("tenkhuvuc")
@Expose
private String tenkhuvuc;

public String getIdKhuvuc() {
return idKhuvuc;
}

public void setIdKhuvuc(String idKhuvuc) {
this.idKhuvuc = idKhuvuc;
}

public String getTenkhuvuc() {
return tenkhuvuc;
}

public void setTenkhuvuc(String tenkhuvuc) {
this.tenkhuvuc = tenkhuvuc;
}

}