package com.example.phamtrungduc.demogiaodien.Retrofit2;

import com.example.phamtrungduc.demogiaodien.entity.Baiviet;
import com.example.phamtrungduc.demogiaodien.entity.Binhluan;
import com.example.phamtrungduc.demogiaodien.entity.Chitietsanbong;
import com.example.phamtrungduc.demogiaodien.entity.Khuvuc;
import com.example.phamtrungduc.demogiaodien.entity.Nguoidung;
import com.example.phamtrungduc.demogiaodien.entity.Sanbong;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataClient {
    @Multipart
    @POST("upload_hinhanh/upload_anhdaidien.php")
    Call<String> uploadImg_anhdaidien(@Part MultipartBody.Part img);

    @Multipart
    @POST("upload_hinhanh/upload_hinhanhbaiviet.php")
    Call<String> uploadImg_anhbaiviet(@Part MultipartBody.Part img);


    @FormUrlEncoded
    @POST("quanlybaiviet/nguoidung/nguoidung_thembaiviet.php")
    Call<String> nguoidung_thembaiviet(@Field("id_baiviet") String id_baiviet,
                                       @Field("email_nguoidung") String email_nguoidung,
                                       @Field("noidung") String noidung,
                                       @Field("tieude") String tieude,
                                       @Field("hinhanh") String url_hinhanh);

    @FormUrlEncoded
    @POST("quanlybaiviet/nguoidung/nguoidung_capnhatbaiviet.php")
    Call<String> nguoidung_chinhsuabaiviet(@Field("id_baiviet") String id_baiviet,
                                           @Field("noidung") String noidung);

    @FormUrlEncoded
    @POST("quanlybaiviet/nguoidung/nguoidung_xoabaiviet.php")
    Call<String> nguoidung_xoabaiviet(@Field("id_baiviet") String id_baiviet,
                                      @Field("hinhanh") String hinhanh);



    @GET("quanlybaiviet/nguoidung/nguoidung_xembaiviet.php")
    Call<List<Baiviet>> nguoidung_getbaiviet(@Query("page") int page);

    @GET("quanlybaiviet/nguoidung/nguoidung_xemtrangcanhan.php")
    Call<List<Baiviet>> nguoidung_xemtrangcanhan(@Query("email_nguoidung") String email_nguoidung,
                                                 @Query("page") int page);

    @FormUrlEncoded
    @POST("quanlybaiviet/nguoidung/get_binhluan.php")
    Call<List<Binhluan>> nguoidung_getbinhluan(@Field("id_baiviet") String id_baiviet);

    @FormUrlEncoded
    @POST("quanlybaiviet/nguoidung/insert_binhluan.php")
    Call<String> nguoidung_thembinhluan(@Field("noidung") String noidung,
                                        @Field("id_baiviet") String id_baiviet,
                                        @Field("email_nguoidung") String email_nguoidung);

    @GET("quanlybaiviet/nguoidung/delete_binhluan.php")
    Call<String> nguoidung_xoabinhluan(@Query("id_binhluan") int id_binhluan);

    @FormUrlEncoded
    @POST("quanlybaiviet/nguoidung/nguoidung_timkiem.php")
    Call<List<Baiviet>> nguoidung_timkiem(@Field("timkiem") String timkiem);

    @GET("quanlysanbong/xemdanhsachsanbong.php")
    Call<List<Sanbong>> nguoidung_xemdanhsachsanbong(@Query("id_khuvuc") String id_khuvuc);

    @GET("quanlysanbong/get_khuvuc.php")
    Call<List<Khuvuc>> sanbong_getkhuvuc();

    @FormUrlEncoded
    @POST("quanlysanbong/get_chitietsanbong.php")
    Call<List<Chitietsanbong>> sanbong_getchitietsanbong(@Field("id_sanbong") String id_sanbong);

    @FormUrlEncoded
    @POST("quanlytaikhoan/dangnhap.php")
    Call<List<Nguoidung>> nguoidung_dangnhap(@Field("email") String email,
                                             @Field("password") String password);

    @FormUrlEncoded
    @POST("quanlytaikhoan/dangky.php")
    Call<String> nguoidung_dangky(@Field("id_nguoidung") String id_nguoidung,
                                  @Field("email") String email,
                                  @Field("matkhau") String password,
                                  @Field("tennguoidung") String username,
                                  @Field("anhdaidien") String anhdaidien);
    @FormUrlEncoded
    @POST("quanlytaikhoan/doimatkhau.php")
    Call<String> nguoidung_doimatkhau(@Field("email") String email,
                                      @Field("matkhaucu") String matkhaucu,
                                      @Field("matkhaumoi") String matkhaumoi);
    @FormUrlEncoded
    @POST("quanlytaikhoan/capnhathoso.php")
    Call<String> nguoidung_capnhathoso(@Field("email_nguoidung") String email,
                                       @Field("tennguoidung") String username,
                                       @Field("gioitinh") String gioitinh,
                                       @Field("diachi") String diachi,
                                       @Field("ngaysinh") String ngaysinh,
                                       @Field("avt_old") String avt_old,
                                       @Field("avt_new") String avt_new);
    @FormUrlEncoded
    @POST("get_nguoidung.php")
    Call<List<Nguoidung>> get_nguoidung(@Field("email") String email);
}
