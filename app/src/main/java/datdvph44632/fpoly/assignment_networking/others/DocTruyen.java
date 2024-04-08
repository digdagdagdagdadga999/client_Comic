package datdvph44632.fpoly.assignment_networking.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import datdvph44632.fpoly.assignment_networking.R;
import datdvph44632.fpoly.assignment_networking.adapters.DocTruyenAdapter;
import datdvph44632.fpoly.assignment_networking.interfaces.TruyenInterface;
import datdvph44632.fpoly.assignment_networking.models.Truyen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DocTruyen extends AppCompatActivity {
    private RecyclerView recyclerView;
    DocTruyenAdapter adapter;
    ArrayList<String> list;

    public static final String URL_API = "http://10.24.45.253:3000/";
    String TAG = "dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_truyen);

        recyclerView = findViewById(R.id.recyclerDoc);
        Intent intent = getIntent();
        String Id = intent.getStringExtra("id");

        list = new ArrayList<>();
        adapter = new DocTruyenAdapter(list, getApplication());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        LoadDocTruyen(Id);
    }

    private void LoadDocTruyen(String id){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TruyenInterface mInterface = mRetrofit.create(TruyenInterface.class);
        Call<Truyen> call = mInterface.doctruyen(id);
        call.enqueue(new Callback<Truyen>() {
            @Override
            public void onResponse(Call<Truyen> call, Response<Truyen> response) {
                if (response.isSuccessful() && response.body() != null){
                    Truyen truyen = response.body();
                    list.addAll(truyen.getNoi_dung_anh_truyen());
                    adapter.LoadDataDoc(list);
                    Toast.makeText(DocTruyen.this, "load ảnh truyện thành công", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(DocTruyen.this, "Không load được ảnh truyện", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Truyen> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(DocTruyen.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}