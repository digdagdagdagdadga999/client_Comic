package datdvph44632.fpoly.assignment_networking.interfaces;

import java.util.List;

import datdvph44632.fpoly.assignment_networking.models.BinhLuan;
import datdvph44632.fpoly.assignment_networking.models.Truyen;
import datdvph44632.fpoly.assignment_networking.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TruyenInterface {
    @GET("alltruyen")
    Call<List<Truyen>> getAllTruyen();

    @GET("chiTietTruyen/{id}")
    Call<Truyen> getTruyenByID(@Path("id") String id);

    @POST("chiTietTruyen/{id}/Conment")
    Call<BinhLuan> addBinhLuan(@Path("id") String id, @Body BinhLuan binhLuan);

    @GET("chiTietTruyen/{id}/doctruyen")
    Call<Truyen> doctruyen(@Path("id") String id);
}
