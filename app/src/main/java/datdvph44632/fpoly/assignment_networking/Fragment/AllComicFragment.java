package datdvph44632.fpoly.assignment_networking.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import datdvph44632.fpoly.assignment_networking.R;
import datdvph44632.fpoly.assignment_networking.adapters.ComicAdapter;
import datdvph44632.fpoly.assignment_networking.interfaces.TruyenInterface;
import datdvph44632.fpoly.assignment_networking.models.Truyen;
import datdvph44632.fpoly.assignment_networking.others.DangKy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AllComicFragment extends Fragment {

    private RecyclerView recyclerView;
    private ComicAdapter adapter;

    private ArrayList<Truyen> arrayList = new ArrayList<>();
    String TAG = "dat";

    public static final String URL_API = "http://10.24.45.253:3000/";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_comic, container, false);

        recyclerView = view.findViewById(R.id.recyclerComic);

        // Gọi API để lấy danh sách truyện
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TruyenInterface mInterface = mRetrofit.create(TruyenInterface.class);
        Call<List<Truyen>> call = mInterface.getAllTruyen();
        call.enqueue(new Callback<List<Truyen>>() {
            @Override
            public void onResponse(Call<List<Truyen>> call, Response<List<Truyen>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: lấy dữ liệu hình ảnh " + response.body().toString());
                    arrayList = (ArrayList<Truyen>) response.body();
                    adapter = new ComicAdapter(getActivity(), arrayList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                    Toast.makeText(getActivity(), "Lấy danh sách thành công", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, "onResponse: Lấy danh sách không add thành công");
                    Toast.makeText(getActivity(), "Lấy danh sách Không thành công", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Truyen>> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}

//    // Gọi API để lấy danh sách truyện
//    Gson gson = new GsonBuilder().setLenient().create();
//    Retrofit mRetrofit = new Retrofit.Builder()
//            .baseUrl(URL_API)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build();
//
//    TruyenInterface mInterface = mRetrofit.create(TruyenInterface.class);
//    Call<List<Truyen>> call = mInterface.getAllTruyen();
//        call.enqueue(new Callback<List<Truyen>>() {
//@Override
//public void onResponse(Call<List<Truyen>> call, Response<List<Truyen>> response) {
//        if (response.isSuccessful()) {
//        Log.d(TAG, "onResponse: lấy dữ liệu Thành công " + response.body().toString());
//        arrayList = (ArrayList<Truyen>) response.body();
//        adapter = new ComicAdapter(getActivity(), arrayList);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//
//        Toast.makeText(getActivity(), "Lấy danh sách thành công", Toast.LENGTH_LONG).show();
//        } else {
//        Log.d(TAG, "onResponse: Lấy danh sách không add thành công");
//        Toast.makeText(getActivity(), "Lấy danh sách Không thành công", Toast.LENGTH_LONG).show();
//        }
//        }
//
//@Override
//public void onFailure(Call<List<Truyen>> call, Throwable t) {
//        Log.e(TAG, "Error: " + t.getMessage());
//        Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
//        }
//        });