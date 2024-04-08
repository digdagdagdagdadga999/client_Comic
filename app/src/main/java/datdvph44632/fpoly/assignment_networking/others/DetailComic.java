package datdvph44632.fpoly.assignment_networking.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import datdvph44632.fpoly.assignment_networking.R;
import datdvph44632.fpoly.assignment_networking.adapters.BinhLuanAdapter;
import datdvph44632.fpoly.assignment_networking.interfaces.TruyenInterface;
import datdvph44632.fpoly.assignment_networking.models.BinhLuan;
import datdvph44632.fpoly.assignment_networking.models.Truyen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailComic extends AppCompatActivity {
    private ImageView imgDetailComic;
    private TextView tvDetailTenTruyen, tvDetailTacGia, tvDetailNam, tvMoTa;
    private EditText edBinhLuan;
    private Button btnBinhLuan, btnDocTruyen;

    private RecyclerView recyclerView;

    private ArrayList<BinhLuan> binhLuanArrayList;

    BinhLuanAdapter binhLuanAdapter;

    public static final String URL_API = "http://10.24.45.253:3000/";
    String TAG = "dat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comic);

        imgDetailComic = findViewById(R.id.imgDetailComic);
        tvDetailTenTruyen = findViewById(R.id.tvDetailTenTruyen);
        tvDetailTacGia = findViewById(R.id.tvDetailTacGia);
        tvDetailNam = findViewById(R.id.tvDetailNam);
        tvMoTa = findViewById(R.id.tvMoTa);
        edBinhLuan = findViewById(R.id.edBinhLuan);
        btnBinhLuan = findViewById(R.id.btnBinhLuan);
        btnDocTruyen = findViewById(R.id.btnDocTruyen);
        recyclerView = findViewById(R.id.recyclerBinhLuan);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        Log.d(TAG, "Received ID: " + id);

        binhLuanArrayList = new ArrayList<>();


        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TruyenInterface mInterface = mRetrofit.create(TruyenInterface.class);
        Call<Truyen> call = mInterface.getTruyenByID(id);
        call.enqueue(new Callback<Truyen>() {
            @Override
            public void onResponse(Call<Truyen> call, Response<Truyen> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvDetailTenTruyen.setText(intent.getStringExtra("ten_truyen"));
                    tvDetailTacGia.setText(intent.getStringExtra("tac_gia"));
                    tvDetailNam.setText(intent.getStringExtra("nam"));
                    tvMoTa.setText(intent.getStringExtra("mo_ta"));
                    String anhBia = intent.getStringExtra("anh_bia");

                    if (anhBia != null && !anhBia.isEmpty()) {
                        Picasso.get().load(URL_API + "uploads/" + anhBia).into(imgDetailComic);
                    } else {
                        imgDetailComic.setImageResource(R.drawable.man_hinh_chao);
                    }
                    Toast.makeText(DetailComic.this, " lấy được truyện thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "API Call failed: " + response.errorBody());
                    Toast.makeText(DetailComic.this, "Không lấy được truyện ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Truyen> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(DetailComic.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        binhLuanAdapter = new BinhLuanAdapter(new ArrayList<BinhLuan>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(binhLuanAdapter);
        loadBinhLuan(id);
        btnBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemBinhLuan(id);
                edBinhLuan.setText("");
            }
        });

        btnDocTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDocTruyen = new Intent(DetailComic.this, DocTruyen.class);
                intentDocTruyen.putExtra("id", id);
                startActivity(intentDocTruyen);
            }
        });
    }

    private void loadBinhLuan(String id) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TruyenInterface mInterface = mRetrofit.create(TruyenInterface.class);
        Call<Truyen> call = mInterface.getTruyenByID(id);
        call.enqueue(new Callback<Truyen>() {
            @Override
            public void onResponse(Call<Truyen> call, Response<Truyen> response) {
                if (response.isSuccessful() && response.body() != null) {
                    binhLuanArrayList.clear();
                    binhLuanArrayList.addAll(response.body().getBinh_luan());
                    binhLuanAdapter.getData(binhLuanArrayList);
                } else {
                    Toast.makeText(DetailComic.this, "Không tải được bình luận", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Truyen> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(DetailComic.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ThemBinhLuan(String truyenid) {
        String content = edBinhLuan.getText().toString();
        if (content.isEmpty()) {
            edBinhLuan.setError("Vui lòng nhập bình luận");
            return;
        }
        BinhLuan binhLuan = new BinhLuan();
        SharedPreferences sharedPreferences = getSharedPreferences("User", getApplication().MODE_PRIVATE);
        String idUser = sharedPreferences.getString("id", "");
        String username = sharedPreferences.getString("username", "");

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TruyenInterface mInterface = mRetrofit.create(TruyenInterface.class);
        Call<BinhLuan> call = mInterface.addBinhLuan(truyenid, new BinhLuan(content, idUser, username, binhLuan.getDate(), binhLuan.getTruyenId()));
        call.enqueue(new Callback<BinhLuan>() {
            @Override
            public void onResponse(Call<BinhLuan> call, Response<BinhLuan> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(DetailComic.this, "Bình luận thành công", Toast.LENGTH_SHORT).show();
                    loadBinhLuan(truyenid);
                } else {
                    Toast.makeText(DetailComic.this, "Bình luận thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BinhLuan> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(DetailComic.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}