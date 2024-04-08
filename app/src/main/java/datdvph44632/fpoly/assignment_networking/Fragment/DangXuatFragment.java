package datdvph44632.fpoly.assignment_networking.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import datdvph44632.fpoly.assignment_networking.R;
import datdvph44632.fpoly.assignment_networking.interfaces.UserInterface;
import datdvph44632.fpoly.assignment_networking.models.User;
import datdvph44632.fpoly.assignment_networking.others.DangNhap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DangXuatFragment extends Fragment {

    String TAG = "dat";
    public static final String URL_API = "http://10.24.45.253:3000/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_user, container, false);

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit mRetrofit = new Retrofit.Builder().baseUrl(URL_API).addConverterFactory(GsonConverterFactory.create(gson)).build();

        UserInterface mInterface = mRetrofit.create(UserInterface.class);
        Call<User> call = mInterface.logOut();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), DangNhap.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Đăng xuất thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                t.printStackTrace();// in ra danh sách các file liên quan tới lỗi
            }
        });

        return view;


    }
}