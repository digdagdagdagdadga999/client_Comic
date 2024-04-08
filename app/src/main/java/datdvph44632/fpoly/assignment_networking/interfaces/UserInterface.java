package datdvph44632.fpoly.assignment_networking.interfaces;

import java.util.List;

import datdvph44632.fpoly.assignment_networking.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserInterface {

    @GET("getuser")
    Call<List<User>> getAllUser();

    @POST("dangky")
    Call<User> dangkyUser(@Body User obj);

    @POST("dangnhap")
    Call<User> dangNhapUser(@Body User obj);

    @POST("edituser/{id}")
    Call<User> updateUser(@Path("id") String id, @Body User user);

    @GET("deleteUser/{id}")
    Call<User> deleteUser(@Path("id") String id);

    @GET("getUserById/{id}")
    Call<User> getUserByID(@Path("id") String id);

    @GET("dangxuat")
    Call<User> logOut();


}
