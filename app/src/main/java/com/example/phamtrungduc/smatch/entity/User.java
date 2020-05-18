package com.example.phamtrungduc.smatch.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

@SerializedName("id_nguoidung")
@Expose
private String idNguoidung;
@SerializedName("tennguoidung")
@Expose
private String tennguoidung;
@SerializedName("email")
@Expose
private String email;
@SerializedName("matkhau")
@Expose
private String matkhau;
@SerializedName("vaitro")
@Expose
private String vaitro;
@SerializedName("gioitinh")
@Expose
private String gioitinh;
@SerializedName("diachi")
@Expose
private String diachi;
@SerializedName("ngaysinh")
@Expose
private String ngaysinh;
@SerializedName("anhdaidien")
@Expose
private String anhdaidien;

    protected User(Parcel in) {
        idNguoidung = in.readString();
        tennguoidung = in.readString();
        email = in.readString();
        matkhau = in.readString();
        vaitro = in.readString();
        gioitinh = in.readString();
        diachi = in.readString();
        ngaysinh = in.readString();
        anhdaidien = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getIdNguoidung() {
return idNguoidung;
}

public void setIdNguoidung(String idNguoidung) {
this.idNguoidung = idNguoidung;
}

public String getTennguoidung() {
return tennguoidung;
}

public void setTennguoidung(String tennguoidung) {
this.tennguoidung = tennguoidung;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getMatkhau() {
return matkhau;
}

public void setMatkhau(String matkhau) {
this.matkhau = matkhau;
}

public String getVaitro() {
return vaitro;
}

public void setVaitro(String vaitro) {
this.vaitro = vaitro;
}

public String getGioitinh() {
return gioitinh;
}

public void setGioitinh(String gioitinh) {
this.gioitinh = gioitinh;
}

public String getDiachi() {
return diachi;
}

public void setDiachi(String diachi) {
this.diachi = diachi;
}

public String getNgaysinh() {
return ngaysinh;
}

public void setNgaysinh(String ngaysinh) {
this.ngaysinh = ngaysinh;
}

public String getAnhdaidien() {
return anhdaidien;
}

public void setAnhdaidien(String anhdaidien) {
this.anhdaidien = anhdaidien;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idNguoidung);
        dest.writeString(tennguoidung);
        dest.writeString(email);
        dest.writeString(matkhau);
        dest.writeString(vaitro);
        dest.writeString(gioitinh);
        dest.writeString(diachi);
        dest.writeString(ngaysinh);
        dest.writeString(anhdaidien);
    }
}